FROM openjdk:17-jdk-alpine3.13
WORKDIR /app
COPY target/springboot-k8s-demo-1.0.0-SNAPSHOT.jar  /app/springboot-k8s-demo-1.0.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "springboot-k8s-demo-1.0.0-SNAPSHOT.jar"]