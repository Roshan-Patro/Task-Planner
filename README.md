<h1 align="center">Task-Planner RESTful Web Services</h1>

<p align="center">
<img src="https://m.media-amazon.com/images/I/71rbZuW4zJL._SX425_.jpg" width="380px" height="350px">
</p>

<img src="https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png" width="100%">

## Project Description
This project contains RESTful APIs to perform all relevant operations for a Task Planner or Task Scheduler application. The types of end users are Admin and User. Both have different sorts of accesses and permissions when it comes to performing operations in the system.

### Admin Features:
- Admins can register themselves and log in with valid credentials.
- Admins can retrieve details like assigned tasks of a user, added tasks of a sprint, all existing tasks, all existing sprints, all existing users, etc.
- Admins can create tasks and sprints.
- Admins can add tasks to sprints and assign tasks to users.
- Admins can update user, task, and sprint details.
- Admins can delete users, tasks, and sprints.
- Admins can update the role (Admin/User) of a user.
### User Features:
- Users can register and log in with valid credentials.
- Users can retrieve their own assigned tasks.
- Users can update their details.
- Users can create tasks and assign them to other users.
- Users can change the assignee, sprint, start date, and end date of a task.
- Users can delete tasks created by them.

The whole application with all the endpoints is protected and secured with Spring Security. With Spring Security, the authentication and authorization mechanisms are made robust using JSON Web Token to ensure that the end users get access to the functionalities they are authorized for.

## Project Structure
![Task-Planner-Project_Structure](https://user-images.githubusercontent.com/107461385/232118428-feccb72d-0c90-423e-bf9d-b02dc6f3b851.PNG)

## Technologies and Tools Used
- Java
- Spring Framework
- Spring Boot
- Spring Data JPA
- Hibernate
- SQL
- Lombok
- PostMan
- Swagger
- MySQL

## ER Diagram

## Modules and Services
- User Module
- Task Module
- Sprint Module

### User Module - Services
- Register a user
- Get logged-in user details
- Get assigned tasks of a user
- Delete a user by id
- Update user details
- Update user role

<!-- ## API Documentation -->

## API Root Endpoint
```
https://localhost:8844/
```
```
http://localhost:8844/swagger-ui/index.html
```

## Usage Instructions

<!-- ## SQL Queries -->

## Feedback


