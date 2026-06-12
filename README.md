# PROG5121 POE Part 3 - Chat App

Java console app for PROG5121 POE. Users can register, login, send/store/discard messages, and view reports. Part 3 implements message storage, search, delete, and longest message features using parallel arrays.

### Features
- **Part 1**: User registration + login with validation
- **Part 2**: Send message, generate hash, store to JSON
- **Part 3**:
    - Load messages from `messages.json`
    - Display longest sent message
    - Search by Message ID
    - Search all messages by recipient
    - Delete message by hash
    - Formatted report: Message Hash + Recipient + Message

### Tech Stack
- Java 17
- NetBeans 20+
- Maven
- JUnit 5 for testing

### How to Run
1. Clone repo: `git clone <your-repo-link>`
2. Open in NetBeans → File → Open Project → select `Chatapp` folder
3. Right-click project → `Run`
4. Menu:
      - `1` Send Message
      - `2` Show Report
      - `3` Quit
      - `4` Stored Messages Menu → a-f for Part 3 features

### How to Test
```bash
mvn test
