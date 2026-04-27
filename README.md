# 💳 Fintech Transaction Backend System

## Overview

This project is a Fintech Transaction Backend System built using Core Java and JDBC, designed to simulate real-world banking transaction processing.

It ensures safe, consistent, and reliable money transfers between accounts with features like:
- Transaction validation  
- Risk assessment  
- Currency conversion  
- Database persistence (MySQL)  
- Atomic transaction handling (commit/rollback)  

---

## Problem Statement

In financial systems, it is critical to ensure that:
- Money is not lost during failures  
- Transactions are processed securely  
- Data remains consistent  

This project solves that by implementing:
 Atomic transactions (All or Nothing execution)

---

##  System Architecture

Client / Request         ↓ Transaction Engine         ↓ Service Layer  (Validation + Risk + Transaction)         ↓ Repository Interface         ↓ JDBC Implementation         ↓ MySQL Database

---

##  Key Features

### Transaction Processing
- Debit sender
- Credit receiver
- Store transaction record

### Transaction Safety
- Uses:
  - setAutoCommit(false)
  - commit()
  - rollback()
- Prevents partial transactions

###  Currency Conversion
- Supports INR ↔ USD conversion

### Risk Detection
- Blocks high-risk transactions

###  Layered Architecture
- Engine Layer
- Service Layer
- Repository Layer

---

## OOP Concepts Used

| Concept | Implementation |
|--------|--------------|
| Encapsulation | Private fields with getters/setters |
| Abstraction | Repository Interfaces |
| Polymorphism | Multiple repository implementations |
| Inheritance | Custom exceptions |
| Composition | Engine uses multiple services |
| Loose Coupling | Interface-based design |

---

## Database Design

### Tables:
- users
- accounts
- transactions

---

##  Transaction Flow

1. Request received  
2. Validation check  
3. Risk assessment  
4. Debit & Credit  
5. Transaction saved  
6. Commit (or rollback on failure)

---

##  Tech Stack

- Java (Core)
- JDBC
- MySQL
- Eclipse IDE

---

##  Future Enhancements

- Spring Boot REST APIs  
- JWT Authentication (Cybersecurity)  
- AI/ML-based Fraud Detection  
- Frontend Integration  

---

## Author
Madhumita Das
Vaishnavi Nautiyal
Anushka Nautiyal
Neha Arya
