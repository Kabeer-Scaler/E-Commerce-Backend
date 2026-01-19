# 🛒 E-Commerce Backend API – Spring Boot + Razorpay

A complete backend system for a minimal E-Commerce platform built using Spring Boot, MongoDB, and Razorpay Payment Gateway, implementing product management, cart, orders, payments, and webhook-based order status updates.

# 🚀 Features
## Core Features

Product Management (Create, List, Search)

Cart Management (Add, View, Clear)

Order Processing (Create order from cart, View order)

Stock Management

Razorpay Payment Integration

Webhook-based Order Status Update

MongoDB Persistence

## Bonus Features

Order History per User

Order Cancellation (if not paid)

Product Search API

# Layers

Controller Layer – REST APIs

Service Layer – Business Logic

Repository Layer – MongoDB Access

DTO Layer – Clean Request/Response Models

# 📦 Tech Stack

Java 17

Spring Boot

Spring Data MongoDB

Razorpay Java SDK

Lombok

Postman

MongoDB

Maven

# ⚙️ Setup Instructions
1️⃣ Clone Repository
git clone https://github.com/Kabeer-Scaler/E-Commerce-Backend.git
cd E-Commerce-Backend

2️⃣ Configure Application

Create a file:

src/main/resources/application.yaml


Copy from:

src/main/resources/application-example.yaml


Then add your credentials:

razorpay:
  key: YOUR_KEY
  secret: YOUR_SECRET

3️⃣ Start MongoDB

Make sure MongoDB is running locally:

mongod

4️⃣ Run Application
mvn spring-boot:run


Server runs on:

http://localhost:8080

# 📮 API Endpoints
## Product APIs
Method	Endpoint	Description


POST	/api/products - Create product

GET	/api/products -	Get all products

GET	/api/products/search?q=laptop -	Search products

## Cart APIs
Method	Endpoint	Description

POST	/api/cart/add -	Add item to cart

GET	/api/cart/{userId} -	View cart

DELETE	/api/cart/{userId}/clear -	Clear cart

## Order APIs
Method	Endpoint	Description

POST	/api/orders -	Create order

GET	/api/orders/{orderId} -	Get order

GET	/api/orders/user/{userId} -	Order history

POST	/api/orders/{orderId}/cancel -	Cancel order

## Payment APIs
Method	Endpoint	Description

POST	/api/payments/create -	Create Razorpay payment

## Webhook
Method	Endpoint	Description

POST	/api/webhooks/payment - Payment callback handler

For local testing, webhook is simulated using Postman.

# 🧪 Testing

All APIs are tested using Postman.
Postman collection is included in the repository.

Variables Used

userId

productId

orderId

paymentId

# 🔐 Security Note

Secrets are excluded from GitHub using .gitignore.
Use application-example.yaml for reference.

# 🏆 Bonus Challenges Implemented

✔ Order History API

✔ Order Cancellation with Stock Restore

✔ Product Search API

✔ Razorpay Integration

# 📚 Learning Outcomes

REST API design

MongoDB relationships handling

Payment gateway integration

Webhook pattern

Clean backend architecture

DTO-based API contracts

GitHub project management


