#!/bin/bash
# The following are the steps used to initialize the Oracle database for the TMX application.
# 1. Install the Oracle database
#    - Download the Oracle database from the Oracle website.
#    - Follow the installation instructions for your operating system, 
#      specify password for SYS and SYSTEM users.
#    - Start the Oracle database.
#
# 2. Create the TMX user and grant privileges
#	- Connect to the Oracle database as SYSDBA.
#	- Create a new user for the TMX application.
#         create user tmx_user identified by tmx_password;
#         select * from user$ where upper(name)='TMX_USER';
#         select * from all_users where upper(username)='TMX_USER';
#         select * from dba_users where upper(username)='TMX_USER';
#         select username, default_tablespace from dba_users where upper(username)='TMX_USER';
#	- Grant the necessary privileges to the TMX user.
#         grant connect, resource, create session, create table, create view, create procedure, create trigger, create sequence to tmx_user;
#   - Allocate quota to the tablespace for the TMX application.
#        alter user tmx_user quota unlimited on users;
# 3. Create the Oracle database schema
echo exit | sqlplus tmx_user/tmx_password@localhost:1521/xepdb1 @schema-oracle.sql 
#
# 4. Create the Oracle database data
echo exit | sqlplus tmx_user/tmx_password@localhost:1521/xepdb1 @data-oracle.sql
#
# 5. Query the Oracle database
echo exit | sqlplus tmx_user/tmx_password@localhost:1521/xepdb1 @query-oracle.sql