FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

COPY customer/pom.xml .
RUN mvn dependency:go-offline

COPY customer/src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine
ARG PROFILE=dev

WORKDIR /app
COPY --from=build /build/target/customer-*.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","app.jar"]
