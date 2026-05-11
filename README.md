# 🎓 ELearn - Online Learning Platform

A full-stack e-learning platform built with Spring Boot, where students can browse and purchase courses, and instructors (admins) can create and manage courses with video lessons.

---

## 🚀 Tech Stack

- **Backend:** Spring Boot 3.3.2, Spring MVC, Spring Data JPA
- **Frontend:** Thymeleaf, HTML, CSS
- **Database:** MySQL 8
- **Build Tool:** Maven
- **Version Control:** Git & GitHub

---

## ✨ Features

### Student
- Register and login with email/password
- Browse all courses by category
- View course details (price, instructor, lessons preview)
- Add a virtual credit card with initial balance
- Top up wallet from card balance
- Purchase courses
- Access purchased course lessons (video streaming)
- View transaction history
- Manage profile (view details)

### Admin (Instructor)
- Admin dashboard with statistics
- Create, edit, and delete courses
- Add video lessons (YouTube URL or file upload)
- View all registered users

### Security & Sessions
- Session-based authentication
- Role-based access (Student/Admin)
- Remember Me cookie (7 days)
- Logout clears session and cookie

### Virtual Wallet System
- Add a card with initial balance
- Top up wallet from card
- Purchase courses using wallet
- Transaction history tracking

---

## 📊 Database Schema (7 Tables)

| Table | Description |
|-------|-------------|
| `users` | Student and admin accounts |
| `wallet` | User's virtual balance |
| `card` | Linked virtual credit card |
| `course` | Courses created by admins |
| `lesson` | Video lessons in courses |
| `enrollment` | Courses purchased by students |
| `transaction` | Top-up and purchase history |

---

## 🏗️ Project Structure
src/main/java/com/example/elearning/
├── controller/
│ ├── AuthController
│ ├── HomeController
│ ├── CourseController
│ ├── WalletController
│ ├── PurchaseController
│ ├── ProfileController
│ └── AdminController
├── service/
│ ├── UserService
│ ├── CourseService
│ ├── EnrollmentService
│ ├── WalletService
│ ├── CardService
│ ├── LessonService
│ ├── TransactionService
│ ├── PurchaseService
│ └── CookieService
├── repository/
│ ├── UserRepository
│ ├── CourseRepository
│ ├── EnrollmentRepository
│ ├── WalletRepository
│ ├── CardRepository
│ ├── LessonRepository
│ └── TransactionRepository
├── model/
│ ├── User
│ ├── Wallet
│ ├── Card
│ ├── Course
│ ├── Lesson
│ ├── Enrollment
│ └── Transaction
└── session/
└── SessionUser

src/main/resources/
├── templates/
│ ├── login.html
│ ├── signup.html
│ ├── Home.html
│ ├── courses.html
│ ├── course-detail.html
│ ├── profile.html
│ ├── my-courses.html
│ ├── wallet.html
│ ├── add-card.html
│ ├── roadmap.html
│ ├── add-lesson.html
│ └── admin/
│ ├── dashboard.html
│ ├── add-course.html
│ ├── edit-course.html
│ └── add-lesson.html
└── static/CSS/
├── auth.css
├── home.css
├── style.css
├── course-detail.css
├── profile.css
├── wallet.css
├── roadmap.css
├── dashboard.css
├── add-course.css
└── edit-course.css

---

## 🛠️ Setup & Installation

### Prerequisites
- Java 17+
- MySQL 8
- Maven

### Steps

1. **Clone the repository**
```bash
git clone https://github.com/your-username/elearning-platform.git

spring.datasource.url=jdbc:mysql://localhost:3306/elearning_db
spring.datasource.username=root
spring.datasource.password=yourpassword
mvn spring-boot:run
http://localhost:8080

📝 Usage
Register as a Student to browse and purchase courses

Register as an Admin to create and manage courses

Admin registration is open for demo purposes

🎯 Bonus Features
✅ Cookie handling (Remember Me)

✅ Transaction history tracking

✅ Video file upload and streaming

👥 Team Members

Ahmed — A7med455

Habiba — HabibaaMohammed

Farida — faridamyg

Sara — saraamohamedd

Abdelrahman — el-r2d

Hussien — HussienKhaled11
