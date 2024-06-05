FROM openjdk:22-jdk-slim

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

COPY src ./src

RUN ./mvnw package -DskipTests=true

CMD ["java", "-jar", "/app/target/meal-planner.jar"]
