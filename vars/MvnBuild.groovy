def call(){
  echo "starting maven build"
  sh 'mvn clean package -DskipTests'  
}

