# Crypto Recommendation Service

## Testing your app

### Running locally
There are multiple options
1. You can just use the SpringBoot run functionality that is default in IntelliJ IDEA

2. Start your application with Maven from the project directory
    ```bash
    mvn spring-boot:run
    ```

3. Package the jar file and run it

    ```bash
    mvn clean package
    java -jar target/crypto-recommendation-service-0.0.1-SNAPSHOT.jar
    ```

### Testing locally
Swagger UI: http://localhost:8080/swagger-ui/index.html  
API description: http://localhost:8080/v3/api-docs:  

### Improvement options
* Increase test coverage
* Additional data validations
* Change storage to something industry standard (e.g. Redis)
