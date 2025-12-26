FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

COPY api-gateway/build.gradle api-gateway/
COPY common/build.gradle common/
COPY order-service/build.gradle order-service/
COPY payment-service/build.gradle payment-service/
COPY product-service/build.gradle product-service/
COPY search-service/build.gradle search-service/

RUN chmod +x ./gradlew

# Payment 빌드에 필요한 소스만 복사
COPY common common
COPY payment-service payment-service

RUN ./gradlew :payment-service:bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/payment-service/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]