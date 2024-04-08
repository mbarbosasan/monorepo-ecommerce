FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app

COPY . .

RUN  chmod +x mvnw && ./mvnw install -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/produtos-0.0.1-SNAPSHOT.jar"]