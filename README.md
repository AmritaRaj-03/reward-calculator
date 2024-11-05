# Reward Calculator

## Project Overview
This project is a **Reward Calculator** built in Java with Spring Boot. It awards reward points to customers based on their purchases over a specified time period. 
The main goal is to allow easy tracking and calculation of rewards, with data stored in MySQL and accessible through a simple API.

---

## Introduction
- **Project Overview**: The Reward Calculator API enables to record customer transactions and calculate rewards based on a points system. Customers earn points for each purchase, which helps increase customer engagement and loyalty.
- **Motivation**: This project was created to demonstrate backend calculation logic for rewards, handling database operations, and providing a clean API with error handling for seamless integration with front-end applications.

---

## Design Details

### Architecture
This project is built using Spring Boot. The application is structured into different layers to handle services, repository interactions, and REST API endpoints within a single service.

### Data Flow
1. **Transaction Recording**: Customer transactions are stored in the database via the `TransactionRepository`.
2. **Reward Calculation**: The `CustomerService` calculates rewards based on a customer’s transactions over a specified period.
3. **API Interaction**: Users interact with the API through `CustomerController`, which routes requests to the service layer.

### Core Components
- **Controller Layer**: `CustomerController` handles incoming API requests and directs them to the service layer.
- **Service Layer**: The `CustomerService` and `CustomerServiceImpl` classes contain business logic, including reward calculations.
- **Repository Layer**: Interfaces with the database through `CustomerRepository` and `TransactionRepository`.

### Database Schema
The database includes two main tables:
- **Customer Table**: Stores customer information.
- **Transaction Table**: Records each transaction and links to the `Customer` table with a foreign key.

---

## Tech Stack

- **Programming Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL
- **Build Tool**: Maven
- **Testing**: JUnit for unit testing
- **API Testing**: Postman
- **Other Libraries**:
  - Lombok (for reducing boilerplate code in model classes)
  - Spring Data JPA (for database interactions)
  - Spring Web (for building RESTful APIs)
- **Version Control**: Git and GitHub

---

## API Endpoints

The following are the available endpoints for the Customer Rewards API:

| Endpoint                                                  | Method | Description                                                                                                                 |
|-----------------------------------------------------------|--------|-----------------------------------------------------------------------------------------------------------------------------|
| `/api/customers`                                          | GET    | Retrieves a list of all customers.                                                                                          |
| `/api/customers/{id}`                                     | GET    | Retrieves details of a specific customer by ID.                                                                             |
| `/api/customers/{customerId}/transactions`                | GET    | Retrieves a list of transactions for a specific customer by their ID.                                                       |
| `/api/customers/{customerId}/rewards?months={months}`     | GET    | Calculates and retrieves reward points for a specific customer over a specified period, with `months` as a query parameter. |
| `/api/customers/rewards?months={months}`                  | GET    | Calculates and retrieves total reward points for all customers over a specified period, with `months` as a query parameter. |

### Query Parameters

- **months** (optional): The number of months to calculate rewards for. Defaults to 3 if not specified.

-----
## Example Usage with Response Body

### Retrieve All Customers

**Endpoint:** `GET /api/customers`
```json
**Response:**
[
    {
        "id": 1,
        "name": "Alice",
        "email": "alice@example.com"
    },
    {
        "id": 2,
        "name": "Bob",
        "email": "bob@example.com"
    }
]
}


**Endpoint:** GET /api/customers/{id}

**Response:** 
[
{
    "id": 1,
    "name": "Alice",
    "email": "alice@example.com"
}
]


**Endpoint:** GET /api/customers/{customerId}/transactions

**Response:**
[
    {
        "transactionId": 1,
        "amount": 120.0,
        "transactionDate": "2024-11-01T10:00:00"
    },
    {
        "transactionId": 2,
        "amount": 80.0,
        "transactionDate": "2024-11-02T15:30:00"
    }
]


**Endpoint:** GET /api/customers/{customerId}/rewards?months={months}

**Response:**
{
    "customerId": 1,
    "totalRewards": 200.0,
    "transactions": [
        {
            "transactionId": 1,
            "amount": 120.0,
            "transactionDate": "2024-11-01T10:00:00"
        },
        {
            "transactionId": 2,
            "amount": 80.0,
            "transactionDate": "2024-11-02T15:30:00"
        }
    ]
}

**Endpoint:** GET /api/customers/rewards?months={months}

**Response:**
{
    "details": [
        { "1": 90, "2": 40 }
    ]
}


