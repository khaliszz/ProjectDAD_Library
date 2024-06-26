# UTeM Library System

## Overview

The UTeM Library System is designed to manage book rentals and returns for university members and staff. It includes multiple applications interacting through a middleware layer with a centralized database.

## Applications

### 1. Member Application
- **Purpose**: Allows university members to register, log in, borrow books, return books, and view their borrowing history.
- **Main Functions**:
  - **Log In**: Only approved members can log in.
  - **Register**: Register new members and handle pending approval.
  - **Borrow Book**: Search, borrow, and manage book borrowings.
  - **Return Book**: Return books, highlighting those borrowed for more than 3 days.
  - **History**: View borrowing history.
  - **Log Out**: End the session.

### 2. Staff Application
- **Purpose**: Provides staff with tools to view book borrowings, returns, and manage member registrations.
- **Main Functions**:
  - **View Borrowed Books**: Search and view currently borrowed books.
  - **View Returned Books**: Search and view returned books.
  - **Pending Registrations**: Approve or reject pending member registrations.
  - **Log Out**: End the session.

## Architecture

The system architecture consists of the Member Application, Staff Application, Middleware, and a centralized Database. Below is the architecture diagram:

![UTeM Library System Architecture](./UTeM_Library_System_Architecture.png)

## Middleware

### List of URL Endpoints (RESTful)
- **retrieveBorrowedBooksByMember**: Retrieves borrowed books for a specific member.
- **retrievePendingMember**: Retrieves pending member registration requests.
- **approveMember**: Approves a pending member registration.
- **borrowBook**: Processes book borrowing.
- **returnBook**: Processes book return.
- **loginMember**: Handles member login.
- **registerMember**: Handles member registration.

### Functions/Features
- **Member Management**: Registering new members, approving memberships, and handling member logins.
- **Book Management**: Borrowing and returning books, tracking borrowed and returned books, and handling overdue books.

## Database
### Tables

1. **members**
   - `memberID` (INT, PRIMARY KEY)
   - `username` (VARCHAR)
   - `password` (VARCHAR)
   - `memberStatus` (ENUM: 'PENDING', 'APPROVED')

2. **books**
   - `bookID` (INT, PRIMARY KEY)
   - `title` (VARCHAR)
   - `isbn` (VARCHAR)
   - `copies` (INT)

3. **borrowed_books**
   - `borrowID` (INT, PRIMARY KEY)
   - `memberID` (INT, FOREIGN KEY)
   - `bookID` (INT, FOREIGN KEY)
   - `dateBorrowed` (DATE)
   - `status` (ENUM: 'ONGOING', 'RETURNED')
