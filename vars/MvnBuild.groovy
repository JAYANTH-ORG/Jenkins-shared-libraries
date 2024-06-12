def call(){
  echo "starting maven build step"
  sh 'cmd /c mvn clean package -DskipTests'  
}

