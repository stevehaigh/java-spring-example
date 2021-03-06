## Simple Rest Client that retrieves links from documents ##

Built using Spring Boot / Maven.

To build, run:

`mvn clean install`

To run:

`java -jar target/restserver-0.0.1-SNAPSHOT.jar`

To test, try:

`curl http://localhost:8080/links?uri=http://www.amazon.com`

or open a browser and navigate to `http://localhost:8080/links?uri=http://www.amazon.com`

Change the text following `?uri=` to test another site.

## Notes / Caveats / Excuses ##
Testing is basic.

Exception handling is non-existant. In the case of an error you will either get a raw 4xx response or a response with some text suggesting that the URL you tried was not valid.

If it works, you get back a plain JSON list of URIs found in the document, this list may be empty.
