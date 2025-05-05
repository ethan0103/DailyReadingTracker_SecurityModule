# Security Module Documentation for Daily Reading Tracker

## Overview
The **Security Module** in the **Daily Reading Tracker** application is designed to ensure that users' data is protected both at rest and in transit. This module includes user authentication, data encryption, password hashing, JWT (JSON Web Token) authentication, and integrity verification for user activity logs. The security module also integrates role-based access control, allowing only authorized users (Admin and User) to access specific functionalities.

This documentation provides an overview of the architecture, functionalities, and usage of the security module. It will help team members understand the module and how to integrate it with other parts of the application.

---

## Security Module Architecture

The security module is composed of several components, each responsible for specific tasks:

1. **User Authentication**
    - **JWT Authentication**: Handles user login, authentication, and the generation of JWT tokens used for securing requests.
    - **Password Management**: Includes encryption of user passwords for storage and secure verification during login.

2. **Data Encryption & Integrity**
    - **Encryption**: Ensures that sensitive data, such as user information and logs, is encrypted when stored or transmitted.
    - **Data Integrity**: Provides mechanisms for verifying that data has not been tampered with, using checksum methods.

3. **Role-Based Access Control (RBAC)**
    - Defines the roles of users (Admin and User) and restricts access based on their roles.

4. **Security Filters and Entry Points**
    - Configures security filters for request validation, error handling, and session management.

---

## Files and Their Responsibilities

### 1. **SecurityConfig.java**
- **Role**: Configures Spring Security settings, including JWT filters, password encoding, and session management.
- **Key Responsibilities**:
    - Configures role-based access for various endpoints (e.g., `/api/admin/**` is restricted to Admin users).
    - Disables CSRF and CORS for simplicity (you can re-enable them if needed).
    - Registers JWT authentication filter for intercepting requests and validating tokens.
    - Configures `UserDetailsService` for loading user data and `PasswordEncoder` for password hashing.
    - Handles session management to restrict concurrent logins.

**Usage**:
- Integrate this file with Spring Boot's security configuration. It ensures secure communication between the front end and backend, enforcing role-based restrictions.

---

### 2. **AuthController.java**
- **Role**: Handles user registration and login requests.
- **Key Responsibilities**:
    - **User Registration**: Registers new users by accepting a username and password, storing the data securely after encrypting the password.
    - **User Login**: Verifies user credentials, generates a JWT token on successful login, and returns it to the client.
- **Usage**:
    - Use this controller when implementing user registration and login functionalities. The `registerUser` method ensures the user is securely registered, and the `loginUser` method verifies the credentials and returns a JWT token.

---

### 3. **User.java**
- **Role**: Represents the User entity in the database.
- **Key Responsibilities**:
    - Stores user data such as `username`, `password`, and `role`.
    - Provides a method to retrieve authorities based on user roles (USER or ADMIN).
- **Usage**:
    - This entity is used in the user management part of the application. The `role` field is critical for implementing role-based access control.

---

### 4. **UserRepository.java**
- **Role**: Provides CRUD operations for the User entity.
- **Key Responsibilities**:
    - Includes methods for querying users by username (`findByUsername`) and checking if a username exists (`existsByUsername`).
- **Usage**:
    - This repository is used in the user service and other parts of the application for interacting with the database.

---

### 5. **JwtAuthenticationEntryPoint.java**
- **Role**: Handles unauthorized access.
- **Key Responsibilities**:
    - Configures the response when a user tries to access a restricted resource without proper authentication, returning a 401 Unauthorized response.
- **Usage**:
    - This class is referenced in `SecurityConfig.java` to handle unauthorized access attempts.

---

### 6. **JwtAuthenticationFilter.java**
- **Role**: A filter that processes JWT tokens in HTTP requests.
- **Key Responsibilities**:
    - Extracts the JWT token from the `Authorization` header of incoming requests.
    - Validates the token using the `JwtService` and authenticates the user by setting the security context if the token is valid.
- **Usage**:
    - The filter is added to the Spring Security filter chain in `SecurityConfig.java` and automatically processes every incoming request.

---

### 7. **EncryptionServiceImpl.java**
- **Role**: Implements the EncryptionService interface, providing encryption and decryption functionalities.
- **Key Responsibilities**:
    - Encrypts data using a specified secret key.
    - Decrypts encrypted data using the same secret key.
    - Generates a new secret key for encryption.
- **Usage**:
    - Used for encrypting sensitive data before storing it in the database or transmitting it across networks. This service is particularly useful when implementing data encryption for user activity logs.

