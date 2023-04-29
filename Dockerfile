FROM openjdk:17-jdk-alpine

COPY bookworm-photos/target/bookworm-photos-0.0.1-SNAPSHOT.jar bookworm-photos.jar

ENV AWS_ACCESS_KEY_ID=AKIAWXVQ5UEVDWSXPTW7
ENV AWS_SECRET_ACCESS_KEY=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

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/bookworm-photos.jar"]
