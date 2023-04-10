package com.paypal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.paypal.dto.LoginUserDto;
import com.paypal.dto.RegisterUserDto;
import com.paypal.dto.UpdateUserDto;
import com.paypal.exception.TaskException;
import com.paypal.exception.UserException;
import com.paypal.model.Sprint;
import com.paypal.model.Task;
import com.paypal.model.User;
import com.paypal.repository.SprintRepository;
import com.paypal.repository.TaskRepository;
import com.paypal.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository urepo;

	@Autowired
	private TaskRepository trepo;

	@Autowired
	private SprintRepository srepo;

	@Override
	public User registerAnUser(RegisterUserDto dto) throws UserException {
		Optional<User> userOpt = urepo.findByEmail(dto.getEmail());

		if (userOpt.isEmpty()) {
			User newUser = new User();

			newUser.setUserName(dto.getUserName());
			newUser.setEmail(dto.getEmail());
			newUser.setAddress(dto.getAddress());
			newUser.setPassword(dto.getPassword());
			newUser.setRole(dto.getRole());

			User savedUser = urepo.save(newUser);

			return savedUser;
		}
		throw new UserException("Email Id is already registered. Please use a different email id.");
	}

	@Override
	public User loginUser(LoginUserDto dto) throws UserException {
		Optional<User> userOpt = urepo.findByEmail(dto.getEmail());

		if (userOpt.isPresent()) {
			User existingUser = userOpt.get();

			if (existingUser.getPassword().equals(dto.getPassword())) {
				return existingUser;
			}

			throw new UserException("Incorrect password for the email id: " + dto.getEmail()
					+ ". Please try again with the correct one.");
		}
		throw new UserException("The email id: " + dto.getEmail() + " is not a registered email id.");
	}

	@Override
	public List<Task> getAssignedTasks(Integer userId) throws UserException, TaskException {
		Optional<User> userOpt = urepo.findById(userId);

		if (userOpt.isPresent()) {
			User existingUser = userOpt.get();
			List<Task> assignedTasks = existingUser.getAssignedTasks();

			if (!assignedTasks.isEmpty()) {
				return assignedTasks;
			}
			throw new TaskException(
					"No assigned task yet with user: " + existingUser.getUserName() + " (Id: " + userId + ")");
		}
		throw new UserException("No user with id: " + userId + " found in the system...!");
	}

	@Override
	public User deleteUserById(Integer userId) throws UserException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			User currentUser = urepo.findByEmail(currentUsername).get();
			if (currentUser.getRole().equals("ROLE_ADMIN")) {
				Optional<User> targetUserOpt = urepo.findById(userId);
				if (targetUserOpt.isPresent()) {
					User targetUser = targetUserOpt.get();
					urepo.delete(targetUser);

					// Putting null in place of creator id for all associated sprints
					List<Sprint> associatedSprints = srepo.getSprintsByCreatorId(userId);
					for (Sprint sprint : associatedSprints) {
						sprint.setCreatorId(null);
						srepo.save(sprint);
					}

					return targetUser;
				}
				throw new UserException("Invalid user id.");
			}
			throw new UserException("Only an admin can delete a user.");
		}
		throw new UserException("A user cannot be deleted by someone with anonymous authentication.");

	}

	@Override
	public User updateUserDetails(UpdateUserDto dto) throws UserException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			User currentUser = urepo.findByEmail(currentUsername).get();

			if (currentUser.getRole().equals("ROLE_ADMIN")) {
				Optional<User> targetUserOpt = urepo.findById(dto.getUserId());
				if (targetUserOpt.isPresent()) {
					User targetUser = targetUserOpt.get();

					targetUser.setUserName(dto.getUserName());
					targetUser.setEmail(dto.getEmail());
					targetUser.setPassword(dto.getPassword());
					targetUser.setAddress(dto.getAddress());

					return urepo.save(targetUser);

				}
				throw new UserException("Invalid user id: " + dto.getUserId());
			} else if (currentUser.getRole().equals("ROLE_USER")) {
				if (currentUser.getUserId() == dto.getUserId()) {
					User targetUser = urepo.findById(dto.getUserId()).get();

					targetUser.setUserName(dto.getUserName());
					targetUser.setEmail(dto.getEmail());
					targetUser.setPassword(dto.getPassword());
					targetUser.setAddress(dto.getAddress());

					return urepo.save(targetUser);
				}
				throw new UserException("Apart from admin, the user itself can update its details.");
			} else {
				throw new UserException("Apart from admin and user no one else can update any user details.");
			}
		}

		throw new UserException("User details can be updated by someone with anonymous authentication.");

	}

	@Override
	public User updateUserRole(Integer userId, String newRole) throws UserException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			User currentUser = urepo.findByEmail(currentUserName).get();
			if(currentUser.getRole().equals("ROLE_ADMIN")) {
				Optional<User> targetUserOpt = urepo.findById(userId);
				if(targetUserOpt.isPresent()) {
					User targetUser = targetUserOpt.get();
					targetUser.setRole("ROLE_"+newRole.toUpperCase());
					return urepo.save(targetUser);
				}
				throw new UserException("Invalid user id: "+userId);
			}
			throw new UserException("Only admin can update an user's role.");
		}
		throw new UserException("User role cannot be updated by someone with anonymous authentication.");
	}
}
