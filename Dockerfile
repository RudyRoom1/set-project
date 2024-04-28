# START WITH THE OFFICIAL GRADLE IMAGE AS BUILD IMAGE
FROM gradle:jdk17 AS builder

# SET JAVA_HOME
ENV JAVA_HOME=/opt/java/openjdk

COPY . /home/gradle/src

# SET WORKDIR FOR GRADLE
WORKDIR /home/gradle/src

# BUILD THE APPLICATION
RUN gradle build --no-daemon

# START NEW STAGE FOR RUNNING THE APPLICATION
FROM openjdk:17-jdk-slim

# COPY THE JAR FILE FROM THE FIRST STAGE
COPY --from=builder /home/gradle/src/build/libs/*.jar /image_service.jar

# MAKE THE APP CONTAINER LISTEN ON PORT 8080 BY DEFAULT
EXPOSE 8080

# START THE APPLICATION
#CMD ["java", "-jar", "/image_service.jar"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/image_service.jar"]
