call mvn clean install -f pubweb-user-service/pom.xml -DskipTests
call java -Dspring.profiles.active=docker -jar pubweb-user-service/target/pubweb-user-service-0.0.1.jar
