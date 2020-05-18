FROM openjdk:11
ADD target/main-api.jar main-api.jar
EXPOSE 5555
ENTRYPOINT ["java", "-jar", "main-api.jar"]
