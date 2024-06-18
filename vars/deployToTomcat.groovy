// vars/deployToTomcat.groovy

def call() {
    // def tomcatCredentialsId = params.tomcatCredentialsId ?: 'tomcat'
    // def tomcatUrl = params.tomcatUrl ?: 'http://localhost:8089/'
    // def contextPath = params.contextPath ?: '/home'
    // def warPattern = params.warPattern ?: '**/*.war'
  // vars/deployToTomcat.groovy

    def tomcatCredentialsId =  'tomcat'
    def tomcatUrl = 'http://localhost:8089/'
    def contextPath = '/home'
    def warPattern = '**/*.war'
    echo "Deploying artifact to tomcat server"
    
    deploy adapters: [tomcat9(credentialsId: tomcatCredentialsId, path: '', url: tomcatUrl)], 
           contextPath: contextPath, 
           war: warPattern
}


 
