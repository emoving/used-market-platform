# 1. 빌드 스테이지
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# 루트의 실행/설정 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# [수정] 모든 모듈의 build.gradle 파일을 복사 (Gradle 구성 에러 방지)
# 폴더 구조만 미리 만들어둡니다.
COPY api-gateway/build.gradle api-gateway/
COPY common/build.gradle common/
COPY order-service/build.gradle order-service/
COPY payment-service/build.gradle payment-service/
COPY product-service/build.gradle product-service/
COPY search-service/build.gradle search-service/

RUN chmod +x ./gradlew

# 실제 빌드에 필요한 소스 코드만 복사
COPY common common
COPY product-service product-service

# 빌드 실행
RUN ./gradlew :product-service:bootJar -x test --no-daemon

# 2. 실행 스테이지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/product-service/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]