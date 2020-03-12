FROM openjdk:13
COPY build/libs/meter-1.0-SNAPSHOT.jar /app/my-app-1.0.jar
EXPOSE 6565
CMD ["java", "-jar", "/app/my-app-1.0.jar"]
