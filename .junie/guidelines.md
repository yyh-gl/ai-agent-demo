# Kotlin Style Guide for AI Agent Demo Project

This style guide outlines the coding conventions and best practices for Kotlin development in this Spring Boot project.

## Idiomatic Kotlin Usage

### Null Safety
- Prefer non-nullable types whenever possible
- Use the safe call operator (`?.`) and the Elvis operator (`?:`) for null handling
- Avoid using `!!` operator unless absolutely necessary and you're certain the value is not null

### Extension Functions
- Use extension functions to extend functionality of existing classes
- Create utility extension functions in a dedicated `Extensions.kt` file

### Collections
- Use the Kotlin standard library functions for collections (map, filter, etc.)
- Prefer immutable collections (`listOf`, `setOf`, `mapOf`) over mutable ones
- Use sequence operations for large collections to improve performance

### Functional Programming
- Prefer using higher-order functions over imperative loops
- Use lambda expressions for short, simple operations
- Consider using function references (`::`) for cleaner code

## Architectural Patterns

### Spring Boot Patterns
- Follow the standard Spring Boot architecture: Controller -> Service -> Repository
- Use constructor injection for dependencies
- Annotate classes appropriately:
  - `@RestController` for REST controllers
  - `@Service` for service classes
  - `@Repository` for data access classes
  - `@Component` for other Spring-managed components

### Data Classes
- Use data classes for DTOs, request/response objects, and domain models
- Keep data classes immutable when possible
- Use named parameters for better readability

### Error Handling
- Create a global exception handler using `@ControllerAdvice`
- Define custom exceptions for different error scenarios
- Return appropriate HTTP status codes and error messages
- Implement validation for form inputs
- Display user-friendly error messages next to form fields

## Common Pitfalls to Avoid

### Java Interoperability
- Be careful with platform types (types coming from Java code)
- Use `@JvmField` and `@JvmStatic` annotations when needed for Java interoperability
- Remember that Kotlin's `Any` corresponds to Java's `Object`

### Coroutines
- Avoid blocking operations in coroutine context
- Use appropriate dispatchers for different types of operations
- Handle exceptions properly in coroutines using try-catch or supervisorScope

### Spring Boot Specific
- Avoid circular dependencies
- Be careful with lazy initialization
- Remember that Spring Boot's property binding is case-insensitive

## Stylistic Preferences

### Naming Conventions
- Use camelCase for variables, functions, and properties
- Use PascalCase for classes and interfaces
- Use UPPER_SNAKE_CASE for constants
- Prefix interface names with 'I' only if it's necessary to distinguish from implementations

### Code Organization
- Group related functions and properties together
- Order class members logically: constants, properties, init blocks, constructors, methods
- Keep files focused on a single responsibility

### Formatting
- Use 4 spaces for indentation
- Maximum line length: 120 characters
- Add a blank line between function and property declarations
- Use trailing commas in multi-line collection literals

### Documentation
- Document public APIs with KDoc comments
- Include examples in documentation when helpful
- Document non-obvious behavior and edge cases

### Testing
- Write unit tests for all business logic
- Use descriptive test method names that explain the scenario and expected outcome
- Prefer using JUnit 5 and MockK for testing
- Structure tests using the Arrange-Act-Assert pattern
- Test coverage includes:
  - Unit tests for services and controllers
  - Integration tests for endpoints and database operations
  - Tests for all CRUD operations (Create, Read, Update, Delete)
  - Edge cases and error handling

#### Test Structure
- **Unit Tests**:
  - `TodoServiceTest`: Tests for the service layer with mocked repository
  - `TodoControllerTest`: Tests for the controller layer with mocked service
- **Integration Tests**:
  - `TodoControllerIntegrationTest`: End-to-end tests for controller endpoints
  - `TodoRepositoryIntegrationTest`: Tests for repository operations with test database

## TODO Application Implementation

This project implements a simple TODO application using Spring Boot and Kotlin. Below is an overview of the implementation:

### Project Structure

- **Model**: `Todo` entity with id, title, dueDate, and completed attributes
- **Repository**: `TodoRepository` interface extending JpaRepository
- **Service**: `TodoService` providing business logic for Todo operations
- **Controller**: `TodoController` handling HTTP requests and `HomeController` for redirecting
- **View**: Thymeleaf template for the main page
- **CSS**: Tailwind CSS via CDN for styling

### Key Components

#### Todo Entity
- Represents a todo item with id, title, due date, and completion status
- Implemented as a Kotlin data class with JPA annotations

