FROM maven:3.6.3 as maven
LABEL COMPANY="TEST"
LABEL MAINTAINER="support@TEST.com"
LABEL APPLICATION="Sample Spring Boot Application"

WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package 

FROM adoptopenjdk/openjdk16:alpine-jre
ARG TOMCAT_FILE_PATH=/docker 
	
#Data & Config - Persistent Mount Point
ENV APP_DATA_FOLDER=/var/lib/SampleApp
ENV SAMPLE_APP_CONFIG=${APP_DATA_FOLDER}/config/
	
ENV CATALINA_OPTS="-Xms1024m -Xmx4096m -XX:MetaspaceSize=512m -	XX:MaxMetaspaceSize=512m -Xss512k"

#Move over the War file from previous build step
WORKDIR /usr/local/tomcat/webapps/
COPY --from=maven /usr/src/app/target/SampleApp.war /usr/local/tomcat/webapps/api.war

COPY ${TOMCAT_FILE_PATH}/* ${CATALINA_HOME}/conf/

WORKDIR $APP_DATA_FOLDER

EXPOSE 8080
ENTRYPOINT ["catalina.sh", "run"]
