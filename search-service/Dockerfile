# 1. 빌드 스테이지
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# 루트 필수 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# [핵심] 모든 모듈의 build.gradle 파일을 복사하여 Gradle 구성 에러 방지
# 빌드 대상이 아니더라도 구조상 정의되어 있다면 폴더가 존재해야 합니다.
COPY api-gateway/build.gradle api-gateway/
COPY common/build.gradle common/
COPY order-service/build.gradle order-service/
COPY payment-service/build.gradle payment-service/
COPY product-service/build.gradle product-service/
COPY search-service/build.gradle search-service/

RUN chmod +x ./gradlew

# 실제 빌드에 필요한 소스만 복사
COPY common common
COPY search-service search-service

# 빌드 실행
RUN ./gradlew :search-service:bootJar -x test --no-daemon

# 2. 실행 스테이지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 빌드된 JAR 결과물 복사
COPY --from=build /app/search-service/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]