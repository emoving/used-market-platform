FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# 프로젝트 구조 인식용 (필수)
COPY api-gateway/build.gradle api-gateway/
COPY common/build.gradle common/
COPY order-service/build.gradle order-service/
COPY payment-service/build.gradle payment-service/
COPY product-service/build.gradle product-service/
COPY search-service/build.gradle search-service/

RUN chmod +x ./gradlew

# API Gateway 빌드에 필요한 소스만 복사
COPY common common
COPY api-gateway api-gateway

RUN ./gradlew :api-gateway:bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/api-gateway/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]