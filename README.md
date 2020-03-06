![](https://github.com/Thiamath/rtghsjfp/workflows/Java%2011%20with%20Gradle/badge.svg)

# Last Minute assignement in Java

## Test
To run the tests just clone the repo and run:
```shell script
gradlew.ba test
```
## Run
To run the service just clone the repo and run:
```shell script
gradlew.bat bootrun
```
This will open the port `8080` and enable the endpoint: `http://localhost:8080/process`.
That endpoint accept `POST` requests with plain text.
Use `Postman` or `cUrl` to put the test strings and check the output.
