FROM openjdk:17
EXPOSE 8087
COPY target/api-gateway.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]