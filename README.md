📚 Library Management System - Java Console Application
# 📚 Library Management System - Java Practice

A console-based Library Management System developed in Java for practicing Object-Oriented Programming (OOP), file handling, authentication, role-based authorization, and software design principles.

## 📖 Project Description

This project was developed based on the assignment requirements of the University of Science - VNUHCM.

The system manages:
- Users
- Readers
- Books
- Borrow/Return records

The application runs entirely on the console using Java.

---

# ✨ Features

## 🔐 User Management
- Login
- Logout
- Change password
- Update personal information
- Create new users
- User role management

### User Roles
- **Admin**
- **Manager**
- **Staff**

---

## 👤 Reader Management
- View reader list
- Add new reader
- Edit reader information
- Delete reader
- Search reader by ID card number (CMND)
- Search reader by full name

---

## 📚 Book Management
- View book list
- Add new book
- Edit book information
- Delete book
- Search book by ISBN
- Search book by title

---

## 📄 Borrow/Return Management
- Create borrow slips
- Create return slips
- Calculate overdue fines
- Calculate lost book compensation

### Rules
- Maximum borrow duration: **7 days**
- Overdue fine: **5,000 VND/day**
- Lost book fine: **200% of book price**

---

## 📊 Statistics
- Total number of books
- Number of books by category
- Total number of readers
- Number of readers by gender
- Number of borrowed books
- List of overdue readers

---

# 🔑 Authorization

| Function | Admin | Manager | Staff |
|---|---|---|---|
| User Management | ✅ | Partial | ❌ |
| Reader Management | ✅ | ✅ | Partial |
| Book Management | ✅ | ✅ | Search only |
| Borrow Books | ✅ | ✅ | ✅ |
| Return Books | ✅ | ✅ | ✅ |
| Statistics | ✅ | ✅ | ❌ |

---

# 🛠️ Technologies Used

- Java Core
- Object-Oriented Programming (OOP)
- File Handling (.txt storage)
- Collections Framework
- Console Application
- Git & GitHub

---

# 📂 Project Structure

```bash
src/
├── model/
├── service/
├── utils/
├── data/
├── menu/
└── Main.java
🚀 Getting Started
Requirements
Java JDK 17+ (or your current version)
IntelliJ IDEA / VS Code
Run the Project
Clone the repository
git clone https://github.com/your-username/Library-management-Java-practice.git
Open the project in your IDE
Run:
Main.java
🧪 Sample Default Account
Username: admin
Password: admin
📌 Learning Objectives

This project helps practice:

Java OOP concepts
Clean code
Authentication & authorization
File processing
Exception handling
Layered architecture
Git workflow
📷 Console Preview
===== LIBRARY MANAGEMENT SYSTEM =====
1. Login
2. Reader Management
3. Book Management
4. Borrow Book
5. Return Book
6. Statistics
0. Exit
👨‍💻 Author

Hoang Kim Tran

📄 License

This project is for educational and practice purposes.
