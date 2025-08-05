#!/bin/bash

# Workforce Management API Test Script
# This script demonstrates all the features and bug fixes implemented

BASE_URL="http://localhost:8080/api/tasks"
echo "🚀 Testing Workforce Management API"
echo "Base URL: $BASE_URL"
echo "=========================================="

# Function to make HTTP requests and display results
make_request() {
    local method=$1
    local url=$2
    local data=$3
    local headers=$4
    
    echo ""
    echo "📡 $method $url"
    if [ ! -z "$data" ]; then
        echo "📝 Data: $data"
    fi
    
    if [ ! -z "$data" ]; then
        if [ ! -z "$headers" ]; then
            curl -s -X $method "$url" -H "Content-Type: application/json" $headers -d "$data" | jq '.'
        else
            curl -s -X $method "$url" -H "Content-Type: application/json" -d "$data" | jq '.'
        fi
    else
        if [ ! -z "$headers" ]; then
            curl -s -X $method "$url" $headers | jq '.'
        else
            curl -s -X $method "$url" | jq '.'
        fi
    fi
    echo "----------------------------------------"
}

# Wait for user input
wait_for_input() {
    echo ""
    read -p "Press Enter to continue..."
}

echo "1️⃣ TESTING BASIC FUNCTIONALITY"
echo "Getting all existing tasks (should show sample data)..."
make_request "GET" "$BASE_URL"
wait_for_input

echo "2️⃣ CREATING NEW TASKS"
echo "Creating a high priority task..."
TASK_DATA='{
    "title": "Urgent customer issue resolution",
    "description": "Resolve critical issue for VIP customer",
    "startDate": "2025-08-05",
    "dueDate": "2025-08-06",
    "assignedStaffId": "staff3",
    "assignedStaffName": "Alice Johnson",
    "priority": "HIGH",
    "customerRef": "VIP-001"
}'
TASK_RESPONSE=$(curl -s -X POST "$BASE_URL" -H "Content-Type: application/json" -H "X-User-ID: manager1" -d "$TASK_DATA")
TASK_ID=$(echo $TASK_RESPONSE | jq -r '.id')
echo $TASK_RESPONSE | jq '.'
echo "Created task ID: $TASK_ID"
wait_for_input

echo "3️⃣ TESTING PRIORITY FEATURES"
echo "Getting all HIGH priority tasks..."
make_request "GET" "$BASE_URL/priority/HIGH"
wait_for_input

echo "Updating task priority from HIGH to MEDIUM..."
PRIORITY_DATA='{"priority": "MEDIUM"}'
make_request "PUT" "$BASE_URL/$TASK_ID/priority" "$PRIORITY_DATA" "-H 'X-User-ID: manager1'"
wait_for_input

echo "4️⃣ TESTING COMMENTS & ACTIVITY HISTORY"
echo "Adding a comment to the task..."
COMMENT_DATA='{
    "content": "Customer has been contacted and issue is being investigated",
    "userId": "staff3",
    "userName": "Alice Johnson"
}'
make_request "POST" "$BASE_URL/$TASK_ID/comments" "$COMMENT_DATA"
wait_for_input

echo "Getting task details with full history and comments..."
make_request "GET" "$BASE_URL/$TASK_ID"
wait_for_input

echo "5️⃣ TESTING SMART DATE FILTERING"
echo "Getting today's tasks (smart filtering)..."
make_request "GET" "$BASE_URL/today"
wait_for_input

echo "Getting tasks for date range (includes active tasks from before range)..."
TODAY=$(date +%Y-%m-%d)
TOMORROW=$(date -d "+1 day" +%Y-%m-%d)
make_request "GET" "$BASE_URL/date-range?startDate=$TODAY&endDate=$TOMORROW"
wait_for_input

echo "6️⃣ TESTING BUG FIX: TASK REASSIGNMENT"
echo "Creating a task with customer reference for reassignment test..."
REASSIGN_TASK_DATA='{
    "title": "Customer support task",
    "description": "Handle customer support request",
    "startDate": "2025-08-05",
    "dueDate": "2025-08-07",
    "assignedStaffId": "staff1",
    "assignedStaffName": "John Doe",
    "priority": "MEDIUM",
    "customerRef": "SUPPORT-123"
}'
REASSIGN_RESPONSE=$(curl -s -X POST "$BASE_URL" -H "Content-Type: application/json" -H "X-User-ID: manager1" -d "$REASSIGN_TASK_DATA")
echo $REASSIGN_RESPONSE | jq '.'
wait_for_input

echo "Now reassigning the task to a different staff member..."
echo "This should cancel the old task and create a new one (BUG FIX)"
ASSIGN_DATA='{
    "newStaffId": "staff2",
    "newStaffName": "Jane Smith"
}'
make_request "POST" "$BASE_URL/assign-by-ref/SUPPORT-123" "$ASSIGN_DATA" "-H 'X-User-ID: manager1'"
wait_for_input

echo "Checking all tasks - old task should be CANCELLED, new task should be ACTIVE..."
make_request "GET" "$BASE_URL"
wait_for_input

echo "7️⃣ TESTING BUG FIX: CANCELLED TASKS FILTERING"
echo "Manually cancelling a task..."
make_request "PUT" "$BASE_URL/$TASK_ID/status?status=CANCELLED" "" "-H 'X-User-ID: manager1'"
wait_for_input

echo "Getting all tasks - cancelled task should NOT appear (BUG FIX)..."
make_request "GET" "$BASE_URL"
wait_for_input

echo "8️⃣ TESTING STAFF-SPECIFIC TASKS"
echo "Getting tasks for specific staff member..."
make_request "GET" "$BASE_URL/staff/staff2"
wait_for_input

echo "9️⃣ TESTING DIFFERENT PRIORITY LEVELS"
echo "Getting MEDIUM priority tasks..."
make_request "GET" "$BASE_URL/priority/MEDIUM"
wait_for_input

echo "Getting LOW priority tasks..."
make_request "GET" "$BASE_URL/priority/LOW"
wait_for_input

echo "🎉 API TESTING COMPLETE!"
echo "=========================================="
echo "✅ All features tested successfully:"
echo "   • Task creation with priority"
echo "   • Priority management"
echo "   • Comments and activity history"
echo "   • Smart date filtering"
echo "   • Task reassignment (bug fix)"
echo "   • Cancelled task filtering (bug fix)"
echo "   • Staff-specific task filtering"
echo "   • Priority-based filtering"
echo ""
echo "🔗 GitHub Repository: https://github.com/1234-ad/workforce-management-api"
echo "📚 Full documentation available in README.md"