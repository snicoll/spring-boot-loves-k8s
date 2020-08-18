FROM openjdk:8-jdk-alpine AS builder
WORKDIR source
ARG JAR_FILE=scribe/target/scribe-*.jar
COPY ${JAR_FILE} application.jar
RUN jar -xf ./application.jar

FROM openjdk:8-jre-alpine
WORKDIR application
COPY --from=builder source/BOOT-INF/lib lib
COPY --from=builder source/META-INF app/META-INF
COPY --from=builder source/BOOT-INF/classes app
ENTRYPOINT ["java","-cp","app:lib/*","io.spring.sample.scribe.ScribeApplication"]