---

### 8. **JwtServiceImpl.java**
- **Role**: Implements the `JwtService` interface for handling JWT operations.
- **Key Responsibilities**:
    - Generates JWT tokens with a secret key and expiration time.
    - Extracts the username from a JWT token.
    - Validates JWT tokens.
- **Usage**:
    - This service is called in `AuthController.java` during the login process to generate JWT tokens. It is also used by the JWT filter for token validation.

---

### 9. **UserServiceImpl.java**
- **Role**: Implements user-related operations, such as user registration and password verification.
- **Key Responsibilities**:
    - Registers new users by encoding their password.
    - Verifies user passwords during login.
- **Usage**:
    - Used by `AuthController.java` to handle user registration and login functionalities.

---

### 10. **CustomUserDetailsService.java**
- **Role**: Implements the `UserDetailsService` interface, which is responsible for loading user-specific data.
- **Key Responsibilities**:
    - Loads user details by username and returns them as `UserDetails` for authentication.
- **Usage**:
    - This service is used in `SecurityConfig.java` to load user details during authentication.

---

### 11. **EncryptionService.java**
- **Role**: Interface for encryption services.
- **Key Responsibilities**:
    - Defines methods for encrypting, decrypting, and generating secret keys.
- **Usage**:
    - This interface should be implemented by the `EncryptionServiceImpl.java` class.

---

### 12. **IntegrityService.java**
- **Role**: Interface for integrity services, providing checksum functionalities.
- **Key Responsibilities**:
    - Defines methods for generating and verifying checksums for data integrity.
- **Usage**:
    - This interface is implemented in `IntegrityServiceImpl.java` and should be used wherever data integrity needs to be verified (e.g., for reading logs).

---

### 13. **EncryptionUtil.java**
- **Role**: Provides utility methods for encryption and decryption using AES.
- **Key Responsibilities**:
    - Encrypts and decrypts data using a specified secret key.
    - Generates secret keys for encryption.
- **Usage**:
    - This utility class is used by `EncryptionServiceImpl` to perform encryption and decryption.

---

### 14. **IntegrityUtil.java**
- **Role**: Provides utility methods for checksum generation and verification using SHA-256.
- **Key Responsibilities**:
    - Generates checksums for data.
    - Verifies the integrity of data using checksums.
- **Usage**:
    - This utility is used by `IntegrityServiceImpl` to ensure the integrity of data logs.

---

### 15. **JwtService.java**
- **Role**: JwtService.java is a service responsible for generating, validating, and extracting information from JWT (JSON Web Tokens). It plays a critical role in securing the application by ensuring that each user request is authenticated via a valid JWT token.
- **Key Responsibilities**:
    - JWT Token Generation: Creates a JWT token for a user when they log in. This token contains the username and an expiration timestamp.
    - Extract Username: Retrieves the username embedded within the JWT token.
    - Validate JWT Token: Verifies the authenticity of a JWT token to ensure it is valid and not tampered with.
- **Usage**:
    - This service is used in:
      - AuthController.java: During the login process, it generates a JWT token after successfully authenticating the user. 
      - JwtAuthenticationFilter.java: Used to validate JWT tokens in the request headers for each incoming request.

## How to Use the Security Module

### Integrating with Other Parts of the Application
1. **User Registration**: When implementing user registration, use `AuthController.java` to create new users. The password will automatically be encrypted before being saved.

2. **User Authentication**: During login, the `AuthController` will verify the user's credentials and generate a JWT token for the authenticated user.

3. **Role-Based Access**: The `SecurityConfig.java` file enforces role-based access control. Admin routes (e.g., `/api/admin/**`) require the user to have the `ADMIN` role, while user routes require the `USER` role.

4. **Data Encryption**: Use `EncryptionServiceImpl.java` to encrypt sensitive user data, such as passwords and personal information. This ensures data protection while storing or transmitting it.

5. **Data Integrity**: Use `IntegrityServiceImpl.java` to ensure that the reading logs have not been tampered with. Use the `generateChecksum` and `verifyChecksum` methods to verify integrity.

---

## Conclusion

This security module provides essential features like user authentication, encryption, data integrity, and role-based access control. It is designed to be flexible and easily integrated into the larger Daily Reading Tracker application. By using these components, you can ensure that the user's data is handled securely and that access is properly managed according to user roles.

If you need further clarification or have any specific questions while integrating the security module with other parts of the project, feel free to reach out.
