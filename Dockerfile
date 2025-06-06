FROM maven:3.9.4-eclipse-temurin-21 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY  . .

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/DemoProjectJava02-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE cổng do Render yêu cầu
EXPOSE 10000
ENV PORT=10000

# Chạy app với cổng lấy từ biến môi trường
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=10000"]