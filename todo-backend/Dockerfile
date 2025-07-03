# 基于官方 OpenJDK 17 运行时镜像
FROM eclipse-temurin:17-jre-alpine

# 设置工作目录
WORKDIR /app

# 复制 jar 包
COPY target/todo-backend-1.0-SNAPSHOT.jar app.jar

# 设置时区（可选）
ENV TZ=Asia/Shanghai

# 运行 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]
