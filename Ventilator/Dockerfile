FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} venti.jar
RUN apk add --no-cache fontconfig ttf-dejavu
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/venti.jar"]