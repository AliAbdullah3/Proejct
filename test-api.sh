#!/bin/bash

echo "🧪 Testing Clothes Warehouse API Endpoints"
echo "=========================================="

BASE_URL="http://localhost:8080/api"
AUTH="admin:admin123"

echo "📋 Testing Distribution Centres API..."
echo ""

# Test 1: Get all distribution centres
echo "1️⃣  GET /api/distribution-centres"
curl -u $AUTH -X GET "$BASE_URL/distribution-centres" -H "Content-Type: application/json" | jq '.'
echo -e "\n"

# Test 2: Get all items in distribution centres
echo "2️⃣  GET /api/distribution-centres/items"
curl -u $AUTH -X GET "$BASE_URL/distribution-centres/items" -H "Content-Type: application/json" | jq '.'
echo -e "\n"

# Test 3: Create a new distribution centre
echo "3️⃣  POST /api/distribution-centres (Create new centre)"
NEW_CENTRE='{
  "name": "Test Distribution Centre",
  "latitude": 43.7000,
  "longitude": -79.4000
}'
curl -u $AUTH -X POST "$BASE_URL/distribution-centres" \
     -H "Content-Type: application/json" \
     -d "$NEW_CENTRE" | jq '.'
echo -e "\n"

# Test 4: Add item to distribution centre (assuming centre ID 1 exists)
echo "4️⃣  POST /api/distribution-centres/1/items (Add item to centre)"
NEW_ITEM='{
  "name": "Test Item",
  "brand": "NIKE",
  "yearOfCreation": 2023,
  "price": 99.99,
  "quantity": 10
}'
curl -u $AUTH -X POST "$BASE_URL/distribution-centres/1/items" \
     -H "Content-Type: application/json" \
     -d "$NEW_ITEM" | jq '.'
echo -e "\n"

echo "✅ API testing completed!"
echo ""
echo "💡 Tips:"
echo "   - Make sure the application is running on http://localhost:8080"
echo "   - Use credentials: admin/admin123 or dcmanager/dcmanager123"
echo "   - Check the application logs for detailed information"
