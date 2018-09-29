Sistema Notarius

![image](https://user-images.githubusercontent.com/15642727/46241343-f0a5c180-c38d-11e8-887d-8d76746a81bc.png)

For exporting DB:
call CSVWRITE ( 'C:/MyFolder/11-7-17.txt', 'SELECT * FROM OPERACION' ) 

SELECT * FROM PERSONAS insert into PERSONAS select * from 
(SELECT * FROM CSVREAD('C://ESD/personas.txt'));

CREATE TABLE OPERACION2 AS SELECT * FROM CSVREAD('C://ESD/test.txt')
insert into OPERACION
    select * from OPERACION2;
