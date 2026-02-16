# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

COPY config-server/pom.xml .
RUN mvn dependency:go-offline

COPY config-server/src ./src
RUN mvn clean package -DskipTests

# ---------- RUNTIME STAGE ----------
FROM amazoncorretto:17-alpine
ARG PROFILE=dev

WORKDIR /app

COPY --from=build /build/target/config-server-*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","app.jar"]
