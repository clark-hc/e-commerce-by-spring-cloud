FROM eclipse-temurin:17-jdk-jammy

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件
COPY target/order-service-*.jar /app/order-service.jar

# 运行应用程序
ENTRYPOINT ["java", "-jar", "order-service.jar", "--spring.profiles.active=docker"]