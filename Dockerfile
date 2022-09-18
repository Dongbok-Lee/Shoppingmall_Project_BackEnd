FROM openjdk:8-jre-alpine
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build/libs/shopping-0.0.1-SNAPSHOT.jar shopping-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "shopping-0.0.1-SNAPSHOT.jar"]
