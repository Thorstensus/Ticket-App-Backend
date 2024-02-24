
# Ticket Application Backend

## 0. Introduction 
This application was created as the final project during the project phase of the former Green Fox Academy IT bootcamp. It serves as a RESTful Backend API for a fictional application meant for selling tickets/tours and providing travel news info. Some of the main features include:

- User Registration and Mail Verification
- Spring Security - Token based Authentication and Authorization
- CRUD Database Operations
- Putting products in Cart and creating User Orders
- Displaying Travel News

The application works with certain environmental variables. For the application to work, a .env file must be created with values contained in the provided env.sample file.

## 1. Used Dependencies

- Java Development Kit - JDK 17
- Spring Boot Data JPA
- Spring Security
- JJWT
- Jackson
- Spring Web
- Flyway
- Javax Mail
- Thymeleaf
- Java Dotenv
- Mockito

## 2. Endpoint Functionality
The endpoints are divided into 3 categories: Unsecured (for anyone), Secured (logged in users only) and Admin (logged in users with admin rights only).
### A) Unsecured Endpoints

#### POST /api/users

Allows users to register with the correct input. The password is saved as a hash in the database and the user receives a verification link in the input email.

##### Sample Request (RequestUserDTO)

```json
{
    "name": "John Doe",
    "email": "johndoe@gmail.com",
    "password": "samplepassword"
}
```
##### Sample Response (ResponseUserDTO):

```json
{
    "id": "1",
    "email": "johndoe@gmail.com",
    "isAdmin": "false"
}
```

#### POST /api/users/login

Allows the users to authenticate (login), which creates a new jwt access token and saves a longer lasting refresh token in the database if the credentials are correct. 

##### Sample Request (AuthenticationRequestDTO)

```json
{
    "email": "johndoe@gmail.com",
    "password": "samplepassword"
}
```
##### Sample Response (AuthenticationResponseDTO):

```json
{
    "status": "ok",
    "token": "9745c9ac-5827-4ed5-bc41-36269a44a866",
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJUaG9yc3RlbnN1cyIsImlhdCI6MTcwODgwNTQxNiwiZXhwIjoxNzQwMzQxNDE2LCJhdWQiOiJ3d3cuZXhhbXBsZS5jb20iLCJzdWIiOiJTYW1wbGUgVG9rZW4iLCJHaXZlbk5hbWUiOiJKb2huIiwiU3VybmFtZSI6IkRvZSIsIkVtYWlsIjoiam9obmRvZUBnbWFpbC5jb20iLCJSb2xlIjoiVXNlciJ9.BtGIVfO__iswPwdrJhShquhKnXd96TWRK5cH0oYJKJE"
}
```
#### GET /api/news OR GET /api/news/{searched}

Displays all existing news by default or news which contain the string specified in the path variable in their title or content.

##### Sample Response (GET /api/news):

```json
{
    "status": "ok",
    "token": "9745c9ac-5827-4ed5-bc41-36269a44a866",
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJUaG9yc3RlbnN1cyIsImlhdCI6MTcwODgwNTQxNiwiZXhwIjoxNzQwMzQxNDE2LCJhdWQiOiJ3d3cuZXhhbXBsZS5jb20iLCJzdWIiOiJTYW1wbGUgVG9rZW4iLCJHaXZlbk5hbWUiOiJKb2huIiwiU3VybmFtZSI6IkRvZSIsIkVtYWlsIjoiam9obmRvZUBnbWFpbC5jb20iLCJSb2xlIjoiVXNlciJ9.BtGIVfO__iswPwdrJhShquhKnXd96TWRK5cH0oYJKJE"
}
```

## 3. Additional Technical Features
- The deployed application automatically sends a working verification link to the newly registered user's mail address (handled by EmailService.class), as well as order summary and reminder emails for items left in the cart for a certain amount of time (currently set to 48 hours, handled by ScheduledTasks.class)
- The application uses standard Authentication Token and Refresh Token flow
- ExceptionServiceImpl.class handles many possible exceptions such as incorrectly filled or empty input fields, referencing non-existing products, attempting to create an account with a taken e-mail address etc.
- The Project uses Continuous Integration for style checks

## 4. Acknowledgements
I want to thank the following amazing people who were indispensable during the creation of this project:

-
-
-
-
-
-
