FROM openjdk:8-alpine
EXPOSE 8080

RUN mkdir /app
COPY . /app


RUN apk add --no-cache nss
RUN apk add --no-cache maven

WORKDIR /app

RUN mvn clean install
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "target/pubweb-user-service-0.0.1.jar"]
#RUN java -jar -Dspring.profiles.active=docker target/pubweb-user-service-0.0.1.jar

