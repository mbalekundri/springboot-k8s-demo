FROM openjdk:17-jdk-alpine3.13
WORKDIR /app
ENV APP_ENV=dev
COPY target/springboot-k8s-demo-1.1.jar  /app/springboot-k8s-demo-1.1.jar
EXPOSE 8080
CMD ["java", "-jar", "springboot-k8s-demo-1.1.jar"]