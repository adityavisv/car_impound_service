FROM rockylinux:9

RUN yum -y install java maven git


RUN mkdir /opt/build/
WORKDIR /opt/build/
RUN git clone https://github.com/adityavisv/car_impound_service.git
WORKDIR /opt/build/car_impound_service/
RUN mvn clean package -DskipTests
RUN ls

RUN mkdir /opt/tomcat/
WORKDIR /opt/tomcat/
RUN curl -O https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.71/bin/apache-tomcat-9.0.71.tar.gz
RUN tar xzvf apache*.tar.gz
RUN mv apache-tomcat-9.0.71/* /opt/tomcat/.





WORKDIR /opt/tomcat/webapps
RUN mv /opt/build/car_impound_service/target/impoundsrv-0.0.1-SNAPSHOT.war .

EXPOSE 8080
CMD ["/opt/tomcat/bin/catalina.sh", "run"]