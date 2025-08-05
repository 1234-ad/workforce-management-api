# Backend Engineer Challenge Submission

## 1. Link to your Public GitHub Repository
https://github.com/1234-ad/workforce-management-api

## 2. Link to your Video Demonstration
[Video demonstration will be uploaded and link provided here]

## Project Overview

This submission includes a complete Spring Boot application that addresses all requirements of the Backend Engineer Challenge for the Workforce Management API.

### ✅ Part 0: Project Setup & Structuring
- Created a professional Spring Boot project with Gradle
- Organized code into standard MVC architecture
- Configured all required dependencies (Spring Web, Lombok, MapStruct)
- Implemented proper package structure

### ✅ Part 1: Bug Fixes
1. **Task Re-assignment Creates Duplicates**: Fixed the `assignTaskByRef` method to properly cancel old tasks when reassigning
2. **Cancelled Tasks Clutter the View**: Modified all task-fetching endpoints to exclude cancelled tasks

### ✅ Part 2: New Features
1. **Smart Daily Task View**: Enhanced date-based filtering to show both tasks starting in range AND active tasks from before the range
2. **Task Priority Implementation**: 
   - Added Priority enum (HIGH, MEDIUM, LOW)
   - Created endpoint to update task priority
   - Created endpoint to filter tasks by priority
3. **Task Comments & Activity History**:
   - Automatic activity logging for all task operations
   - User comments functionality
   - Complete chronological history in task details

## Technical Implementation

### Architecture
- **Models**: Task, Staff, Priority, Status, ActivityLog, Comment
- **DTOs**: CreateTaskRequest, TaskDto, UpdatePriorityRequest, AddCommentRequest, AssignTaskRequest
- **Service Layer**: TaskService with comprehensive business logic
- **Controller Layer**: RESTful API endpoints with proper validation
- **Mapping**: MapStruct for clean object mapping

### Key Features Implemented
- ✅ Professional project structure
- ✅ Comprehensive validation using Jakarta Validation
- ✅ Proper error handling and HTTP status codes
- ✅ Activity logging for audit trail
- ✅ Smart date filtering logic
- ✅ Priority management system
- ✅ Comment system with user attribution
- ✅ Task reassignment with proper cleanup
- ✅ In-memory data storage using Java collections
- ✅ Sample data for immediate testing

### API Endpoints
- `POST /api/tasks` - Create task
- `GET /api/tasks` - Get all active tasks
- `GET /api/tasks/{id}` - Get task with full history
- `GET /api/tasks/staff/{staffId}` - Get tasks by staff
- `GET /api/tasks/date-range` - Smart date filtering
- `GET /api/tasks/priority/{priority}` - Filter by priority
- `PUT /api/tasks/{id}/priority` - Update priority
- `POST /api/tasks/{id}/comments` - Add comments
- `POST /api/tasks/assign-by-ref/{customerRef}` - Reassign tasks
- `PUT /api/tasks/{id}/status` - Update status

### Bug Fixes Verified
1. **Reassignment Fix**: Old tasks are now properly cancelled when reassigning by customer reference
2. **Cancelled Task Filtering**: All listing endpoints exclude cancelled tasks from results

### Testing Ready
- Sample data included for immediate testing
- All endpoints functional and tested
- Proper error handling for edge cases
- Comprehensive logging for debugging

## Running the Application

1. Clone the repository
2. Run `./gradlew bootRun`
3. Access API at `http://localhost:8080/api/tasks`
4. Use the provided sample data or create new tasks via API

The application is production-ready with proper structure, validation, error handling, and comprehensive feature implementation.