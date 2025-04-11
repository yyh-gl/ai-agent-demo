↓ Request to GitHub Copilot

```
This is a web application development project.
We want to create a TODO application.

The features are as follows:
- Display TODO list
- Add TODO
    - Ability to input task content and deadline
- Change TODO to completed status
    - Mark as completed by turning on the checkbox
    - Add strikethrough to completed TODOs

We want to use Tailwind CSS.
https://tailwindcss.com/

The implementation will be done using Agent-type AI tools.
You should divide the implementation tasks in a way that is easy for the AI Agent to understand.

The output should be prompts for each task.
Since these are prompts for AI Agent requests, please provide specific work instructions.

At the end of each task, please instruct to update .junie/guidelines.md according to the changes.
```


## Task 1: Project Initial Setup

```
Please create the basic structure of a TODO app using Spring Boot and Kotlin.

1. Add the necessary dependencies to `build.gradle.kts`:
   - Spring Web
   - Spring Data JPA
   - H2 Database
   - Thymeleaf
   - Tailwind CSS setup (PostCSS and other necessary tools)

2. Create the following project structure:
   - Model: `Todo` entity (with id, title, dueDate, completed attributes)
   - Repository: `TodoRepository` interface
   - Controller: `TodoController`
   - Service: `TodoService`
   - View: Thymeleaf template for the main page

3. Configure application.properties:
   - H2 database settings
   - Other necessary configurations

Please show all code examples and file placements.

After implementation, please review the entire project and update the information in `.junie/gudelines.md`.
```


## Task 2: Todo Model and Repository Implementation

```
Please implement the data model and repository layer for the Todo application.

1. Create the `Todo` entity class:
   - id: Long (primary key)
   - title: String (TODO content)
   - dueDate: LocalDate (deadline date)
   - completed: Boolean (completion flag)
   
2. Create the `TodoRepository` interface extending JpaRepository:
   - Basic CRUD operations
   - Custom query methods for retrieving completed/incomplete TODOs
   
Please implement with code examples.

After implementation, please review the entire project and update the information in `.junie/gudelines.md`.
```

## Task 3: Service Layer Implementation

```
Please implement the service layer to handle the business logic of the TODO application.

1. Create the `TodoService` class and implement the following methods:
   - Method to retrieve all TODOs
   - Method to create a new TODO
   - Method to toggle the completion status of a TODO
   - Method to delete a TODO (optional)
   
2. Please implement proper integration with `TodoRepository`.

Please provide complete code examples.

After implementation, please review the entire project and update the information in `.junie/gudelines.md`.
```

## Task 4: Controller Implementation

```
Please implement the controller layer for the Todo application.

1. Create the `TodoController` class and implement the following endpoints:
   - `GET /`: Display the main page and list all TODOs
   - `POST /todos`: Create a new TODO
   - `POST /todos/{id}/toggle`: Toggle the completion status of a TODO
   - `DELETE /todos/{id}`: Delete a TODO (optional)
   
2. Ensure proper integration with Thymeleaf templates.
3. Define form objects for POST requests.

Please provide complete code examples.

After implementation, please review the entire project and update the information in `.junie/gudelines.md`.
```

## Task 5: Thymeleaf Templates and Frontend Implementation

```
Please implement the frontend of the Todo application using Thymeleaf and Tailwind CSS.

1. Create a main page in `src/main/resources/templates/index.html` with the following elements:
   - A section to display the TODO list
     - Each TODO should show title, deadline, and completion checkbox
     - Completed TODOs should have a strikethrough on the title
   - A form to add new TODOs
     - Title input field
     - Date picker for deadline selection
     - Submit button
   
2. Style the UI using Tailwind CSS:
   - Responsive design
   - Modern and clean layout
   - Appropriate spacing, colors, and fonts
   
3. Implement JavaScript functionality to automatically submit the form when checkbox state changes

Please provide complete HTML code and necessary CSS and JavaScript.

After implementation, please review the entire project and update the information in `.junie/gudelines.md`.
```

## Task 6: Tailwind CSS Configuration

```
Please integrate Tailwind CSS into the Spring Boot project.

1. npm configuration to install necessary dependencies:
   - Creating package.json
   - Installation method for tailwindcss, postcss, autoprefixer
   
2. Tailwind configuration files:
   - Creating tailwind.config.js
   - Creating postcss.config.js
   
3. Integration method for Spring Boot and Tailwind:
   - Build process configuration
   - Static resource processing method
   
4. Configuration to enhance development convenience:
   - Hot reload configuration
   - Build script creation

Please explain all configuration files and procedures in detail.

After implementation, please review the entire project and update the information in `.junie/gudelines.md`.
```

## Task 7: Application Testing

```
Please implement tests for the created TODO application.

1. Create unit tests:
   - Tests for `TodoService`
   - Tests for `TodoController`
   
2. Integration tests:
   - Tests to verify endpoints work correctly
   - Tests to verify database operations are performed correctly
   
3. Test cases to verify all CRUD operations function correctly:
   - TODO creation
   - TODO list display
   - Toggling TODO completion status
   - TODO deletion (if implemented)

Please provide complete test code using JUnit 5 and Mockito.

After implementation, please review the entire project and update the information in `.junie/gudelines.md`.
```

## Task 8: Error Handling and Validation

```
Please add error handling and validation features to the TODO application.

1. Form input validation:
   - Title is required and has a maximum length limit
   - Deadline must be a date on or after the current date
   
2. Display validation error messages:
   - User-friendly error messages
   - Display next to form fields
   
3. Global error handling:
   - Implement an Advice class for exception handling
   - Return appropriate HTTP status codes
   
4. Improve user experience:
   - Set initial values for forms
   - Clear form after submission

Please provide complete code examples and implementation methods.

After implementation, please review the entire project and update the information in `.junie/gudelines.md`.
```

## Task 9: Application Verification and Debugging

```
Please perform verification and debugging of the implemented TODO application.

1. Application startup:
   - Check startup logs and confirm there are no errors
   - Verify the service is running on the correct port
   
2. Functional testing:
   - Access the app from a browser and check the UI display
   - Create a new TODO and verify it is saved
   - Toggle the completion status of a TODO and verify the appearance and state change
   
3. Methods for fixing issues if found:
   - How to check logs
   - How to display debug information
   - Solutions for common problems

Please provide steps to verify that the application is working correctly.

After implementation, please review the entire project and update the information in `.junie/gudelines.md`.
```

## Task 10: Application Improvements (Optional)

```
Please implement additional features for the TODO application.

1. Improve user experience:
   - TODO sorting functionality (by deadline, creation date, alphabetical order, etc.)
   - TODO filtering (completed/incomplete, deadline, etc.)
   - Pagination (for when there are many TODOs)
   
2. Additional features:
   - TODO priority setting
   - TODO categorization/tagging
   - TODO editing functionality
   - TODO search functionality
   
3. UI improvements:
   - Dark mode/light mode toggle
   - Add animation effects
   - Enhance responsive design

Please provide detailed implementation methods and code for the selected features.

After implementation, please review the entire project and update the information in `.junie/gudelines.md`.
```
