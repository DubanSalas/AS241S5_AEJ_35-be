# Stage 1: Build with Maven
FROM maven:3.9.0-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with Java
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]


# docker build -t juan321/spotify-be:1.0 .

# docker run -d --name spotify-be -p 8085:8085 juan321/spotify-be:1.0

# docker push juan321/spotify-be:1.0

# 2. Build de tu imagen
# docker build -t salasnapandubanalexander/duban-salas-35-be:1.0 .

# 3. Login a Docker Hub
# docker login

# 4. Push
# docker push salasnapandubanalexander/duban-salas-35-be:1.0