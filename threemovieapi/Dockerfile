FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/threemovieapi-1.0.0-plain.jar

COPY ${JAR_FILE} app.jar

VOLUME /tmp

EXPOSE 8081

ENTRYPOINT ["java", "-jar","/app.jar"]
