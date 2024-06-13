def call(Map params = [:]) {
  echo "Starting Maven build step"
  echo "Operation: ${params.Operation}" // Access the 'Operation' parameter

  // Define Maven goals based on the 'Operation' parameter
  def mavenGoals
  if (params.Operation == 'build') {
    mavenGoals = 'clean package'
  } else if (params.Operation == 'test') {
    mavenGoals = 'clean test'
  } else if (params.Operation == 'deploy') {
    mavenGoals = 'clean deploy'
  } else {
    echo "Invalid operation: ${params.Operation}"
    return
  }

  try {
    echo "Running Maven build with goals: ${mavenGoals}"
    bat "mvn ${mavenGoals} ${params.options ?: ''}"
  } catch (Exception e) {
    echo "Maven build failed: ${e.message}"
    // Add error handling logic here (e.g., send notifications, log to a file)
  }
}
