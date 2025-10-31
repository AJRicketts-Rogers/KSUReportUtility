create  sequence

 CREATE SEQUENCE  "OM_KSU_UTILITY_CONFIG_SEQ"  MINVALUE 1 MAXVALUE 2 INCREMENT BY 1;
 CREATE SEQUENCE  "OM_KSU_UTILITY_TRACKER_SEQ"  MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1;



create  tabel
####
  -- This  Table  for Configuration to be  used  by  JAVA Program(Configurable)
 CREATE TABLE "OM_KSU_UTILITY_CONFIG" 
   (	
    "NAME" VARCHAR2(20 BYTE), 
	"LAST_RUN_TIME" TIMESTAMP (6), 
	"INTERVAL" NUMBER, 
	"START_TIME" TIMESTAMP (6), 
	"END_TIME" TIMESTAMP (6), 
	"ID" NUMBER NOT NULL ENABLE, 
	"OPERATION_LIMIT" NUMBER, 
	"LAST_EXECUTION_STATUS" CLOB, 
	"LOCKED" VARCHAR2(20 BYTE), 
	 CONSTRAINT "OM_KSU_UTILITY_CONFIG_PK" PRIMARY KEY ("ID")
   )
   
--Insert Data  to OM_KSU_UTILITY_CONFIG
Insert into OM_KSU_UTILITY_CONFIG (NAME,LAST_RUN_TIME,INTERVAL,START_TIME,END_TIME,ID,OPERATION_LIMIT,LOCKED) values ('OSMORDEREXECUTER',null,null,null,null,2,100,null);
Insert into OM_KSU_UTILITY_CONFIG (NAME,LAST_RUN_TIME,INTERVAL,START_TIME,END_TIME,ID,OPERATION_LIMIT,LOCKED) values ('OSMORDERLOADER',null,1800,null,to_timestamp('23-03-23 16:00:43.274000000 AM','DD-MM-RR HH12:MI:SSXFF AM'),1,0,'OPEN');

#####################################################
  CREATE TABLE "OM_KSU_UTILITY_TRACKER" 
   (	
    "ID" NUMBER NOT NULL ENABLE, 
	"OSM_ID" NUMBER, 
	"ORDER_NUMBER" VARCHAR2(100 BYTE), 
	"CREATED_DATE" TIMESTAMP (6), 
	"COMPLETED_DATE" TIMESTAMP (6), 
	"EXECUTION_STATUS" VARCHAR2(20 BYTE), 
	"OPERATION_PAYLOAD" CLOB, 
	"LAST_UPDATED" TIMESTAMP (6), 
	"TASK_NAME" VARCHAR2(20 BYTE), 
	"KSU_STATUS" VARCHAR2(20 BYTE), 
	"RESULT_PAYLOAD" CLOB, 
	 CONSTRAINT "OM_KSU_UTILITY_TRACKER_PK" PRIMARY KEY ("ID")
	 )
	 
###################################################   Application Properties
applicationruntime.properties	 

###############################  Setup  for  Application
build maven to generate  jar file.

copy the  jar file and  filterconfig.xml and applicationruntime.properties	  to same  folder.
and execute
1:  for  Running the application  for  OSM  Completed Order
java -jar KSUReportUtility-1.jar LOADOSMONLY

2:  for  Running the application  for generating ksu report from Completed OSM Orders
java -jar KSUReportUtility-1.jar LOADKSUREPORT