FROM openjdk:17-jdk-alpine

COPY target/bookworm-photos-0.0.1-SNAPSHOT.jar bookworm-photos.jar

ENV AWS_ACCESS_KEY_ID=AKIAW2ESVQQFG25X35DJ
ENV AWS_SECRET_ACCESS_KEY=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

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/bookworm-photos.jar"]