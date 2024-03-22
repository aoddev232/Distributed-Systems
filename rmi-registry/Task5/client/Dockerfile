FROM openjdk:8-jre-alpine
COPY target/*-allinone.jar /client.jar
CMD sleep 10 && /usr/bin/java -jar /client.jar broker