#### TodoRepository
- Extends Spring Data JPA's JpaRepository
- Provides CRUD operations for Todo entities
- Includes a custom method for finding todos by completion status

#### TodoService
- Provides business logic for todo operations
- Includes methods for:
  - Getting all TODOs (`getAllTodos()`)
  - Getting a TODO by ID (`getTodoById(id: Long)`)
  - Getting completed TODOs (`getCompletedTodos()`)
  - Getting incomplete TODOs (`getIncompleteTodos()`)
  - Creating a new TODO (`createTodo(title: String, dueDate: LocalDate)`)
  - Updating a TODO (`updateTodo(id: Long, title: String, dueDate: LocalDate, completed: Boolean)`)
  - Toggling a TODO's completion status (`toggleTodoStatus(id: Long)`)
  - Deleting a TODO (`deleteTodo(id: Long)`)
- Uses the repository for data access
- Follows Spring Boot best practices with proper dependency injection

#### TodoController
- Handles HTTP requests for todo operations
- Maps to the "/todos" path
- Uses the service for business logic
- Returns the "index" Thymeleaf template for rendering
- Provides endpoints for listing, creating, updating, toggling, and deleting TODOs

#### HomeController
- Handles the root URL ("/") and displays the main page with all TODOs
- Uses the TodoService to fetch all TODOs
- Passes the TODOs and a new TodoForm to the "index" Thymeleaf template

#### Thymeleaf Template
- Main template is `index.html` which displays:
  - A list of todos with title, due date, and completion status
  - Completed TODOs have a strikethrough on the title
  - A form for creating and editing todos with title input and date picker
- Includes JavaScript for auto-submitting when checkbox state changes
- Includes actions for toggling status, editing, and deleting todos

### Database Configuration
- Uses H2 in-memory database
- Configured in application.properties
- H2 console available at /h2-console

### UI Styling
- Uses Tailwind CSS integrated into the build process
- PostCSS with Tailwind CSS and Autoprefixer for CSS processing
- Responsive design for various screen sizes
- Clean and modern user interface
- Hot-reloading CSS during development with Gradle tasks

## Application Testing and Debugging Guide

### Starting the Application
1. Run the application using Gradle:
   ```
   ./gradlew bootRun
   ```
2. Verify the application starts without errors
3. Check the logs to confirm the application is running on port 8081:
   ```
   o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8081 (http)
   ```
4. Verify the H2 database is initialized:
   ```
   o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:tododb'
   ```

### Accessing the Application
1. Open a web browser and navigate to `http://localhost:8081`
2. You should see the Todo App interface with:
   - A form to add new TODOs
   - A table displaying existing TODOs (empty on first run)
   - Filter buttons for All, Completed, and Incomplete TODOs

### Testing Functionality
1. **Creating a new TODO**:
   - Enter a title in the "Title" field
   - Select a due date using the date picker
   - Click the "Save" button
   - Verify the new TODO appears in the table
   - Check for the success message "Todoが正常に保存されました"

2. **Toggling TODO completion status**:
   - Find a TODO in the table
   - Click the checkbox in the "Status" column
   - Verify the TODO's appearance changes:
     - Completed TODOs have a strikethrough on the title
     - The status badge changes from "Pending" to "Completed"
     - The row background changes to a light green color

3. **Editing a TODO**:
   - Click the "Edit" link for a TODO
   - Modify the title and/or due date
   - Click the "Save" button
   - Verify the changes are reflected in the table

4. **Deleting a TODO**:
   - Click the "Delete" link for a TODO
   - Confirm the deletion in the popup dialog
   - Verify the TODO is removed from the table

5. **Filtering TODOs**:
   - Click the "Completed" button to view only completed TODOs
   - Click the "Incomplete" button to view only incomplete TODOs
   - Click the "All" button to view all TODOs

### Debugging
1. **Checking logs**:
   - Application logs are output to the console when running with `./gradlew bootRun`
   - For more detailed logs, run with the `--debug` flag:
     ```
     ./gradlew bootRun --debug
     ```

2. **Accessing the H2 Console**:
   - Navigate to `http://localhost:8081/h2-console`
   - Use the following connection details:
     - JDBC URL: `jdbc:h2:mem:tododb`
     - Username: `sa`
     - Password: `password`
   - You can execute SQL queries to inspect the database

3. **Common Issues and Solutions**:
   - **Port already in use**: Change the port in `application.properties`
   - **Database errors**: Check the H2 console for database state
   - **Form validation errors**: Check the error messages displayed next to form fields
   - **UI not updating**: Ensure Thymeleaf caching is disabled in development
