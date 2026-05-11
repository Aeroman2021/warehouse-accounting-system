# Warehouse Accounting System

A modular ERP-style backend system built with Java and Spring Boot, focused on inventory management, accounting operations, and event-driven communication between business modules.

The project follows a **Modular Monolith Architecture** with clear domain separation between modules while running inside a single Spring Boot application.

---

# 🚀 Technologies

* Java 21
* Spring Boot 3
* Spring Data JPA
* Hibernate
* MySQL
* Maven Multi Module
* Event-Driven Architecture
* REST API
* Lombok

---

# 📦 Modules

## Inventory Module

Handles all inventory-related operations:

* Product management
* Stock management
* Stock movement tracking
* Supplier/customer stock operations
* Inventory transactions

### Features

* Stock In / Stock Out
* Movement history
* Inventory quantity updates
* Reference number generation
* Event publishing after stock operations

---

## Accounting Module

Responsible for financial operations and bookkeeping.

### Features

* Journal Entries
* Journal Items
* Account management
* Double-entry accounting
* Accounting event listeners
* Inventory-to-accounting integration

---

## Shared Concepts

The system supports shared business concepts such as:

* Parties
* Suppliers
* Companies
* Departments
* Customers

---

# 🧠 Architecture

The project uses a **Modular Monolith** architecture.

Each module:

* Has its own entities
* Services
* Repositories
* Controllers
* Events

But all modules run inside a single Spring Boot application.

This approach provides:

* Loose coupling
* Better maintainability
* Easier future migration to microservices
* Simpler transactions and deployment

---

# 🔄 Event-Driven Communication

Modules communicate using Spring Events.

Example flow:

1. Inventory module creates a stock movement
2. `StockMovementCreatedEvent` is published
3. Accounting module listens to the event
4. Journal entries are created automatically

---

# 📂 Project Structure

```text
inventory-accounting-app
│
├── inventory
├── accounting
├── main-app
└── pom.xml
```

---

# ⚙️ Running the Project

## Clone Repository

```bash
git clone https://github.com/Aeroman2021/warehouse-accounting-system.git
```

---

## Build Project

```bash
mvn clean install
```

---

## Run Application

Run:

```text
MainApplication.java
```

Or:

```bash
mvn spring-boot:run
```

---

# 📌 Future Improvements

* JWT Authentication & Authorization
* Kafka/RabbitMQ Integration
* Audit Logging
* Reporting Module
* Docker Support
* CI/CD Pipeline
* Multi Warehouse Support
* Invoice Management
* Financial Reports

---

# 👨‍💻 Author

Mohsen Malakouti

Java Backend Developer
Focused on Spring Boot, System Design, and Enterprise Applications.

---

# 📄 License

This project is for educational and portfolio purposes.

