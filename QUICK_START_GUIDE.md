# Quick Start Guide - Authentication Setup

## 1. Prerequisites

Before running the application, make sure:
- Java 21 is installed
- PostgreSQL is running
- Maven is installed
- Database `sjdb` is created

## 2. Database Setup

```sql
-- Create database
CREATE DATABASE sjdb;

-- Connect to database
\c sjdb

-- Create roles table (will be auto-created by Hibernate, but good to have data)
-- The application will create all tables automatically on startup
```

## 3. Build & Run Application

```bash
# Navigate to project directory
cd c:\Users\sohag\OneDrive\Desktop\sj

# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 4. Create Initial Roles (One-time Setup)

Run these requests in **Postman** or **cURL**:

### Create Customer Role
```bash
curl -X POST http://localhost:8080/api/roles \
  -H "Content-Type: application/json" \
  -d '{"name": "CUSTOMER"}'
```

**Response:**
```json
{
  "id": 1,
  "name": "CUSTOMER"
}
```

### Create Seller Role
```bash
curl -X POST http://localhost:8080/api/roles \
  -H "Content-Type: application/json" \
  -d '{"name": "SELLER"}'
```

### Create Admin Role
```bash
curl -X POST http://localhost:8080/api/roles \
  -H "Content-Type: application/json" \
  -d '{"name": "ADMIN"}'
```

## 5. User Registration

### Request
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "phone": "1234567890",
    "roleId": 1
  }'
```

### Response
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEyOTk5OTk5LCJleHAiOjE3MTMwODYzOTl9.xyz...",
  "type": "Bearer",
  "userId": 1,
  "email": "john@example.com",
  "name": "John Doe",
  "message": "User registered successfully"
}
```

**Save the token for next step!**

## 6. User Login

### Request
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Response
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEyOTk5OTk5LCJleHAiOjE3MTMwODYzOTl9.xyz...",
  "type": "Bearer",
  "userId": 1,
  "email": "john@example.com",
  "name": "John Doe"
}
```

## 7. Access Protected Resources

Use the token from login/register response:

### Example: Get User by ID
```bash
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEyOTk5OTk5LCJleHAiOjE3MTMwODYzOTl9.xyz..."
```

### Example: Create Product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Product Name",
    "description": "Product Description",
    "price": 99.99,
    "stock": 50
  }'
```

### Example: Create Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "totalPrice": 199.98,
    "status": "PENDING",
    "createdAt": "2024-04-12T10:30:00"
  }'
```

## 8. Postman Setup (Recommended)

### Import Collection

1. Open Postman
2. Create new Workspace: "SJ-API"
3. Create new Collection: "Authentication & API"

### Create Requests

**Request 1: Register**
- Method: POST
- URL: http://localhost:8080/api/auth/register
- Body (raw JSON):
```json
{
  "name": "Test User",
  "email": "test@example.com",
  "password": "test123",
  "phone": "9876543210",
  "roleId": 1
}
```

**Request 2: Login**
- Method: POST
- URL: http://localhost:8080/api/auth/login
- Body (raw JSON):
```json
{
  "email": "test@example.com",
  "password": "test123"
}
```

**Request 3: Get All Users**
- Method: GET
- URL: http://localhost:8080/api/users
- Headers:
  - Authorization: Bearer [paste-token-here]

### Set Token as Variable

1. After login request, go to "Tests" tab
2. Add:
```javascript
var jsonData = pm.response.json();
pm.globals.set("token", jsonData.token);
```

3. In Authorization header, use: `Bearer {{token}}`

## 9. Common Errors & Solutions

### Error: "User not found with email"
- **Cause:** User doesn't exist or email is incorrect
- **Solution:** Register first, then login

### Error: "Email already registered"
- **Cause:** Email is already registered
- **Solution:** Use a different email or login with existing credentials

### Error: 403 Unauthorized (No Token)
- **Cause:** Missing Authorization header
- **Solution:** Add header: `Authorization: Bearer YOUR_TOKEN`

### Error: 401 Unauthorized (Invalid Token)
- **Cause:** Token expired or corrupted
- **Solution:** Login again to get new token

### Error: 400 Bad Request
- **Cause:** Invalid JSON or missing fields
- **Solution:** Check request body format

## 10. Available API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Users (Require JWT)
- `POST /api/users` - Create user
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Products (Public Read)
- `POST /api/products` - Create product
- `GET /api/products` - Get all products (Public)
- `GET /api/products/{id}` - Get product (Public)
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### And all other endpoints...
- Orders, Customers, Sellers, Admins, Carts, Wishlists, Payments, Reviews, etc.

## 11. Token Information

**Token Type:** JWT (JSON Web Token)

**Token Format:**
```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ...[payload]...[signature]
```

**Token Expiration:** 24 hours (86400000 milliseconds)

**Token Contains:**
- Email (subject)
- Issued at time
- Expiration time
- Digital signature (HS512)

## 12. Security Notes

⚠️ **Important for Production:**

1. **Change JWT Secret**
   - Edit `application.properties`
   - Set `jwt.secret` to a secure random string

2. **Use HTTPS**
   - Always use HTTPS in production
   - Never send tokens over HTTP

3. **Token Storage**
   - Client: Store in localStorage or sessionStorage
   - Server: Never log tokens

4. **Password Security**
   - Use strong passwords (8+ chars, mixed case, numbers)
   - Passwords are hashed with BCrypt

5. **CORS Configuration**
   - Configure CORS for your frontend domain
   - Restrict allowed origins

## 13. Test Flow

```
1. Start Application
   ↓
2. Create Roles
   ↓
3. Register User
   ↓
4. Get JWT Token
   ↓
5. Use Token in Headers
   ↓
6. Access Protected Resources
```

## Troubleshooting

### Application won't start
```bash
# Check logs
mvn clean install
mvn spring-boot:run -X

# Verify Java version
java -version  # Should be 21+

# Verify PostgreSQL connection
psql -U postgres -d sjdb
```

### Database connection error
```bash
# Check PostgreSQL is running
psql -U postgres

# Verify database exists
\l

# If not, create it
CREATE DATABASE sjdb;
```

### Token validation fails
- Restart application (secret key may have changed)
- Use fresh token from login
- Check token expiration time

## Next Steps

1. ✅ Setup database and run application
2. ✅ Create roles via API
3. ✅ Register new user
4. ✅ Login and get JWT token
5. ✅ Test protected endpoints
6. ✅ Build frontend application
7. ✅ Implement token refresh mechanism

For detailed documentation, see **AUTHENTICATION_GUIDE.md**
