FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

COPY src src
RUN ./mvnw install -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

VOLUME /tmp

COPY --from=build /workspace/app/target/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
