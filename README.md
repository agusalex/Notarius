#
#  Notarius - Clients and Operations CRUD
![https://hub.docker.com/repository/docker/agusalex/notarius](https://img.shields.io/badge/Docker-build-green)
# Running the project:
You'll need **Docker** installed

    docker run -e DEBUG=true -p 8080:8080/tcp agusalex/notarius
![image](https://user-images.githubusercontent.com/15642727/46241343-f0a5c180-c38d-11e8-887d-8d76746a81bc.png)

# Building the project:
You'll need **Docker** and **Maven** installed

	
    mvn clean build
    docker build -t "notarius" .
    docker run -e DEBUG=true -p 8080:8080/tcp notarius
   
  


# Exporting DB:

    call CSVWRITE ( 'C:/MyFolder/11-7-17.txt', 'SELECT * FROM OPERACION' ) 
    
    SELECT * FROM PERSONAS insert into PERSONAS select * from 
    (SELECT * FROM CSVREAD('C://ESD/personas.txt'));
    
    CREATE TABLE OPERACION2 AS SELECT * FROM CSVREAD('C://ESD/test.txt')
    insert into OPERACION
        select * from OPERACION2;
