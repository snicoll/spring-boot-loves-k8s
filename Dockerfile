FROM openjdk:8-jdk-alpine AS builder
WORKDIR source
ARG JAR_FILE=scribe/target/scribe-*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:8-jre-alpine
WORKDIR application
COPY --from=builder source/dependencies/ ./
COPY --from=builder source/spring-boot-loader/ ./
COPY --from=builder source/webjars/ ./
COPY --from=builder source/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
