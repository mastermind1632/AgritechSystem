# AgriFinanceAPIs

Spring Boot 4.1.1 / Java 21 backend containing six APIs:
1. Farmer Authentication/Profile
2. Finance
3. Supplier
4. Group Order
5. AI Recommendation
6. Notification

## Database

The current Java project uses UUID-style `VARCHAR(36)` IDs for farmers and the related APIs. This is intentional because the existing Farmer API already uses `String` UUID IDs.

Use database `agritech` with:
- username: `postgres`
- password: `Master`
- port: `5432`

`database_schema.sql` is included for reference/manual setup. The application also uses `spring.jpa.hibernate.ddl-auto=update`.

## Run

Start:
`tut.ac.za.AgriFinanceAPIs.AgriFinanceApIsApplication`

Base URL:
`http://localhost:8080`

## Postman sequence

### 1. Register farmer
POST `/api/farmers/register`

```json
{
  "name": "John Farmer",
  "location": "Polokwane",
  "contact": "0712345689",
  "password": "Password123"
}
```

Successful status: **201 Created**. Use a new contact value if the contact was already registered.

### 2. Login
POST `/api/farmers/login`

```json
{
  "contact": "0712345689",
  "password": "Password123"
}
```

### 3. Get farmer
GET `/api/farmers/{farmerId}`

### 4. Create supplier
POST `/api/suppliers`

```json
{
  "name": "Agri Supplies SA",
  "location": "Polokwane",
  "contact": "0151234567"
}
```

### 5. Add supplier product
POST `/api/suppliers/{supplierId}/products`

```json
{
  "productName": "Fertilizer",
  "price": 2500.00
}
```

### 6. Create group order
POST `/api/group-orders`

```json
{
  "productId": "PRODUCT-ID",
  "targetQuantity": 100,
  "discountRate": 10.00
}
```

### 7. Join group order
POST `/api/group-orders/{groupOrderId}/join`

```json
{
  "farmerId": "FARMER-ID",
  "quantity": 20
}
```

### 8. Generate recommendations
POST `/api/recommendations/generate`

```json
{
  "farmerId": "FARMER-ID"
}
```

The recommendation engine is a simple rule-based implementation using recorded farmer expenses and open group orders; it is not a trained machine-learning model.

### 9. Supplier notifications
GET `/api/notifications/supplier/{supplierId}`

GET `/api/notifications/supplier/{supplierId}/unread-count`

PUT `/api/notifications/{notificationId}/read`

Notifications are automatically created for the supplier when a group order is created and when it reaches its target.

## Finance endpoints

POST `/api/finance/expenses`
```json
{
  "farmerId": "FARMER-ID",
  "item": "Fertilizer",
  "category": "Farming Supplies",
  "amount": 2500.00,
  "date": "2026-09-20"
}
```

GET `/api/finance/expenses/{farmerId}`

PUT `/api/finance/expenses/{expenseId}`

DELETE `/api/finance/expenses/{expenseId}?farmerId=FARMER-ID`

POST `/api/finance/income`
```json
{
  "farmerId": "FARMER-ID",
  "item": "Maize Sales",
  "amount": 10000.00,
  "date": "2026-09-20"
}
```

GET `/api/finance/income/{farmerId}`

PUT `/api/finance/income/{incomeId}`

DELETE `/api/finance/income/{incomeId}?farmerId=FARMER-ID`

GET `/api/finance/{farmerId}/summary`

## Important Postman status codes

- Register farmer: `201 Created`
- Create supplier/product: `201 Created`
- Create expense/income: `201 Created`
- Create group order/join/recommendations: `201 Created`
- Successful GET/PUT: `200 OK`
- Successful DELETE: `204 No Content`
- Invalid request: `400 Bad Request`
- Not found: `404 Not Found`
- Invalid login: `401 Unauthorized`
- Resource ownership violation: `403 Forbidden`
