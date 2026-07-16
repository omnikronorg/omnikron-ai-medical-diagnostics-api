# ==========================================
# STAGE 1: Build the application with Maven
# ==========================================
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Copy pom.xml and download dependencies first (caching layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# ==========================================
# STAGE 2: Runtime with JRE only
# ==========================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Create a non-root user for security
RUN groupadd -r aimda && useradd -r -g aimda aimda

# Copy the JAR from the builder stage
COPY --from=builder /build/target/*.jar app.jar

# Switch to non-root user
USER aimda

# Expose the application port
EXPOSE 8030

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]