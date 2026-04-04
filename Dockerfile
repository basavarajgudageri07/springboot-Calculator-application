#---------Build-stage-------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests

# ---------- RUN STAGE ----------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]

