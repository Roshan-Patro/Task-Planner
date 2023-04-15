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
- Spring Security
- Spring Data JPA
- Hibernate
- SQL
- Lombok
- PostMan
- Swagger
- MySQL

## Entity Relationship (ER) Diagram
<p align="center">
<img src="https://user-images.githubusercontent.com/107461385/232238989-76b99442-c52b-4cba-b3ce-8a3442c20a15.png">
</p>
:bulb: Click on the ER Diagram to open it in a new tab.

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

### Task Module - Services
- Create a task
- Assign a task to a user
- Change the assignee of a task
- Change the sprint of a task
- Change the status of a task
- Change the priority of a task
- Change the start date of a task
- Change the end date of a task
- Change the type of a task
- Update a task (many attributes at a time)
- Get all existing tasks
- Get all existing tasks with pagination
- Delete a task by id

### Sprint Module - Services
- Create a sprint
- Add a task to a sprint
- Get added tasks of a sprint
- Delete a sprint by id
- Update a sprint description

<!-- ## API Documentation -->

## API Root Endpoint
```
https://localhost:8844/
```
```
http://localhost:8844/swagger-ui/index.html
```

## Usage Instructions
- Before running the server, you should update the database configuration details inside the [application.properties](https://github.com/Roshan-Patro/Task-Planner/blob/main/TaskPlanner/src/main/resources/application.properties) file. 
- Update the port number, username and password as per your local database configuration.
- The basic details of the application.properties file are given below for reference.
```
#Server's the port number (Customized)
server.port=8844

#Database related properties
spring.datasource.url=jdbc:mysql://${BD_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:taskplanner}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:Patro@1997}

#ORM s/w specific properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

<!-- ## SQL Queries -->

## Feedback✍️
I would indeed appreciate your feedback and suggestions if any. Each single one of them matters ❣️. Please, let me know your feedback, suggestions, or queries through my LinkedIn. [Let's connect...](https://www.linkedin.com/in/t-roshan-kumar-patro/):smiley:

