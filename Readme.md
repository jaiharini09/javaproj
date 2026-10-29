# 🚀 SkillBridge: Student Skill Exchange Platform

> **Abstract:**  
> SkillBridge is a web-based Student Skill Exchange Platform designed to encourage peer-to-peer learning, collaboration, and knowledge sharing among students. The platform enables students to create profiles, showcase the skills they can teach, and identify skills they wish to learn from others. It provides a common environment where students can connect with like-minded learners and exchange knowledge without necessarily involving monetary transactions.
>
> The system includes student registration and login, profile management, skill-based search, and filtering facilities to help users find suitable learning partners. Students can send skill exchange requests, accept or reject requests, and communicate through an integrated messaging feature. Each profile displays relevant information, including the student's name, biography, offered skills, and learning interests. The platform also supports creating, viewing, updating, and deleting profile information with appropriate access restrictions.
>
> SkillBridge is developed using Java, Spring Boot, MySQL, HTML, CSS, and JavaScript. Spring Boot handles backend operations and API communication, while MySQL stores student profiles, skill listings, exchange requests, and messages. The user interface provides a responsive and interactive experience for accessing the platform through a web browser.
>
> The main objective of SkillBridge is to make learning more accessible, collaborative, and community-driven by allowing students to learn from one another, develop new abilities, and build meaningful academic connections.

---

## 🛠️ Technology Stack

* **Backend**: Java 21 LTS, Spring Boot 3.3.5, Spring Data JPA, Hibernate, Bean Validation
* **Database**: MySQL 8.0 (Schema: `skillbridge`)
* **Frontend**: Responsive Single-Page Application using modern HTML5, Vanilla CSS / Tailwind CSS, ES6+ JavaScript
* **Build Tool**: Apache Maven (`mvnw` wrapper included)

---

## 🌟 Key Platform Features

1. **Student Registration & Login**:
   * Complete student account registration with email validation, password security, department, year of study, and biography.
   * Session authentication and convenient 1-click student demo profile switching for testing.

2. **Comprehensive Profile Management**:
   * **Full Profile View**: Each student profile displays their name, department, college, biography, contact phone, **offered skills (skills they can teach)**, and **learning interests (skills they wish to learn)**.
   * **Peer Profile Inspection**: Students can click on any peer's name or avatar to view their complete profile with all their skills offered and learning goals.
   * **Profile CRUD with Access Restrictions**:
     * Create profile on registration.
     * View own and peer profiles.
     * Update biography, contact, department, year of study, and avatar (only authorized owners can modify their profile).
     * Delete profile information or delete account with security verification.

3. **Skill-Based Search & Filtering Facilities**:
   * Search across skill titles, descriptions, departments, and peer names in real time.
   * Filter listings by **Skill Type** (All, Offered Skills / Can Teach `TEACH`, Learning Interests / Wants to Learn `LEARN`).
   * Filter by **Category** (Programming, UI/UX & Design, Languages, Academics, Music & Arts, Other).
   * Filter by **Proficiency Level** (Beginner, Intermediate, Advanced, Expert).

4. **Skill Exchange Requests & Proposal Tracking**:
   * Send exchange proposals specifying what skill you wish to learn from a peer and what skill you can teach in return.
   * Manage **Incoming Requests** (with `Accept` and `Decline` actions).
   * Track **Sent Proposals** (with `Cancel` option for pending proposals).
   * Mark exchange proposals as `Completed` once sessions finish.

5. **Integrated Peer-to-Peer Messaging**:
   * Threaded peer conversations to coordinate learning sessions directly on campus.
   * Real-time polling for instant message delivery and unread count badges.
   * Automatic conversation notification logs when proposals are sent or accepted.

---

## ⚙️ REST API Reference

### 🔐 Authentication & Session
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register new student profile |
| `POST` | `/api/auth/login` | Authenticate student credentials |
| `GET` | `/api/auth/me/{id}` | Retrieve logged-in student info |

### 👤 Profile Management
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/users/{id}` | Get student basic profile |
| `GET` | `/api/users/{id}/full` | Get complete profile with offered skills & learning interests |
| `PUT` | `/api/users/{id}?requestingUserId={id}` | Update student profile information (Access restricted) |
| `DELETE` | `/api/users/{id}/field/{fieldName}?requestingUserId={id}` | Clear specific profile field (bio, phone, avatar) |
| `DELETE` | `/api/users/{id}?requestingUserId={id}` | Delete student account & profile (Access restricted) |

### 📚 Skill Listings (Offered & Learning Interests)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/skills` | Search/filter skills (`query`, `category`, `skillType`, `proficiencyLevel`) |
| `GET` | `/api/skills/{id}` | Get specific skill details |
| `GET` | `/api/skills/user/{userId}` | Get all skills listed by a student |
| `POST` | `/api/skills` | Create a skill listing (`TEACH` or `LEARN`) |
| `PUT` | `/api/skills/{id}?userId={userId}` | Update skill listing (Access restricted) |
| `DELETE` | `/api/skills/{id}?userId={userId}` | Delete skill listing (Access restricted) |

### 🔄 Skill Exchange Requests
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/requests` | Propose skill exchange to a student |
| `GET` | `/api/requests/incoming?userId={userId}` | List incoming proposals |
| `GET` | `/api/requests/outgoing?userId={userId}` | List sent proposals |
| `PUT` | `/api/requests/{id}/status?status={status}&userId={userId}` | Update status (`ACCEPTED`, `REJECTED`, `COMPLETED`, `CANCELLED`) |

### 💬 Integrated Messaging
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/messages/send` | Send a direct message |
| `GET` | `/api/messages/conversation?user1={id}&user2={id}` | Get chat history & mark read |
| `GET` | `/api/messages/unread-count?userId={id}` | Get unread message count |
| `GET` | `/api/messages/threads?userId={id}` | Get recent conversation threads |

---

## ▶️ Running the Application

### 1. Prerequisites
* **Java 21 LTS**
* **MySQL 8.0** running locally on port 3306

### 2. Launching with the Run Script
Double click or execute in PowerShell:
```powershell
.\run.bat
```
Or use the Maven Wrapper directly:
```powershell
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-21.0.13.21-hotspot"
.\mvnw.cmd spring-boot:run
```

### 3. Access in Web Browser
Open: [http://localhost:8080/](http://localhost:8080/)

---

## 👥 Pre-Configured Student Demo Accounts

| Student Name | Email | Password | Teaches (Offered) | Learns (Interests) |
| :--- | :--- | :--- | :--- | :--- |
| **Alice Johnson** | `alice@easwari.edu` | `password123` | Full-Stack Web Dev (React & Spring), Figma UI/UX | Machine Learning, French |
| **Bob Smith** | `bob@easwari.edu` | `password123` | Python for Data Science & ML, PyTorch | Docker & Cloud Deployment |
| **Chloe Davis** | `chloe@easwari.edu` | `password123` | Visual Design & Brand Identity | Data Structures in Java |
| **David Miller** | `david@easwari.edu` | `password123` | Conversational German, Classical Guitar | Web Design & HTML/CSS |