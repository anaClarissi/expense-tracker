#!/bin/bash
PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"

echo "🔨 Building project..."
mvn clean package -q

echo "📦 Creating execution script..."
cat > /tmp/expense-tracker << SCRIPT
#!/bin/bash
java -jar $PROJECT_DIR/target/expense-tracker-1.0-SNAPSHOT.jar "\$@"
SCRIPT

chmod +x /tmp/expense-tracker
sudo mv /tmp/expense-tracker /usr/local/bin/expense-tracker

echo "✅ Installed successfully!"
echo "👉 Use: expense-tracker --help"