FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

COPY gateway/pom.xml .
RUN mvn dependency:go-offline

COPY gateway/src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine
ARG PROFILE=dev
ARG JWT_SECRET=changeme

WORKDIR /app
COPY --from=build /build/target/gateway-*.jar app.jar

EXPOSE 8222

ENV JWT_SECRET=${JWT_SECRET}

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","app.jar"]
