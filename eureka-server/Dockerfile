FROM eclipse-temurin:17-jdk-jammy

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件
COPY target/eureka-server-*.jar /app/eureka-server.jar

# 运行应用程序
ENTRYPOINT ["java", "-jar", "eureka-server.jar", "--spring.profiles.active=docker"]