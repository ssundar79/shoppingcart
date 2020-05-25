# SHOPPINCART
CAMEL+SPRING BOOT+APACHE KAFKA+MONGO DB

# Overview
# Shoppingcart -Producer
This microservice which will get Post request as json from Rest endpoint.This request will be saved in to Kafka messaging topic called ProductTopic. 

# Shoppingcart-Consumer
This Microservice will listen  from ProductTopic  and the payload request will saved in to Mongo DB.

CAMEL: Camel is open source integration framework that empowers you to quickly and easily integrate various systems consuming or producing data. Using consuming less and smart code is acheived.
APACHE KAFKA: Apache Kafka is a distributed streaming platform that lets you publish and subscribe to streams of records
Publish and subscribe to streams of records, similar to a message queue or enterprise messaging system.
Store streams of records in a fault-tolerant durable way.Process streams of records as they occur.
MONGO DB : Mongo DB is schema less No sql Database

# Features in this microservice:
1)Json payload can be posted in any structure,
2) The payload will be saved in to Kafka topic first as said Kafka is fault tolerant messaging queue, the message will be persist even the Database Mongo db is down. And later the messge can be saved in to database  from topic.
3)If DB down message from topic can be retried(3 times) with certain delay seconds using Camel retry feature and exception can be handled. currently display Failure message.
3)JUnit test using mockito is covered
4)Maintanance of code is easy as less code and camel framework used.


# To Build the application
mvn clean install

# To Run  this application
mvn spring-boot:run

# To execute Junit Test in this application
mvn -Dtest=ShoppingCartApplicationTest test

# Url to hit Postman:
http://localhost:8092/api/addproduct

# Json payload: 
{
	"productID": 110,
	"productName":"IPHONE 10 RED",
	"productPrice":600
	
}
Or

{
	"productID": 233,
	"productName":"SAMSUNG 10 WHITE",
	"productPrice":600,
	"quantity":2
	
}


# # TODO:
Authetication mechanism of http endpoints, Mongo Database
Expose GetProduct Rest services.
Refactoring, removing the hardcodings, POM cleanups


