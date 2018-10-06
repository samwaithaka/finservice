Simple Account Transaction Microservice

This project is a microservice for account transactions, that handles the following: account balance query, account deposits and account withdraws. It effects withdrawal and deposits amount limits, per transaction as well as per day. It effects frequency limit as well.

Getting Started
The service can run on any server by running Maven package, and running the packaged jar, as will be shown shortly.

Prerequisites
- Maven
- Postgresql database

Installing
1. Make sure you have Postgresql database installed
2. Create a Postgres database and database user, and grant privileges, as per the following commands:

postgres=# CREATE DATABASE finservice_db;
CREATE DATABASE
postgres=# CREATE USER finservice_user WITH PASSWORD 'fdpafdfs444d^GD';
CREATE ROLE
postgres=# GRANT ALL PRIVILEGES ON DATABASE finservice_db TO finservice_user;
GRANT
postgres=# 

3. Clone the project onto the deployment machine
4. Run the following: maven package
5. Go to the target, and run the resultant jar as follows:

java -jar finservice.0.0.1.jar

Use the following endpoints
1. Query balance: http://localhost:8080/balance
2. Make deposit: http://localhost:8080/deposit/{amount} 
2. Make withdrawal: http://localhost:8080/withdraw/{amount}