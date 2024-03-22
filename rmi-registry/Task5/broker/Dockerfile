FROM openjdk:8-jre-alpine
COPY target/*-allinone.jar /broker.jar
CMD sleep 5 && /usr/bin/java -jar /broker.jar