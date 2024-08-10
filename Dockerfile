FROM maven:3.9.8-eclipse-temurin-21-alpine as builder

COPY ./src src/
COPY ./pom.xml pom.xml

RUN mvn clean verify

FROM eclipse-temurin:21-jre-alpine

COPY --from=builder target/*.jar task-management.jar
EXPOSE 8080

CMD ["java", "-jar", "task-management.jar"]