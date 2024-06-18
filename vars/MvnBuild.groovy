def call(Operation) {
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

     def server = Artifactory.server('artifactory') // Replace with your Artifactory server ID
        def uploadSpec = """{
            "files": [
                {
                    "pattern": "target/*.war",
                    "target": "test/"
                }
            ]
        }"""
        retry(3) {
            def buildInfo = server.upload spec: uploadSpec
            server.publishBuildInfo buildInfo
        }


    // if (params.Operation == 'build' || params.Operation == 'test') {
    //     steps.echo "Running SonarQube analysis"
    //     steps.withSonarQubeEnv('SonarQube') {
    //         steps.sh "mvn sonar:sonar"
    //     }
    // }
  } catch (Exception e) {
    echo "Maven build failed: ${e.message}"
    // Add error handling logic here (e.g., send notifications, log to a file)
  }
}
