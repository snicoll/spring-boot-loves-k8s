FROM openjdk:8-jre-alpine
WORKDIR application
ARG JAR_FILE=scribe/target/scribe-*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar","application.jar"]
