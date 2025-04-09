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