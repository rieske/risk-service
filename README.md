# Simple Risk Decision Service

[![Actions Status](https://github.com/rieske/risk-service/workflows/build/badge.svg)](https://github.com/rieske/risk-service/actions) [![Build Status](https://travis-ci.org/rieske/risk-service.png?branch=master)](https://travis-ci.org/rieske/risk-service) [![Build Status](https://drone.io/github.com/rieske/risk-service/status.png)](https://drone.io/github.com/rieske/risk-service/latest) [![Coverage Status](https://coveralls.io/repos/github/rieske/risk-service/badge.svg?branch=master)](https://coveralls.io/github/rieske/risk-service?branch=master)

RESTful API for simplified risk decision service

## Building

Gradle wrapper is included so the simplest thing to do is:
`./gradlew build`

## Running

To spawn an embedded Tomcat on port 8080:
`./gradlew run`

## API

The API contains a single `/decision` endpoint. It will return 200 on any correctly formed request with risk decision details in the response object.
Other standard HTTP status codes are also returned:
400 - bad request (incorrectly formed json/field validation failure)
415 - unspecified or incorrect content-type (application/json is required)

##### Request structure

```json
{
	"email": string (valid email), 
	"first_name": string (not empty),
	"last_name": string (not empty),
	"amount": integer (not negative)
}
```

##### Response structure

```json
{
	"accepted": boolean, 
	"reason": string
}
```

##### Example interaction

`curl -d '{"email":"a@b.se", "first_name":"a","last_name":"b","amount":10}' http://localhost:8080/decision -H "Content-Type: application/json"`

Note the mandatory `Content-Type` header.


## Implementation notes

Spring Boot is used as the main framework (Spring's MVC and DI together with boot actuator).

Risk engine semantics:

* If the amount is less than 10, it should always be accepted
* If the amount is higher than 1000, it should always be rejected with the reason "amount"
* If the sum of purchases from a particular email is larger than 1000 (including current purchase), it should be rejected with reason "debt"

The first rule takes precedence over the third one - if the debt of some email address is over 1000, amounts less than 10 are still accepted. If they were to be rejected, the first rule would effectively be "hidden" by the other two and any amount less than 1000 or total debt would be accepted.