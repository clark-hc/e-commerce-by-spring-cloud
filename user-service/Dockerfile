FROM eclipse-temurin:17-jdk-jammy

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件
COPY target/user-service-*.jar /app/user-service.jar

# 运行应用程序
ENTRYPOINT ["java", "-jar", "user-service.jar", "--spring.profiles.active=docker"]