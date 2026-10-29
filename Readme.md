# 🚀 SkillBridge – Student Skill Exchange Platform

SkillBridge is a peer-to-peer campus platform where students can exchange knowledge and skills. Students can list skills they want to teach or learn, browse peer listings with categories and filters, propose skill swaps, and coordinate learning sessions through direct messaging.

---

## 🛠️ Tech Stack

* **Backend**: Java 21, Spring Boot 3.3.5, Spring Data JPA, Hibernate, Bean Validation
* **Database**: MySQL 8.0
* **Frontend**: HTML5, Tailwind CSS, JavaScript (Vanilla ES6+ SPA)
* **Build Tool**: Apache Maven (Maven Wrapper included)

---

## 🌟 Key Features

1. **Skill Discovery & Search**:
   * Filter skills by type (`TEACH` vs `LEARN`), category (Programming, Design, Languages, Academics, Music, etc.), and keyword.
   * View student profile details, proficiency levels, and availability.
2. **Learning / Exchange Proposals**:
   * Send exchange requests proposing mutual skill swaps.
   * Manage incoming and outgoing proposals with status tracking (`PENDING`, `ACCEPTED`, `REJECTED`, `COMPLETED`).
3. **Peer Messaging & Coordination**:
   * Threaded peer-to-peer chat between matched students to coordinate study sessions.
   * Unread message tracking and instant updates.
4. **Student Profiles**:
   * View and update student bio, college/department, year of study, contact info, and active skill listings.
5. **Pre-Seeded Demo Data**:
   * Automatically initializes sample student profiles and listings on first launch for immediate testing.

---

## ⚙️ REST API Endpoints

### 🔐 Authentication
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new student account |
| `POST` | `/api/auth/login` | Authenticate student |
| `GET` | `/api/auth/me/{id}` | Fetch current student profile |

### 📚 Skills
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/skills` | Search/filter skills (`query`, `category`, `skillType`) |
| `GET` | `/api/skills/{id}` | Fetch skill details by ID |
| `GET` | `/api/skills/user/{userId}` | Fetch all skills created by a student |
| `POST` | `/api/skills` | Create a new skill listing (`TEACH` / `LEARN`) |
| `DELETE` | `/api/skills/{id}?userId={userId}` | Delete a skill listing |

### 🔄 Exchange Requests
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/requests` | Send an exchange proposal to a peer |
| `GET` | `/api/requests/incoming?userId={userId}` | View incoming exchange requests |
| `GET` | `/api/requests/outgoing?userId={userId}` | View outgoing exchange requests |
| `PUT` | `/api/requests/{id}/status` | Update status (`ACCEPTED`, `REJECTED`, `COMPLETED`) |

### 💬 Messaging
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/messages/send` | Send a direct message |
| `GET` | `/api/messages/conversation?user1={id}&user2={id}` | Get conversation history |
| `GET` | `/api/messages/unread-count?userId={id}` | Get total unread messages count |
| `GET` | `/api/messages/threads?userId={id}` | Get list of active conversation threads |

---

## ▶️ How to Run Locally

### 1. Prerequisites
* **Java 21 LTS**
* **MySQL 8.0** running locally on port 3306

### 2. Configure Database (Optional)
By default, the application connects to `jdbc:mysql://localhost:3306/skillbridge`.
You can override credentials via environment variables:
```bash
set DB_USERNAME=root
set DB_PASSWORD=your_password
```

### 3. Run the Application
Using the Maven Wrapper:
```powershell
.\mvnw.cmd spring-boot:run
```
Or simply run:
```powershell
.\run.bat
```

### 4. Open in Browser
* **Web Application**: [http://localhost:8080/](http://localhost:8080/)
* **API Documentation/Skills**: [http://localhost:8080/api/skills](http://localhost:8080/api/skills)

---

## 👥 Demo Accounts

| Student Name | Email | Password | Role |
| :--- | :--- | :--- | :--- |
| **Alice Johnson** | `alice@easwari.edu` | `password123` | Teaches React & Spring Boot |
| **Bob Smith** | `bob@easwari.edu` | `password123` | Teaches Python & Data Science |
| **Chloe Davis** | `chloe@easwari.edu` | `password123` | Teaches Figma UI/UX Design |
| **David Miller** | `david@easwari.edu` | `password123` | Teaches German & Classical Guitar |