### Requirements
* JDK 8,17

## APIs
### Customer Actions ("/v1/customer") :
* `POST /v1/customer/loan` : Post API to create a new loan
* `GET /v1/customer/{cid}/loan` : Get all loans for CustomerId : {cid}
* `Get /v1/customer/{cid}/loan/{lid}` : Get Specific Loan details for Customer {cid}
* `PUT /v1/customer/payment` : Repay loan amount

### Admin Actions ("/v1/admin") :
* `PUT /v1/admin/loan/{id}` : Approve LoanId {id} if not already approved
* `POST /v1/admin/customer` : Create new customer. Note : We can also put this API in Customer Controller based on the business requirements.
* `GET /v1/admin/customer` : Get All Customer details
* `GET /v1/admin/loan` : Get All loan details

### Testing
* Added Functional tests for all the APIs

## Installation and Running locally :
* This is a simple java-spring based application which can be build and started from IDE as a Spring Boot application
* We can also build and package the jar using `maven package` and run the jar
* This is a maven project so we can test, build and run using mvn commands :
  * Run Tests : `mvn test`
  * Build and generate jar : `mvn install`
* This will generate a jar file in target folder [mini-aspire-0.0.1-SNAPSHOT.jar](target%2Fmini-aspire-0.0.1-SNAPSHOT.jar)
* Run the jar file to start the server : `java -jar mini-aspire-0.0.1-SNAPSHOT.jar`
* Once started you can use postman or swagger that I have integrated in the server 

## Predefined Data : 
* To make the testing easy, I have used in mem DB H2 and added some of the entries for Customer, loan and payments which will be created in H2 DB on starting the server
* Use APIs to create new entries and test the functionality

## Swagger :
* You can access swagger through `http://localhost:8080/swagger-ui/index.html#` based on your address and port

## Project Structure :
* **source directory** : `src/main/java/com/example/miniaspire/`
* **test directory** : `tst/main/java/com/example/miniaspire/`