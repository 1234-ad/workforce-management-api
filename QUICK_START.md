# Quick Start Guide

## 🚀 Running the Application

### Prerequisites
- Java 17 or higher
- Git

### Steps to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/1234-ad/workforce-management-api.git
   cd workforce-management-api
   ```

2. **Make gradlew executable (Linux/Mac)**
   ```bash
   chmod +x gradlew
   ```

3. **Run the application**
   ```bash
   # Linux/Mac
   ./gradlew bootRun
   
   # Windows
   gradlew.bat bootRun
   ```

4. **Verify it's running**
   - Open browser to: http://localhost:8080/api/tasks
   - You should see sample tasks in JSON format

## 🧪 Testing the API

### Option 1: Use the Test Script (Recommended)
```bash
# Make the script executable
chmod +x test-api.sh

# Run the comprehensive test
./test-api.sh
```

### Option 2: Use Postman
1. Import `Workforce-Management-API.postman_collection.json`
2. Set the `baseUrl` variable to `http://localhost:8080`
3. Run the requests in order

### Option 3: Manual cURL Testing

**Get all tasks:**
```bash
curl http://localhost:8080/api/tasks
```

**Create a new task:**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "X-User-ID: manager1" \
  -d '{
    "title": "Test Task",
    "description": "Testing the API",
    "startDate": "2025-08-05",
    "dueDate": "2025-08-08",
    "assignedStaffId": "staff1",
    "assignedStaffName": "John Doe",
    "priority": "HIGH"
  }'
```

**Get tasks by priority:**
```bash
curl http://localhost:8080/api/tasks/priority/HIGH
```

## 🎯 Key Features to Test

### 1. Bug Fixes
- **Task Reassignment**: Use `/api/tasks/assign-by-ref/{customerRef}` - old task gets cancelled
- **Cancelled Task Filtering**: Cancelled tasks don't appear in listings

### 2. New Features
- **Smart Date Filtering**: `/api/tasks/date-range?startDate=2025-08-05&endDate=2025-08-05`
- **Priority Management**: `/api/tasks/priority/HIGH`
- **Comments**: POST to `/api/tasks/{id}/comments`
- **Activity History**: GET `/api/tasks/{id}` shows full history

## 📊 Sample Data

The application starts with sample tasks:
- Customer onboarding task (staff1)
- Sales follow-up task (staff2)

## 🔧 Troubleshooting

**Port already in use:**
```bash
# Change port in src/main/resources/application.properties
server.port=8081
```

**Java version issues:**
```bash
# Check Java version
java -version

# Should be Java 17 or higher
```

**Build issues:**
```bash
# Clean and rebuild
./gradlew clean build
```

## 📝 API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all active tasks |
| POST | `/api/tasks` | Create new task |
| GET | `/api/tasks/{id}` | Get task with history |
| GET | `/api/tasks/staff/{staffId}` | Get tasks by staff |
| GET | `/api/tasks/priority/{priority}` | Get tasks by priority |
| GET | `/api/tasks/today` | Get today's tasks |
| GET | `/api/tasks/date-range` | Smart date filtering |
| PUT | `/api/tasks/{id}/priority` | Update priority |
| POST | `/api/tasks/{id}/comments` | Add comment |
| POST | `/api/tasks/assign-by-ref/{ref}` | Reassign task |
| PUT | `/api/tasks/{id}/status` | Update status |

## 🎥 Video Demo

The video demonstration should cover:
1. Starting the application
2. Testing basic CRUD operations
3. Demonstrating bug fixes (reassignment, filtering)
4. Showing new features (priority, comments, smart filtering)
5. Viewing activity history and comments

## 📧 Submission

Remember to:
1. ✅ Public GitHub repository
2. ✅ Video demonstration (5-10 minutes)
3. ✅ SUBMISSION.md completed
4. ✅ Email with subject: "Railse backend Assignment"