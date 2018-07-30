### What's this?

The sole purpose of the project is for me to learn Kotlin, and Spring Webflux.

It serves cluclings at 'localhost:8080/clucklings'. Yuu may use standard Restful opreations
to manipulate the data, see RoutingConfig and ClucklingHandler.


### How to run?

1. Run mongo, e.g: docker run --name cluckling-repo -p 27017:27017 -d mongo:latest
2. Run app, e.g: ./gradlew bootRun