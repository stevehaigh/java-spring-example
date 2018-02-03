## Simple Rest Client that retrieves links from documents ##

Built using Spring Boot / Maven.

To build, run:

`mvn clean install`

To run:

`java -jar target/restserver-0.0.1-SNAPSHOT.jar`

To test, try:

`curl http://localhost:8080/links?uri=http://www.exonar.com`

or open a browser and navigate to `http://localhost:8080/links?uri=http://www.exonar.com`

Change the text following '?uri=' to test another site.