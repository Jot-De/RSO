call mvn clean install -f pub-service/pom.xml -DskipTests
call java -Dspring.profiles.active=docker -jar pub-service/target/pub-service-1.0.0.jar
cmd /k
