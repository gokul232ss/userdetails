FROM openjdk:11
EXPOSE 8083
ADD target/userdetails_local.jar userdetails_local.jar
ENTRYPOINT ["java", "-jar", "userdetails_local.jar"]