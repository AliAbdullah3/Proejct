#!/bin/bash

echo "🏭 Starting Clothes Warehouse Management System (QA Mode)"
echo "=================================================="

# Check if Docker is installed and running
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    exit 1
fi

if ! docker info &> /dev/null; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven first."
    exit 1
fi

# Start PostgreSQL database
echo "🐘 Starting PostgreSQL database..."
docker-compose up -d postgres

# Wait for PostgreSQL to be ready
echo "⏳ Waiting for PostgreSQL to be ready..."
sleep 10

# Clean and build the project
echo "🔨 Building the project..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Please check the errors above."
    docker-compose down
    exit 1
fi

# Run the application in QA mode (PostgreSQL database)
echo "🚀 Starting application in QA mode..."
echo "📊 Database: PostgreSQL (Docker)"
echo "🌐 Application will be available at: http://localhost:8080"
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

java -jar target/clothes-warehouse-0.0.1-SNAPSHOT.jar --spring.profiles.active=qa
