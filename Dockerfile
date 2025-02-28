FROM openjdk:17-jdk-slim
WORKDIR /./
COPY target/*.jar BotC.jar
CMD ["java", "-jar", "./BotC.jar"]
