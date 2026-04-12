# Authentication & Security Guide

## Overview
The application uses Spring Security with JWT (JSON Web Token) for user authentication and authorization. All users must register and login to access protected resources.

## Authentication Flow

### 1. User Registration
**Endpoint:** `POST /api/auth/register`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "phone": "1234567890",
  "roleId": 3
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "userId": 1,
  "email": "john@example.com",
  "name": "John Doe",
  "message": "User registered successfully"
}
```

**Available Roles:**
- Role ID 1: Admin
- Role ID 2: Seller
- Role ID 3: Customer (Default)

### 2. User Login
**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "userId": 1,
  "email": "john@example.com",
  "name": "John Doe"
}
```

## Using JWT Token

After successful login/registration, you'll receive a JWT token. Use it in all subsequent requests:

```
Authorization: Bearer <your_jwt_token>
```

### Example API Request with JWT
```bash
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

## Public vs Protected Endpoints

### Public Endpoints (No Authentication Required)
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/roles` - Get all roles
- `GET /api/categories` - Get all categories
- `GET /api/products` - View all products
- `GET /api/search-history` - View search history

### Protected Endpoints (JWT Required)
All other endpoints require valid JWT token in Authorization header:
- `/api/users/*` - User management
- `/api/customers/*` - Customer management
- `/api/sellers/*` - Seller management
- `/api/orders/*` - Order management
- `/api/carts/*` - Cart management
- `/api/wishlist/*` - Wishlist management
- And all other resources...

## Security Features

### 1. Password Encryption
- Passwords are encrypted using BCrypt
- Never sent in plain text
- Always use HTTPS in production

### 2. JWT Token
- Token expires after 24 hours (86400000 ms)
- Generated using HS512 algorithm
- Cannot be modified without secret key

### 3. Role-Based Access Control
- Users have roles (Admin, Seller, Customer)
- Authorization based on `ROLE_*` pattern
- Can be extended for fine-grained access control

## DTO (Data Transfer Object) Structure

All API responses use DTOs to transfer data safely:

### User Registration DTO
```java
RegisterRequest {
  name: String
  email: String
  password: String
  phone: String
  roleId: Integer
}
```

### Login DTO
```java
LoginRequest {
  email: String
  password: String
}
```

### Auth Response DTO
```java
AuthResponse {
  token: String
  type: String (Bearer)
  userId: Integer
  email: String
  name: String
  message: String
}
```

## Configuration Files

### SecurityConfiguration.java
- Configures Spring Security
- Sets up JWT filter
- Defines public/protected endpoints
- Configures CORS and CSRF

### JwtTokenProvider.java
- Generates JWT tokens
- Validates tokens
- Extracts email from token
- Uses HS512 algorithm

### CustomUserDetailsService.java
- Implements UserDetailsService
- Loads user by email
- Maps role to authorities
- Used for authentication

### JwtAuthenticationFilter.java
- Intercepts HTTP requests
- Extracts token from Authorization header
- Validates token
- Sets authentication in security context

## Application Properties

```properties
# JWT Configuration in application.properties
jwt.secret=mySecretKeyForJWTTokenGenerationAndValidationPurposeOnly
jwt.expiration=86400000  # 24 hours in milliseconds
```

## Best Practices

1. **Token Management**
   - Store token securely on client (localStorage, sessionStorage)
   - Always include token with Authorization header
   - Implement token refresh mechanism for production

2. **Password Security**
   - Use strong passwords (8+ characters)
   - Mix uppercase, lowercase, numbers, special characters
   - Never hardcode passwords

3. **HTTPS Usage**
   - Always use HTTPS in production
   - Never transmit tokens over HTTP

4. **Token Expiration**
   - Implement logout functionality
   - Clear token on client-side
   - Handle expired token errors

## Testing Authentication

### Using cURL
```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com","password":"pass123","phone":"9876543210","roleId":3}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"pass123"}'

# Access Protected Resource
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Using Postman
1. Set request type to POST/GET
2. Set URL for desired endpoint
3. For Auth endpoints: Add JSON body
4. For Protected endpoints: 
   - Go to "Authorization" tab
   - Select "Bearer Token"
   - Paste your JWT token
   - Send request

## Error Handling

### Common Errors

| Status | Error | Cause |
|--------|-------|-------|
| 400 | Bad Request | Invalid request body |
| 401 | Unauthorized | Invalid credentials or missing token |
| 403 | Forbidden | Token expired or insufficient permissions |
| 404 | Not Found | Resource not found |
| 500 | Internal Server Error | Server error |

## Summary

| Component | Purpose |
|-----------|---------|
| AuthController | REST endpoints for login/register |
| AuthService | Business logic for authentication |
| JwtTokenProvider | Token generation and validation |
| CustomUserDetailsService | Load user details from database |
| JwtAuthenticationFilter | Extract and validate JWT tokens |
| SecurityConfiguration | Spring Security setup |
| DTOs | Data transfer objects for API |
