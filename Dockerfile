# Sử dụng image Java JDK chính thức
FROM openjdk:21-jdk-slim

# Tạo thư mục làm việc bên trong container
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

# Copy file JAR vào container (sửa tên file nếu khác)
COPY target/DemoProjectJava02-0.0.1-SNAPSHOT.jar app.jar

# Mở port 8080
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
