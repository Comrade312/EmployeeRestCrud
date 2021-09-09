# Employee REST CRUD Service
Has been done:
* Implemented CRUD operations for Employee and Department
* Added caching for getting all Employee (via EhCache)
* Added basic authorization (users added by inMemoryRepository)
* Done database migration (via Flyway)
* Added [Dockerfile](Dockerfile) to build container for application
* Added [docker-compose](docker-compose.yml) to run applications with postgres database
* Added unit-tests and integration tests
* Added Postman collection with requests [PostmanRequests](EmployeeRestRequests.postman_collection.json)

Link to docker app image - https://hub.docker.com/repository/docker/comrade312/demo 
### Used technologies

* Spring Boot
* Spring Security
* Spring Data
* EhCache
* Hibernate
* Flyway
* Postgres
* Docker
* Lombok
* Maven



