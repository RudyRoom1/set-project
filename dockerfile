# START WITH THE OFFICIAL GRADLE IMAGE AS BUILD IMAGE
FROM gradle:jdk17 AS build

# SET JAVA_HOME
ENV JAVA_HOME=/opt/java/openjdk

# COPY YOUR SRC CODE INTO THE CONTAINER
COPY . /home/gradle/src

# SET WORKDIR FOR GRADLE
WORKDIR /home/gradle/src

# ECHO JAVA_HOME
RUN echo $JAVA_HOME

# BUILD THE APPLICATION
RUN gradle build --no-daemon