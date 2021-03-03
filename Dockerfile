FROM openjdk:11-jdk-slim
RUN apt-get update && apt-get install -y iputils-ping
VOLUME /tmp
ADD /target/back-endv1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]