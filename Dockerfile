# Using Java21 from amazon AWS
FROM amazoncorretto:21

# Copying the jar generated into the container
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# command to run the app
ENTRYPOINT ["java","-jar","/app.jar"]
