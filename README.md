call CSVWRITE ( 'C:/MyFolder/11-7-17.txt', 'SELECT * FROM OPERACION' ) 

SELECT * FROM PERSONAS insert into PERSONAS select * from 
(SELECT * FROM CSVREAD('C://ESD/personas.txt'));

O bien

CREATE TABLE OPERACION2 AS SELECT * FROM CSVREAD('C://ESD/test.txt')
insert into OPERACION
    select * from OPERACION2;
