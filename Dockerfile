# we are extending everything from tomcat:8.0 image ...
FROM tomcat:jdk8-corretto
MAINTAINER agusalex
# COPY path-to-your-application-war path-to-webapps-in-docker-tomcat
COPY target/Notarius-1.0.3-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war