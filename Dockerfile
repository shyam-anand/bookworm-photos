FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17-jdk-alpine
COPY --from=build /usr/src/app/target/bookworm-photos/target/bookworm-photos-0.0.1-SNAPSHOT.jar /usr/app/bookworm-photos.jar
ARG key
ARG secret
ENV AWS_ACCESS_KEY_ID=$key
ENV AWS_SECRET_ACCESS_KEY=$secret
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/bookworm-photos.jar"]
