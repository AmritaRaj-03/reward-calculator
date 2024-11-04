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

| Endpoint                                           | Method | Description                                                                                       |
|----------------------------------------------------|--------|---------------------------------------------------------------------------------------------------|
| `/api/customers`                                   | GET    | Retrieves a list of all customers.                                                                |
| `/api/customers/{id}`                              | GET    | Retrieves details of a specific customer by ID.                                                   |
| `/api/customers/{customerId}/transactions`         | GET    | Retrieves a list of transactions for a specific customer by their ID.                             |
| `/api/customers/{customerId}/rewards`              | GET    | Calculates and retrieves reward points for a specific customer over a specified period.           |
| `/api/customers/rewards`                           | GET    | Calculates and retrieves total reward points for all customers over a specified period.           |

### Example Usage
1. **Retrieve All Customers**: Use `GET /api/customers` to get a list of all registered customers.
2. **Retrieve Customer by ID**: Use `GET /api/customers/{id}` to fetch a customer’s details by their unique ID.
3. **Retrieve Transactions for a Customer**: Use `GET /api/customers/{customerId}/transactions` to get all transactions for a customer.
4. **Calculate Rewards for a Customer**: Use `GET /api/customers/{customerId}/rewards?months={months}` to get rewards for a customer over a period.
5. **Calculate Rewards for All Customers**: Use `GET /api/customers/rewards?months={months}` to get total rewards for all customers.

