# Recipe Management System API

## Table Of Contents
- [Frameworks and Language Used](#frameworks-and-language-used)
- [Data Flow](#data-flow)
  - [Controller](#controller)
  - [Services](#services)
  - [Repository](#repository)
  - [Utility (EmailHandler and PasswordEncrypter)](#utility-emailhandler-and-passwordencrypter)
  - [Database Design](#database-design)
- [Data Structure Used](#data-structure-used)
- [Project Summary](#project-summary)
- [Getting Started](#getting-started)
- [Testing Endpoints](#testing-endpoints)
- [License](#license)

## Frameworks and Language Used
- Spring Boot (Java)
- Spring Data JPA
- Spring Web
- Lombok
- Hibernate
- H2 Database (for local testing)

## Data Flow

### Controller
- `RecipeController`: Handles requests related to recipe management.
- `CommentController`: Handles requests related to commenting on recipes.
- `UserController`: Handles requests related to user registration and authentication.

### Services
- `RecipeService`: Manages recipe-related business logic and interactions with the repository.
- `CommentService`: Manages comment-related business logic and interactions with the repository.
- `UserService`: Manages user-related business logic and interactions with the repository.

### Repository
- `AuthRepo`: Interface extending JpaRepository for managing authentication tokens.
- `CommentRepo`: Interface extending JpaRepository for managing comments.
- `RecipeRepo`: Interface extending JpaRepository for managing recipes.
- `UserRepo`: Interface extending JpaRepository for managing users.

### Utility (EmailHandler and PasswordEncrypter)
- `EmailHandler`: Handles sending emails for authentication tokens.
- `PasswordEncrypter`: Provides methods for encrypting passwords using MD5 hashing.

### Database Design
- `User`: Represents user information, including username, email, and encrypted password.
- `Recipe`: Represents recipe information, including recipe name, ingredients, instructions, and cooking time.
- `Comment`: Represents comments on recipes, including the comment body, timestamp, and relationships to users and recipes.
- `AuthenticationToken`: Represents authentication tokens associated with user sessions.

### DataToObject
The `DataToObject` package contains classes used for data transfer between the API endpoints and the service layer.
- SignInInput

    - This class represents the data structure for user sign-in input.
    - It contains the following fields:
        - `email` (String): The user's email for sign-in.
        - `password` (String): The user's password for sign-in.

- SignUpOutput

    - This class represents the data structure for the output of the user sign-up process.
    - It contains the following fields:
        - `signUpStatus` (boolean): A boolean value indicating whether the sign-up process was successful.
        - `signUpStatusMessage` (String): A message providing information about the sign-up process result.


These data transfer objects are used in the API endpoints to send and receive data between the client and server. They help maintain a clear separation between the presentation layer and the service layer of the application.

## Data Structure Used
- List: Used for storing and managing lists of comments for each recipe.
- Optional: Used for handling nullable values, especially when querying the database and returning optional results.


## Project Summary
This project is a Recipe Management System API developed using Spring Boot and Spring Data JPA. It allows users to register, sign in, and manage their recipes and comments.

## Getting Started
To run the API locally, follow these steps:
1. Clone the repository.
2. Install Java and Maven on your system.
3. Set up your IDE or build tools (Eclipse, IntelliJ, or VS Code).
4. Import the project as a Maven project.
5. Set up a local database (H2 database is used by default for local testing).
6. Update database configuration in the application properties file.
7. Run the application.

## Testing Endpoints
Use tools like Postman or cURL to test the API endpoints.
1. Register a new user using the `/user/register` POST endpoint.
2. Sign in to get an authentication token using the `/user/signin` POST endpoint.
3. Use the authentication token for authorized access to other endpoints.
4. Manage recipes using the `/recipes` endpoints (create, read, update, and delete).
5. Comment on recipes using the `/comments` endpoints (add, update, and delete).

## License
This project is licensed under the [MIT License](LICENSE).
