def call(){
  echo "starting maven build"
  sh 'cmd /c mvn clean package -DskipTests'  
}

