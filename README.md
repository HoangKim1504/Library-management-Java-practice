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

# 🔑 Authorization Table

| Function | Admin System | Manager | Staff |
|---|---|---|---|
| 1.1 Login | ✅ | ✅ | ✅ |
| 1.2 Logout | ✅ | ✅ | ✅ |
| 1.3 Change Password | ✅ | ✅ | ✅ |
| 1.4 Update Personal Information | ✅ | ✅ | ✅ |
| 1.5 Create User | ✅ | ❌ | ❌ |
| 1.6 User Role Management | ✅ | ❌ | ❌ |
| 2.1 View Reader List | ✅ | ✅ | ✅ |
| 2.2 Add Reader | ✅ | ✅ | ✅ |
| 2.3 Edit Reader | ✅ | ✅ | ✅ |
| 2.4 Delete Reader | ✅ | ✅ | ❌ |
| 2.5 Search Reader by ID Card | ✅ | ✅ | ✅ |
| 2.6 Search Reader by Name | ✅ | ✅ | ✅ |
| 3.1 View Book List | ✅ | ✅ | ❌ |
| 3.2 Add Book | ✅ | ✅ | ❌ |
| 3.3 Edit Book | ✅ | ✅ | ❌ |
| 3.4 Delete Book | ✅ | ✅ | ❌ |
| 3.5 Search Book by ISBN | ✅ | ✅ | ✅ |
| 3.6 Search Book by Title | ✅ | ✅ | ✅ |
| 4.xxx Borrow Book Functions | ✅ | ✅ | ✅ |
| 5.xxx Return Book Functions | ✅ | ✅ | ✅ |
| 6.1 Total Books Statistics | ✅ | ✅ | ❌ |
| 6.2 Books by Category Statistics | ✅ | ✅ | ❌ |
| 6.3 Total Readers Statistics | ✅ | ✅ | ❌ |
| 6.4 Readers by Gender Statistics | ✅ | ✅ | ❌ |
| 6.5 Borrowed Books Statistics | ✅ | ✅ | ✅ |
| 6.6 Overdue Readers Statistics | ✅ | ✅ | ✅ |

> ✅ Allowed  
> ❌ Not Allowed

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
├── book/                  # Book management module
│   ├── Book.java
│   ├── BookService.java
│   └── BookManagement.java
│
├── data/                  # Store txt data files
│   ├── books.txt
│   ├── readers.txt
│   ├── users.txt
│   └── slips.txt
│
├── enums/                 # Enum classes
│   ├── Gender.java
│   ├── UserRole.java
│   ├── AccountStatus.java
│   └── SlipStatus.java
│
├── library/               # Main menu and library system
│   ├── LibraryManagement.java
│   └── Main.java
│
├── reader/                # Reader management module
│   ├── Reader.java
│   ├── ReaderService.java
│   └── ReaderManagement.java
│
├── slip/                  # Borrow/Return slip module
│   ├── BorrowSlip.java
│   ├── ReturnSlip.java
│   ├── SlipService.java
│   └── SlipManagement.java
│
├── user/                  # User management module
│   ├── User.java
│   ├── UserService.java
│   └── AuthService.java
│
├── util/                  # Utility classes
│   ├── FileUtil.java
│   ├── DateUtil.java
│   ├── InputUtil.java
│   └── FormatUtil.java
│
└── validator/             # Input validation classes
    ├── UserValidator.java
    ├── ReaderValidator.java
    ├── BookValidator.java
    └── ValidationUtil.java

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
