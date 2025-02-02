FROM openjdk:17
WORKDIR /app
COPY build/libs/weblab4.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]