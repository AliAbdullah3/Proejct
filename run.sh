#!/bin/bash

echo "🏭 Starting Clothes Warehouse Management System (Development Mode)"
echo "=================================================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven first."
    exit 1
fi

# Clean and build the project
echo "🔨 Building the project..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi

# Run the application in development mode (H2 database)
echo "🚀 Starting application in development mode..."
echo "📊 Database: H2 (In-memory)"
echo "🌐 Application will be available at: http://localhost:8080"
echo "🗄️  H2 Console will be available at: http://localhost:8080/h2-console"
echo ""
echo "Demo Login Credentials:"
echo "👤 Admin: admin / admin123"
echo "👤 Employee: employee / emp123"
echo "👤 Customer: customer / cust123"
echo ""
echo "API Access (Basic Auth):"
echo "🔑 admin / admin123"
echo "🔑 dcmanager / dcmanager123"
echo ""

java -jar target/clothes-warehouse-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
