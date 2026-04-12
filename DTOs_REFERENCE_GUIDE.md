# DTOs Reference Guide

## All DTOs Summary

### Purpose of DTOs (Data Transfer Objects)
- Transfer data between layers safely
- Hide sensitive information (like passwords)
- Provide clear API contracts
- Enable data validation
- Separate entity structure from API response

---

## Entity DTOs

### 1. RoleDTO
```java
{
  id: Integer,
  name: String
}
```
**Usage:** Managing user roles (ADMIN, SELLER, CUSTOMER)

### 2. CategoryDTO
```java
{
  id: Integer,
  name: String
}
```
**Usage:** Product categories

### 3. UserDTO
```java
{
  id: Integer,
  name: String,
  email: String,        // Unique
  password: String,     // BCrypt encrypted
  phone: String,
  roleId: Integer,      // Foreign key to Role
  createdAt: LocalDateTime
}
```
**Usage:** User-related operations

### 4. CustomerDTO
```java
{
  id: Integer,
  userId: Integer,      // Foreign key to User
  loyaltyPoints: Integer
}
```
**Usage:** Customer loyalty program

### 5. SellerDTO
```java
{
  id: Integer,
  userId: Integer,      // Foreign key to User
  shopName: String,
  tradeLicense: String,
  bankAccount: String,
  status: String,       // APPROVED, PENDING, REJECTED
  createdAt: LocalDateTime
}
```
**Usage:** Seller shop management

### 6. AdminDTO
```java
{
  id: Integer,
  userId: Integer,      // Foreign key to User
  permissions: String   // JSON or comma-separated
}
```
**Usage:** Admin permission management

### 7. AddressDTO
```java
{
  id: Integer,
  userId: Integer,      // Foreign key to User
  addressLine: String,
  city: String,
  postalCode: String,
  country: String
}
```
**Usage:** User addresses

### 8. ProductDTO
```java
{
  id: Integer,
  name: String,
  description: String,
  price: BigDecimal,    // Precision: 10, Scale: 2
  stock: Integer
}
```
**Usage:** Product catalog

### 9. CartDTO
```java
{
  id: Integer,
  userId: Integer       // Foreign key to User
}
```
**Usage:** Shopping cart management

### 10. CartItemDTO
```java
{
  id: Integer,
  cartId: Integer,      // Foreign key to Cart
  productId: Integer,   // Foreign key to Product
  quantity: Integer
}
```
**Usage:** Items in shopping cart

### 11. WishlistDTO
```java
{
  id: Integer,
  userId: Integer       // Foreign key to User
}
```
**Usage:** User wishlist

### 12. WishlistItemDTO
```java
{
  id: Integer,
  wishlistId: Integer,  // Foreign key to Wishlist
  productId: Integer    // Foreign key to Product
}
```
**Usage:** Products in wishlist

### 13. OrderDTO
```java
{
  id: Integer,
  userId: Integer,      // Foreign key to User
  totalPrice: BigDecimal,
  status: String,       // PENDING, CONFIRMED, SHIPPED, DELIVERED
  createdAt: LocalDateTime
}
```
**Usage:** Order management

### 14. OrderItemDTO
```java
{
  id: Integer,
  orderId: Integer,     // Foreign key to Order
  productId: Integer,   // Foreign key to Product
  quantity: Integer
}
```
**Usage:** Products in orders

### 15. ProductImageDTO
```java
{
  id: Integer,
  productId: Integer,   // Foreign key to Product
  imageUrl: String      // URL or file path
}
```
**Usage:** Product images

### 16. PaymentDTO
```java
{
  id: Integer,
  orderId: Integer,     // Foreign key to Order
  paymentMethod: String,     // CARD, BANK_TRANSFER, CASH
  paymentStatus: String,     // PENDING, COMPLETED, FAILED
  transactionId: String      // Payment gateway ID
}
```
**Usage:** Payment processing

### 17. ComplaintDTO
```java
{
  id: Integer,
  userId: Integer,      // Foreign key to User
  orderId: Integer,     // Foreign key to Order
  subject: String,
  message: String,      // Text area
  status: String,       // OPEN, IN_PROGRESS, RESOLVED
  createdAt: LocalDateTime
}
```
**Usage:** Customer complaints

### 18. ReviewDTO
```java
{
  id: Integer,
  userId: Integer,      // Foreign key to User
  productId: Integer,   // Foreign key to Product
  rating: Integer,      // 1-5
  comment: String,      // Review text
  createdAt: LocalDateTime
}
```
**Usage:** Product reviews

### 19. RecommendationDTO
```java
{
  id: Integer,
  userId: Integer,      // Foreign key to User
  productId: Integer    // Foreign key to Product
}
```
**Usage:** Personalized recommendations

### 20. SellerOrderDTO
```java
{
  id: Integer,
  sellerId: Integer,    // Foreign key to Seller
  orderId: Integer,     // Foreign key to Order
  amount: BigDecimal,   // Seller's earnings
  status: String,       // PENDING, SHIPPED, DELIVERED
  createdAt: LocalDateTime
}
```
**Usage:** Seller order tracking

