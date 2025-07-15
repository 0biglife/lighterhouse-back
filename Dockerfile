# 1. build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. run
FROM eclipse-temurin:21-jre
WORKDIR /app

# 빌드 결과물 복사
COPY --from=build /app/target/*.jar app.jar

# 포트 설정 - application.properties - server.port
EXPOSE 8080

# 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]
