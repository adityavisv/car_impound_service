FROM rockylinux:9

RUN mkdir /opt/tomcat/

WORKDIR /opt/tomcat/

RUN curl -O https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.70/bin/apache-tomcat-9.0.70.tar.gz
RUN tar xzvf apache*.tar.gz
RUN mv apache-tomcat-9.0.70/* /opt/tomcat/.
RUN yum -y install java

WORKDIR /opt/tomcat/webapps
COPY impoundsrv-0.0.1-SNAPSHOT.war .

EXPOSE 8080
CMD ["/opt/tomcat/bin/catalina.sh", "run"]