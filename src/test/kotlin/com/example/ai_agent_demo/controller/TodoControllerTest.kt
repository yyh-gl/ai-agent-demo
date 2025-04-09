package com.example.ai_agent_demo.controller

import com.example.ai_agent_demo.controller.form.TodoForm
import com.example.ai_agent_demo.model.Todo
import com.example.ai_agent_demo.service.TodoService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class TodoControllerTest {

    @Mock
    private lateinit var todoService: TodoService

    private lateinit var todoController: TodoController
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        todoController = TodoController(todoService)
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build()
    }

    @Test
    fun `listTodos should return all todos`() {
        // Arrange
        val todos = listOf(
            Todo(id = 1, title = "Test Todo 1", dueDate = LocalDate.now(), completed = false),
            Todo(id = 2, title = "Test Todo 2", dueDate = LocalDate.now().plusDays(1), completed = true)
        )
        `when`(todoService.getAllTodos()).thenReturn(todos)

        // Act & Assert
        mockMvc.perform(get("/todos"))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("todos"))
            .andExpect(model().attributeExists("todoForm"))
            .andExpect(model().attribute("todos", todos))

        verify(todoService, times(1)).getAllTodos()
    }

    @Test
    fun `listCompletedTodos should return completed todos`() {
        // Arrange
        val completedTodos = listOf(
            Todo(id = 1, title = "Completed Todo 1", dueDate = LocalDate.now(), completed = true),
            Todo(id = 2, title = "Completed Todo 2", dueDate = LocalDate.now().plusDays(1), completed = true)
        )
        `when`(todoService.getCompletedTodos()).thenReturn(completedTodos)

        // Act & Assert
        mockMvc.perform(get("/todos/completed"))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("todos"))
            .andExpect(model().attributeExists("todoForm"))
            .andExpect(model().attribute("todos", completedTodos))

        verify(todoService, times(1)).getCompletedTodos()
    }

    @Test
    fun `listIncompleteTodos should return incomplete todos`() {
        // Arrange
        val incompleteTodos = listOf(
            Todo(id = 1, title = "Incomplete Todo 1", dueDate = LocalDate.now(), completed = false),
            Todo(id = 2, title = "Incomplete Todo 2", dueDate = LocalDate.now().plusDays(1), completed = false)
        )
        `when`(todoService.getIncompleteTodos()).thenReturn(incompleteTodos)

        // Act & Assert
        mockMvc.perform(get("/todos/incomplete"))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("todos"))
            .andExpect(model().attributeExists("todoForm"))
            .andExpect(model().attribute("todos", incompleteTodos))

        verify(todoService, times(1)).getIncompleteTodos()
    }

    @Test
    fun `createTodo should create a new todo and redirect`() {
        // Arrange
        val todoForm = TodoForm(title = "New Todo", dueDate = LocalDate.now().plusDays(2))
        val createdTodo = Todo(id = 1, title = todoForm.title, dueDate = todoForm.dueDate)
        
        `when`(todoService.createTodo(todoForm.title, todoForm.dueDate)).thenReturn(createdTodo)

        // Act & Assert
        mockMvc.perform(post("/todos")
                .param("title", todoForm.title)
                .param("dueDate", todoForm.dueDate.toString()))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/todos"))

        verify(todoService, times(1)).createTodo(todoForm.title, todoForm.dueDate)
    }

    @Test
    fun `editTodoForm should return form with todo data`() {
        // Arrange
        val todoId = 1L
        val todo = Todo(id = todoId, title = "Test Todo", dueDate = LocalDate.now(), completed = false)
        val allTodos = listOf(todo)
        
        `when`(todoService.getTodoById(todoId)).thenReturn(todo)
        `when`(todoService.getAllTodos()).thenReturn(allTodos)

        // Act & Assert
        mockMvc.perform(get("/todos/{id}/edit", todoId))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("todoForm"))
            .andExpect(model().attributeExists("todos"))
            .andExpect(model().attribute("todos", allTodos))

        verify(todoService, times(1)).getTodoById(todoId)
        verify(todoService, times(1)).getAllTodos()
    }

    @Test
    fun `updateTodo should update todo and redirect`() {
        // Arrange
        val todoId = 1L
        val todoForm = TodoForm(
            id = todoId,
            title = "Updated Todo",
            dueDate = LocalDate.now().plusDays(3),
            completed = true
        )
        val updatedTodo = Todo(
            id = todoId,
            title = todoForm.title,
            dueDate = todoForm.dueDate,
            completed = todoForm.completed
        )
        
        `when`(todoService.updateTodo(todoId, todoForm.title, todoForm.dueDate, todoForm.completed))
            .thenReturn(updatedTodo)

        // Act & Assert
        mockMvc.perform(post("/todos/{id}", todoId)
                .param("title", todoForm.title)
                .param("dueDate", todoForm.dueDate.toString())
                .param("completed", todoForm.completed.toString()))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/todos"))

        verify(todoService, times(1)).updateTodo(todoId, todoForm.title, todoForm.dueDate, todoForm.completed)
    }

    @Test
    fun `toggleTodoStatus should toggle todo status and redirect`() {
        // Arrange
        val todoId = 1L
        val todo = Todo(id = todoId, title = "Test Todo", dueDate = LocalDate.now(), completed = false)
        val toggledTodo = todo.copy(completed = true)
        
        `when`(todoService.toggleTodoStatus(todoId)).thenReturn(toggledTodo)

        // Act & Assert
        mockMvc.perform(post("/todos/{id}/toggle", todoId))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/todos"))

        verify(todoService, times(1)).toggleTodoStatus(todoId)
    }

    @Test
    fun `deleteTodo should delete todo and redirect`() {
        // Arrange
        val todoId = 1L
        doNothing().`when`(todoService).deleteTodo(todoId)

        // Act & Assert
        mockMvc.perform(post("/todos/{id}/delete", todoId))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/todos"))

        verify(todoService, times(1)).deleteTodo(todoId)
    }

    @Test
    fun `redirectToTodos should redirect to todos list`() {
        // Act & Assert
        mockMvc.perform(get("/todos/"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/todos"))
    }
}