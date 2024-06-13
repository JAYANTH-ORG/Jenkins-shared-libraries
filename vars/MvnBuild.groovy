def call(Map params = [:]) {
  echo "Starting Maven build step"
  mavenGoals = params.goals ?: 'clean package'
  
  try {
    echo "Running Maven build with goals: ${mavenGoals}"
    bat "mvn ${mavenGoals ?: 'clean package'} ${params.options ?: ''}"
  } catch (Exception e) {
    echo "Maven build failed: ${e.message}"
    // Add error handling logic here (e.g., send notifications, log to a file)
  }
}
