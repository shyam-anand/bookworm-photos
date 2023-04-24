FROM openjdk:17-jdk-alpine
LABEL authors="shyam"
MAINTAINER shyam-anand.com
COPY target/bookworm-photos-0.0.1-SNAPSHOT.jar bookworm-photos.jar
ENV AWS_ACCESS_KEY_ID AKIAW2ESVQQFNMM3VD3E
ENV AWS_SECRET_ACCESS_KEY "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"
ENTRYPOINT ["java", "-jar", "/bookworm-photos.jar"]