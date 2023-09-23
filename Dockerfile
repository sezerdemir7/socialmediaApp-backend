FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/secoialApp-0.0.1-SNAPSHOT.jar socialapp.jar
ENTRYPOINT ["java","-jar","socialapp.jar"]