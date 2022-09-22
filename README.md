# Expertise Test solution for Outfit7

### Requirements

* JDK 8+
* Maven 3.6+
* Install [Lombok plugin for IntelliJ IDEA](https://plugins.jetbrains.com/plugin/6317-lombok-plugin)

### Technologies used

* [JUnit5](https://junit.org/junit5/docs/current/user-guide/) as test runner
* [Spring boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) - for DI and just for fun, actually there is no need in it
* [Allure Framework](https://docs.qameta.io/allure) as test report tool
* [Lombok](https://projectlombok.org/) to get rid of boilerplate code

### Structure

* client package contains the lowest layer when we just make calls to some endpoints
* steps layer should be used for some more complex logical operations - don't need for now
* endpoints package keeps all the endpoints
* dto - models for deserialized payloads
* util is util
* config - here we can create, link together and configure our beans using java code
* TaskApplication class creates context
* @SpringBootTest - this annotation in test classes builds up context for tests

### How to run tests locally

* Just run command: ```mvn clean test```

### How to generate test report

* Use command in your terminal inside your project folder ```mvn allure:serve```

## Test scenarios

### Search Breweries

##### Search

1. Check search by keyword works: status code, response payload
2. Check search by keyword with pagination: specified amount is returned
3. Check all breweries have specified keyword in name
4. Check response when we look for breweries with whitespace in name
5. Check get specific brewery by name
6. Check pagination when per_page value is 0
7. Check pagination with incorrect values: -1, some string etc.
8. Check response when 'query' equals null value

##### Autocomplete

1. Check endpoint just works
2. Check only that only Id and Name are in response
3. Play with boundary values as The maximum number of breweries returned is 15.
4. Send some incorrect value for query parameter

### List Breweries

1. Check we can list of all breweries, endpoint just works
2. Check per_page parameter works
3. Check filters: by_city, by_dist, by_name, by_state, by_postal, by_type -> correct/incorrect values, filters are applied correctly
4. Check per_page filter: default value, max value, max value + 1, 0, -1, incorrect value etc.
5. Check page offset using per page filter and you will know which breweries should be on specific page
6. Try sort breweries option ASC DESC works correctly
7. Check by_dist does not work with the sort filter since it is a filter of its own.