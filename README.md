A REST API of translation memory (TMX, XLIFF and TBX), implemented with Oracle/Postgres and Spring Boot 

The following are the steps used to initialize the Oracle database for the TMX application:
1. Install the Oracle database
   - Download the Oracle database from the Oracle website.
   - Follow the installation instructions for your operating system,
     specify password for SYS and SYSTEM users, for example, admin
   - Start the Oracle database listener
     lsnrctl start
     lsnrctl status
     lsnrctl stop

2. Create the TMX user and grant privileges
   - Connect to the Oracle database as SYSDBA.
   - Create a new user for the TMX application.
        create user tmx_user identified by tmx_password;
        select * from user$ where upper(name)='TMX_USER';
        select * from all_users where upper(username)='TMX_USER';
        select * from dba_users where upper(username)='TMX_USER';
        select username, default_tablespace from dba_users where upper(username)='TMX_USER';
   - Grant the necessary privileges to the TMX user.
        grant connect, resource, create session, create table, create view, create procedure, create trigger, create sequence to tmx_user;
   - Allocate quota of the tablespace for the TMX application.
        alter user tmx_user quota unlimited on users;
3. Create the Oracle database schema
   echo exit | sqlplus tmx_user/tmx_password@localhost:1521/xepdb1 @schema-oracle.sql

4. Init the application data
   echo exit | sqlplus tmx_user/tmx_password@localhost:1521/xepdb1 @data-oracle.sql

5. Create the Oracle procedures
   echo exit | sqlplus tmx_user/tmx_password@localhost:1521/xepdb1 @procedure-oracle.sql

The following are the steps to build and run the TMX application:
1. build the application:
   mvn clean install

2. run the application:
   mvn spring-boot:run 

The following are the steps to test the TMX application:
1. Download the OpenAPI document in a JSON file: 
   http://localhost:8080/api-docs

2. Import the OpenAPI document into a Postman's collection
   ....

3. Run the test script...

