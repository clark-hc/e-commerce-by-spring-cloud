FROM eclipse-temurin:17-jdk-jammy

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件
COPY target/product-service-*.jar /app/product-service.jar

# 运行应用程序
ENTRYPOINT ["java", "-jar", "product-service.jar", "--spring.profiles.active=docker"]