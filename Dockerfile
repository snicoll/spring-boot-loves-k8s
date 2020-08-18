FROM openjdk:8-jdk-alpine AS builder
WORKDIR source
ARG JAR_FILE=scribe/target/scribe-*.jar
COPY ${JAR_FILE} application.jar
RUN jar -xf ./application.jar

FROM openjdk:8-jre-alpine
WORKDIR application
COPY --from=builder source/BOOT-INF/lib BOOT-INF/lib
COPY --from=builder source/BOOT-INF/classpath.idx BOOT-INF
COPY --from=builder source/org org
COPY --from=builder source/META-INF META-INF
COPY --from=builder source/BOOT-INF/classes BOOT-INF/classes
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
