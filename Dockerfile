FROM openjdk:17-jdk-alpine
MAINTAINER ying.com
WORKDIR /usr/app
EXPOSE 8080
RUN addgroup -S springdocker && adduser -S springdocker -G springdocker
USER springdocker:springdocker
COPY target/*.jar ms-user-management.jar
ENTRYPOINT ["java","-jar","ms-user-management.jar"]