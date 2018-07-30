### What's this?

The sole purpose of this project is for me to learn Kotlin and Spring Webflux.

It serves clucklings at 'localhost:8080/clucklings'. You may use standard Restful operations
to manipulate the data. See RoutingConfig and ClucklingHandler for details.

### How to run?

1. Run mongo, e.g: docker run --name cluckling-repo -p 27017:27017 -d mongo:latest
2. Run app, e.g: ./gradlew bootRun

Cluck cluck.