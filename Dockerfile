FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /tmp/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tmp/app.jar"]
RUN echo "hello world"