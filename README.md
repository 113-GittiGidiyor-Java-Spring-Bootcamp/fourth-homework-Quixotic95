# Homework 4 - School Management System

## Schema

![homework](https://user-images.githubusercontent.com/45206582/131386439-6727321a-5a50-4c20-9413-ea4013013434.PNG)

* Student
* Course
* Instructor
    * Visiting Researcher
    * Permanent Instructor

## Summary

A system with Students, Courses and 2 types of Instructors all related to each other.

## Details

1. User can make any CRUD operation on ->

* `Student`
* `Course`
* `Instructor` entities  via DTO which will be added to the relational database.


2. DTO and Entities could be convertible to each other via Mapstruct API.

3. Due to MVC design pattern, project will have ->

* `entity`
* `repository`
* `service`
* `controller` layers. 
* +++
* `Config` for Swagger2 implementation
* `dto` for request/response specialization
* `exception` for custom case exceptions
* `mapper` for mapstruct API usage

4. For some special cases, specialized Exceptions will be thrown ->

* **Student** `-age`: must between 18-40
* **Instructor** `-phoneNumber`: must be unique.
* **Course** `-courseCode`: must be unique.
* **Course** `-couseStudents`: Can only take max 20 Students.

5. For all exceptions, a *Global Exception Handler* will be created and errors will be thrown with status code and
   message.
6. Thrown exceptions ->

* will be logged with date, message and error information at the database.
* can be retrieved from database via error type or/and date

7. Entities will have last modified date and created date on database.

## Used Technologies

* IntelliJ IDEA `2021.2`
    * Spring Initializr
        * Java `8`
        * Maven Project
        * Spring Boot `2.5.4`
        * Dependencies
        * Plugins

## Usage
1. Run the program.
2. Go to [SwaggerUI](http://localhost:8080/swagger-ui.html) from your browser.
3. Use the controllers for CRUD operations.
4. For exception-logger-controller, date must be formatted as **'YYYY-MM-DD'** and type should be like **'404 NOT_FOUND'** or **'404'** or **'NOT_FOUND'**


## License

[MIT](https://choosealicense.com/licenses/mit/)