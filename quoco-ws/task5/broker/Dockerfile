FROM openjdk:8-jre-alpine
ENV URLS ""
COPY target/broker-1.0.0-allinone.jar /broker.jar
CMD sleep 5 && /usr/bin/java -jar /broker.jar $URLS