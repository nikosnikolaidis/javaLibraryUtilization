FROM adoptopenjdk:11-jdk-hotspot
RUN apt-get update && apt-get install -y git && apt-get install -y maven
ADD target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]