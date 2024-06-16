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

    if (params.Operation == 'build' || params.Operation == 'deploy') {
      steps.echo "Deploying artifacts to Artifactory"
      def server = steps.Artifactory.server 'Artifactory-Server'  // Use the ID configured in Jenkins
      def buildInfo = steps.Artifactory.newBuildInfo()
      def rtMaven = steps.Artifactory.newMavenBuild()
      
      rtMaven.resolver server: server, releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot'
      rtMaven.deployer server: server, releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot'

      rtMaven.run pom: 'pom.xml', goals: 'deploy', buildInfo: buildInfo
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
