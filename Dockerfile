FROM openjdk:24-jdk-slim
WORKDIR /./
COPY target/*.jar BotC.jar
CMD ["java", "-jar", "./BotC.jar"]
