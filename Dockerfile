FROM openjdk:17-jdk-slim
WORKDIR /spring
ARG JAR_FILE=/build/libs/hertz-*.jar
COPY ${JAR_FILE} /spring/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Xms512m", "-Xmx2048m", "-jar", "/spring/app.jar"]