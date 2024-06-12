def call(){
  echo "starting maven build step"
  bat 'mvn clean package -DskipTests'  
}

