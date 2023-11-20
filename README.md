#  Notarius - Clients and Operations CRUD with Levenshtein distance search
.
[![Docker Hub package][dockerhub-badge]][dockerhub-link]

[dockerhub-badge]: https://img.shields.io/badge/images%20on-Docker%20Hub-blue.svg
[dockerhub-link]: https://hub.docker.com/repository/docker/agusalex/notarius "Docker Hub Image"
[github-link]: https://github.com/agusalex/Notarius "Github"

Using **Levenshtein distance** prediction algorithm for the search bar.
**Vaadin** as the frontend/backend and **H2** for database (can also use MySQL/MariaDB see Parameters section )
# Running the project:
You'll need **Docker** installed

    docker run -e DEBUG=true -p 8080:8080/tcp agusalex/notarius
![image](https://user-images.githubusercontent.com/15642727/46241343-f0a5c180-c38d-11e8-887d-8d76746a81bc.png)

# Building the project:
You'll need **Docker** and **Maven** installed

	
    mvn clean build
    docker build -t "notarius" .
    docker run -e DEBUG=true -p 8080:8080/tcp notarius
   
  
# Container Parameters
| Parameter |Value  | Example|
|--|--|--|
|DEBUG| If this is SET it uses internal H2 DB  | true |
|URL| The URL of the external DB (if DEBUG = FALSE)  | 127.0.0.1:3306 |
|URL| The URL of the external DB (if DEBUG = FALSE) | 127.0.0.1:3306 |
|USER| The USER of the external DB (if DEBUG = FALSE) | root |
|PASS| The PASSWORD of the external DB (if DEBUG = FALSE) | root |

# Exporting DB:

    call CSVWRITE ( 'C:/MyFolder/11-7-17.txt', 'SELECT * FROM OPERACION' ) 
    
    SELECT * FROM PERSONAS insert into PERSONAS select * from 
    (SELECT * FROM CSVREAD('C://ESD/personas.txt'));
    
    CREATE TABLE OPERACION2 AS SELECT * FROM CSVREAD('C://ESD/test.txt')
    insert into OPERACION
        select * from OPERACION2;
