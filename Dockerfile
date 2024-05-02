FROM maven:3.8-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18-ea-8-jdk-slim
COPY --from=build /target/petopia-0.0.1-SNAPSHOT.jar petopia.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","petopia.jar"]