### 21. SearchHistoryDTO
```java
{
  id: Integer,
  userId: Integer,      // Foreign key to User
  query: String,        // Search keyword
  createdAt: LocalDateTime
}
```
**Usage:** User search history tracking

---

## Authentication DTOs

### 22. LoginRequest
```java
{
  email: String,        // Required
  password: String      // Required
}
```
**Usage:** `/api/auth/login`

**Example:**
```json
{
  "email": "user@example.com",
  "password": "securePassword123"
}
```

### 23. RegisterRequest
```java
{
  name: String,         // Required
  email: String,        // Required, unique
  password: String,     // Required
  phone: String,        // Optional
  roleId: Integer       // Required (1=Admin, 2=Seller, 3=Customer)
}
```
**Usage:** `/api/auth/register`

**Example:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "phone": "1234567890",
  "roleId": 3
}
```

### 24. AuthResponse
```java
{
  token: String,        // JWT token
  type: String,         // Always "Bearer"
  userId: Integer,      // User ID
  email: String,        // User email
  name: String,         // User name
  message: String       // Success/error message
}
```
**Usage:** Response from `/api/auth/login` and `/api/auth/register`

**Example:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEyOTk5OTk5LCJleHAiOjE3MTMwODYzOTl9.xyz...",
  "type": "Bearer",
  "userId": 1,
  "email": "john@example.com",
  "name": "John Doe",
  "message": "Login successful"
}
```

---

## DTO Usage Patterns

### Create Operation
Request uses DTO (without ID):
```
POST /api/users
{
  "name": "John",
  "email": "john@example.com",
  ...
}
```

Response includes ID:
```json
{
  "id": 1,
  "name": "John",
  "email": "john@example.com",
  ...
}
```

### Update Operation
```
PUT /api/users/1
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  ...
}
```

### List Operation
```
GET /api/users
```

Returns array of DTOs:
```json
[
  {
    "id": 1,
    "name": "John",
    ...
  },
  {
    "id": 2,
    "name": "Jane",
    ...
  }
]
```

---

## Data Types Reference

| Type | Java | Example |
|------|------|---------|
| Text | String | "John Doe" |
| Number | Integer | 42 |
| Decimal | BigDecimal | 99.99 |
| Date | LocalDateTime | "2024-04-12T10:30:00" |
| Boolean | Boolean | true |
| Array | List | [1, 2, 3] |

---

## Security in DTOs

### Password Handling
- **Never** send password in response
- User DTO doesn't include password
- Only LoginRequest and RegisterRequest have password

### Sensitive Fields
Should NOT be in DTOs:
- Bank account full numbers (except last 4 digits)
- Credit card information
- Social security numbers
- System IDs

### Safe Fields
Can be in DTOs:
- User names
- Email addresses
- Addresses
- Phone numbers
- Order history

---

## DTO Validation

### Email Format
```
Must match: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
Example: user@example.com
```

### Phone Format
```
Optional, can be any format
Examples: 1234567890, +1-234-567-8900, 123 456 7890
```

### Password Requirements
```
Minimum 6 characters recommended
Examples: pass123, MySecure@Pass, 12345678
```

### BigDecimal (Price/Amount)
```
Precision: 10 digits total
Scale: 2 decimal places
Range: 0.00 to 99999999.99
Example: 99.99, 1000000.00
```

---

## Complete DTO Count

| Category | Count | Files |
|----------|-------|-------|
| Entity DTOs | 21 | *DTO.java |
| Authentication | 3 | LoginRequest, RegisterRequest, AuthResponse |
| **Total** | **24** | **24 DTO classes** |

---

## DTO Best Practices

✅ **DO:**
- Keep DTOs simple and flat
- Use clear field names
- Include validation annotations (in production)
- Document field purposes
- Version DTOs for API versioning

❌ **DON'T:**
- Include unnecessary fields
- Return entity objects directly
- Expose internal logic
- Use complex nested structures
- Ignore data validation

---

## API Response Templates

### Success Response
```json
{
  "id": 1,
  "field1": "value1",
  "field2": "value2",
  "timestamp": "2024-04-12T10:30:00"
}
```

### Error Response
```json
{
  "message": "Error description",
  "status": 400,
  "timestamp": "2024-04-12T10:30:00"
}
```

### List Response
```json
[
  {
    "id": 1,
    "field1": "value1"
  },
  {
    "id": 2,
    "field1": "value2"
  }
]
```

---

## Conversion (Entity ↔ DTO)

In production, add mapper methods:

```java
// Entity to DTO
public static UserDTO toDTO(User user) {
  UserDTO dto = new UserDTO();
  dto.setId(user.getId());
  dto.setName(user.getName());
  dto.setEmail(user.getEmail());
  // ... other fields
  return dto;
}

// DTO to Entity
public static User toEntity(UserDTO dto) {
  User user = new User();
  user.setId(dto.getId());
  user.setName(dto.getName());
  user.setEmail(dto.getEmail());
  // ... other fields
  return user;
}
```

Or use MapStruct/ModelMapper libraries for automatic mapping.

---

This DTO structure provides a complete, secure, and maintainable API layer for your Spring Boot application.
