FROM openjdk:17-jdk-alpine

COPY target/websocket-server-0.0.1.war weebsocket-server.war

EXPOSE 8080
ENTRYPOINT ["java","-jar","/weebsocket-server.war"]