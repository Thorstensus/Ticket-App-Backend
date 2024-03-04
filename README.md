
# Ticket Application Backend

## 0. Introduction 
This application was created as the final project during the project phase of the former Green Fox Academy IT bootcamp. It serves as a RESTful Backend API for a fictional application meant for selling tickets/tours and providing travel news info. Some of the main features include:

- User Registration and Mail Verification
- Spring Security - Token based Authentication and Authorization in Request Headers
- CRUD Database Operations
- Putting products in Cart and creating User Orders
- Displaying Travel News

The project was built in IntelliJ and works with certain environmental variables. For the application to work, a .env file must be created with values contained in the provided env.sample file.

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
    "name" : "John Doe",
    "email" : "johndoe@gmail.com",
    "password" : "samplepassword"
}
```
##### Sample Response (ResponseUserDTO):

```json
{
    "id" : 1,
    "email" : "johndoe@gmail.com",
    "isAdmin" : "false"
}
```

#### POST /api/users/login

Allows the users to authenticate (login), which creates a new jwt access token and saves a longer lasting refresh token in the database if the credentials are correct. 

##### Sample Request (AuthenticationRequestDTO)

```json
{
    "email" : "johndoe@gmail.com",
    "password" : "samplepassword"
}
```
##### Sample Response (AuthenticationResponseDTO):

```json
{
    "status" : "ok",
    "token" : "9745c9ac-5827-4ed5-bc41-36269a44a866",
    "accessToken" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzA4ODE2OTQ5LCJleHAiOjE3MDg4MjA1NDl9.a1vbJ_ifhz_IQafvSTLWncsalW-xYC4M2EL1wB0koKQ"
}
```
#### GET /api/news OR GET /api/news/{searched}

Displays all existing news by default or news which contain the string specified in the path variable in their title or content.

##### Sample Response (GET /api/news):

```json
{
    "articles": [
        {
            "id" : 1,
            "title" : "random title 1",
            "content" : "random content one",
            "publishDate" : "2024-02-24"
        },
        {
            "id" : 2,
            "title" : "random title 2",
            "content" : "random content two",
            "publishDate" : "2024-02-25"
        }
    ]
}
```
##### Sample Response (GET /api/news/one):

```json
{
    "articles": [
        {
            "id" : 1,
            "title" : "random title 1",
            "content" : "random content one",
            "publishDate" : "2024-02-24"
        }
    ]
}
```
#### GET /api/email-verification/{token}

When the link is accessed (from user's mail address inbox), their account becomes verified.

##### Expected Response:

```json
"User verified"
```
#### POST /api/refresh-token

When request with a valid refresh token is sent, a new access token is generated. If the refresh token is expired, it is removed from the database and the user is encouraged to log in again.

##### Sample Request (RefreshTokenRequestDTO)

```json
{
    "token" : "9745c9ac-5827-4ed5-bc41-36269a44a866"
}
```
##### Sample Response (AuthenticationResponseDTO):

```json
{
    "status" : "ok",
    "token" : "9745c9ac-5827-4ed5-bc41-36269a44a866",
    "accessToken" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzA4ODE2OTQ5LCJleHAiOjE3MDg4MjA1NDl9.a1vbJ_ifhz_IQafvSTLWncsalW-xYC4M2EL1wB0koKQ"
}
```

### B) Secured Endpoints

#### POST /api/cart

When an existing item id is sent, a single quantity of the chosen item is added into the logged in user's cart. Further requests increment the count in cart by one.

##### Sample Request (CartRequestDTO, logged in user id = 1)

```json
{
    "productId" : 3
}
```

##### Sample Response (CartResponseDTO):

```json
{
    "id" : 1,
    "productId" : 3
}
```

#### PATCH /api/cart

Modifies the logged in user's cart to contain a specific quantity of the chosen product given that the cart already contains the specified item. The response contains the newly updated cart contents.

##### Sample Request (ModifyCartRequestDTO)

```json
{
    "productId" : 3,
    "quantity" : 5
}
```

##### Sample Response (ModifyCartResponseDTO):

```json
{
  "cartProducts": [
    {
      "id": 2,
      "product": "Ticket 2",
      "quantity": 2
    },
    {
      "id": 3,
      "product": "Ticket 3",
      "quantity": 5
    }
  ]
}
```

#### DELETE /api/cart

Deletes all the cart contents for the logged in user.

##### *A request without specific body*

##### Sample Response 1 (provided that the cart contained items):

```json
"The cart has been deleted"
```

##### Sample Response 2 (provided that the cart contained no items):

```json
"No cart to delete"
```

#### POST /api/orders

Given that the logged in user's cart contains items, converts all of the cart contents into a complete order. 

NOTE: An automated order summary mail is sent to the user's e-mail address at this point.

##### Sample Response (ResponseOrderDTO):

```json
{
    "id": 1,
    "status": null,
    "expiry": null,
    "products": [
        {
            "productId": 2,
            "quantity": 2
        },
        {
            "productId": 3,
            "quantity": 5
        }
    ]
}
```

#### GET /api/orders

Lists all of the orders previously created by the logged in user.

##### Sample Response (ResponseOrderSummaryDTO):

```json
{
    "orders": [
        {
            "id": 1,
            "status": null,
            "expiry": null,
            "products": [
                {
                    "productId": 2,
                    "quantity": 2
                },
                {
                    "productId": 3,
                    "quantity": 5
                }
            ]
        },
        {
            "id": 2,
            "status": null,
            "expiry": null,
            "products": [
                {
                    "productId": 1,
                    "quantity": 1
                }
            ]
        }
    ]
}
```

### C) Admin Endpoints

#### POST /api/admin/news-add
Used for creating a new news entity in the database.

##### Sample Request (CreateNewsRequestDTO)

```json
{
    "title" : "Click Here Please!!!",
    "content" : "sample clickbait"
}
```

##### Sample Response (CreateNewsResponseDTO):

```json
{
    "id" : 1,
    "title" : "Click Here Please!!!",
    "content" : "sample clickbait"
}
```

#### GET /api/admin/products
Used to list out all the products saved in the database (which are being offered for sale).

##### Sample Response (CreateNewsResponseDTO):

```json
{
    "products": [
        {
            "id": 1,
            "name": "Ticket 1",
            "price": 1.0,
            "duration": "10 hours",
            "description": "A ticked with a large number 1 printed on it",
            "type": "Ticket"
        },
        {
            "id": 2,
            "name": "Ticket 2",
            "price": 2.0,
            "duration": "20 hours",
            "description": "A ticked with a large number 1 printed on it",
            "type": "Ticket"
        },
        {
            "id": 3,
            "name": "Ticket 3",
            "price": 3.0,
            "duration": "30 hours",
            "description": "A ticked with a large number 1 printed on it",
            "type": "Ticket"
        }
    ]
}
```

#### POST /api/admin/products
Creates new products in the database.

##### Sample Request (RequestProductDTO):

```json
{
  "name" : "Ticket 1",
  "price" : 1,
  "duration" : 10,
  "description" : "A ticked with a large number 1 printed on it",
  "type" : "Ticket"
}
```

##### Sample Response (ResponseProductDTO):

```json
{
    "id": 1,
    "name": "Ticket 1",
    "price": 1.0,
    "duration": "10 hours",
    "description": "A ticked with a large number 1 printed on it",
    "type": "Ticket"
}
```

#### PATCH /api/admin/products/{productId}
Modifies the details of the product with the id specified in the request parameter.

##### Sample Request to /api/admin/products/1 (RequestProductDTO):

```json
{
  "name" : "Modified Ticket 1",
  "price" : 100,
  "duration" : 100,
  "description" : "A modified ticket",
  "type" : "Ticket (2nd Type)"
}
```

##### Sample Response (ResponseProductDTO):

```json
{
    "id": 1,
    "name": "Modified Ticket 1",
    "price": 100.0,
    "duration": "100 hours",
    "description": "A modified ticket",
    "type": "Ticket (2nd Type)"
}
```


#### POST /api/admin/product-types
Creates new product type in the database.

##### Sample Request (ProductTypeRequestDTO):

```json
{
  "name" : "Ticket (2nd type)",
}
```

##### Sample Response (ProductTypeResponseDTO):

```json
{
    "id": 2,
    "name": "Ticket (2nd type)"
}
```

#### GET /api/admin/product-types/stats
Returns statistics of purchases based on product type.

##### Sample Response (ResponseProductTypeStatisticsDTO):

```json
[
    {
        "productTypeName": "Ticket (2nd Type)",
        "quantitySold": 7,
        "totalPrice": 700.0
    },
    {
        "productTypeName": "Ticket",
        "quantitySold": 4,
        "totalPrice": 79.96
    }
]
```

#### PATCH /api/admin/users/{userId}
Modifies specific user details (name, email, password). The new password is hashed.

##### Sample Request to /api/admin/users/1 (RequestUserDTO):

```json
{
    "name" : "John Doe the 2nd",
    "email" : "secondjohndoe@gmail.com",
    "password" : "samplenewpassword"
}
```

##### Sample Response (PatchResponseUserDTO):

```json
{
    "id": 1,
    "name": "John Doe the 2nd",
    "email": "secondjohndoe@gmail.com"
}
```


## 3. Additional Technical Features
- The deployed application automatically sends a working verification link to the newly registered user's mail address (handled by EmailService.class), as well as order summary and reminder emails for items left in the cart for a certain amount of time (currently set to 48 hours, handled by ScheduledTasks.class)
- The application uses standard Authentication Token and Refresh Token flow and the user info is retrieved from the Header Token
- ExceptionServiceImpl.class handles many possible exceptions such as incorrectly filled or empty input fields, referencing non-existing products, attempting to create an account with a taken e-mail address etc.
- The Project uses Continuous Integration for Style Checks (Google Java Style)
- The Project implements Flyway Database Migration

## 4. Acknowledgements
I want to thank the following amazing people who were indispensable during the creation of this project:

- https://github.com/chabyie
- https://github.com/scepan66
- https://github.com/belaerdos
- https://github.com/PPetr1

And last but not least, our amazing mentor and team lead:

- https://github.com/bekobarna
