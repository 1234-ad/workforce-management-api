# Workforce Management API

A comprehensive Spring Boot REST API for managing workforce tasks, priorities, and team collaboration.

## Features

### Core Functionality
- ✅ Create, read, update, and delete tasks
- ✅ Assign tasks to staff members
- ✅ Track task status (ACTIVE, COMPLETED, CANCELLED)
- ✅ Set and modify task priorities (HIGH, MEDIUM, LOW)

### Bug Fixes Implemented
1. **Task Re-assignment Fix**: When reassigning tasks by customer reference, old tasks are properly cancelled
2. **Cancelled Task Filtering**: Cancelled tasks are excluded from all task listing endpoints

### New Features
1. **Smart Daily Task View**: Enhanced date-based filtering that shows:
   - All active tasks that started within the date range
   - All active tasks that started before the range but are still open
   
2. **Task Priority Management**:
   - Set priority when creating tasks
   - Update task priority after creation
   - Filter tasks by priority level
   
3. **Activity History & Comments**:
   - Automatic activity logging for all task changes
   - User comments on tasks
   - Complete chronological history for each task

## API Endpoints

### Task Management
- `POST /api/tasks` - Create a new task
- `GET /api/tasks` - Get all active tasks
- `GET /api/tasks/{id}` - Get task details with full history
- `PUT /api/tasks/{id}/status` - Update task status
- `DELETE /api/tasks/{id}` - Delete a task

### Staff & Assignment
- `GET /api/tasks/staff/{staffId}` - Get tasks for a specific staff member
- `POST /api/tasks/assign-by-ref/{customerRef}` - Reassign task by customer reference

### Date & Priority Filtering
- `GET /api/tasks/date-range?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` - Smart date filtering
- `GET /api/tasks/today` - Get today's relevant tasks
- `GET /api/tasks/priority/{priority}` - Filter by priority (HIGH/MEDIUM/LOW)
- `PUT /api/tasks/{id}/priority` - Update task priority

### Comments & History
- `POST /api/tasks/{id}/comments` - Add comment to task
- Task details endpoint includes full activity history and comments

## Technology Stack

- **Java 17**
- **Spring Boot 3.0.4**
- **Gradle** for build management
- **Lombok** for reducing boilerplate code
- **MapStruct** for object mapping
- **In-memory storage** using Java collections

## Project Structure

```
src/main/java/com/company/workforcemgmt/
├── WorkforcemgmtApplication.java
├── controller/
│   └── TaskController.java
├── service/
│   └── TaskService.java
├── model/
│   ├── Task.java
│   ├── Staff.java
│   ├── Priority.java
│   ├── Status.java
│   ├── ActivityLog.java
│   └── Comment.java
├── dto/
│   ├── TaskDto.java
│   ├── CreateTaskRequest.java
│   ├── UpdatePriorityRequest.java
│   ├── AddCommentRequest.java
│   └── AssignTaskRequest.java
└── mapper/
    └── TaskMapper.java
```

## Running the Application

1. **Prerequisites**: Java 17 and Gradle installed

2. **Clone the repository**:
   ```bash
   git clone https://github.com/1234-ad/workforce-management-api.git
   cd workforce-management-api
   ```

3. **Build and run**:
   ```bash
   ./gradlew bootRun
   ```

4. **Access the API**: http://localhost:8080/api/tasks

## Sample API Usage

### Create a Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "X-User-ID: manager1" \
  -d '{
    "title": "Complete customer onboarding",
    "description": "Onboard new customer ABC Corp",
    "startDate": "2025-08-05",
    "dueDate": "2025-08-08",
    "assignedStaffId": "staff1",
    "assignedStaffName": "John Doe",
    "priority": "HIGH",
    "customerRef": "ABC-001"
  }'
```

### Get Smart Daily Tasks
```bash
curl "http://localhost:8080/api/tasks/date-range?startDate=2025-08-05&endDate=2025-08-05"
```

### Update Task Priority
```bash
curl -X PUT http://localhost:8080/api/tasks/{taskId}/priority \
  -H "Content-Type: application/json" \
  -H "X-User-ID: manager1" \
  -d '{"priority": "HIGH"}'
```

### Add Comment
```bash
curl -X POST http://localhost:8080/api/tasks/{taskId}/comments \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Task is progressing well",
    "userId": "staff1",
    "userName": "John Doe"
  }'
```

## Key Implementation Details

### Bug Fixes
1. **Task Reassignment**: The `assignTaskByRef` method now properly cancels the old task before creating a new one
2. **Cancelled Task Filtering**: All task listing methods filter out cancelled tasks using `task.getStatus() != Status.CANCELLED`

### Smart Date Filtering
The enhanced date filtering logic includes:
- Tasks that started within the specified date range
- Active tasks that started before the range but are still open and incomplete

### Activity Logging
Every significant action is automatically logged:
- Task creation, status changes, priority updates
- Task assignments and reassignments
- Comment additions

### Data Persistence
Uses in-memory Java collections for simplicity:
- `Map<String, Task>` for task storage
- Automatic UUID generation for unique IDs
- Sample data initialization for testing

## Testing the Application

The application includes sample data for immediate testing. You can:
1. View existing tasks: `GET /api/tasks`
2. Test the smart date filtering with today's date
3. Add comments and see activity history
4. Test task reassignment functionality
5. Filter tasks by priority levels

## Future Enhancements

- Database integration (PostgreSQL/MySQL)
- User authentication and authorization
- Real-time notifications
- Task templates and recurring tasks
- Advanced reporting and analytics