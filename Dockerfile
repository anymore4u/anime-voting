# Use a base image with Java 17
FROM openjdk:17-jdk-alpine

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Copia o arquivo JAR diretamente para a imagem
COPY target/anime-voting-0.0.1-SNAPSHOT.jar anime-voting-app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/anime-voting-app.jar"]