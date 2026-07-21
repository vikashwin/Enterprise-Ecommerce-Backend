# helps to make this application as an image

FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/Ecommerce-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]

# multi-stage help to manual maven packaging
# docker compose up --build
# docker compose down
# docker compose up
# docker logs redis
# docker logs springboot
