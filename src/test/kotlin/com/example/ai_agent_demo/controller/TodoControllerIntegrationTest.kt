package com.example.ai_agent_demo.controller

import com.example.ai_agent_demo.model.Todo
import com.example.ai_agent_demo.repository.TodoRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var todoRepository: TodoRepository

    @BeforeEach
    fun setUp() {
        todoRepository.deleteAll()
    }

    @Test
    fun `should display empty todo list when no todos exist`() {
        mockMvc.perform(get("/todos"))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("todos"))
            .andExpect(model().attributeExists("todoForm"))

        val todos = todoRepository.findAll()
        assertThat(todos).isEmpty()
    }

    @Test
    fun `should create a new todo and display it in the list`() {
        // Create a new todo
        val title = "Integration Test Todo"
        val dueDate = LocalDate.now().plusDays(1)

        mockMvc.perform(post("/todos")
                .param("title", title)
                .param("dueDate", dueDate.toString()))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/todos"))

        // Verify the todo was created
        val todos = todoRepository.findAll()
        assertThat(todos).hasSize(1)
        assertThat(todos[0].title).isEqualTo(title)
        assertThat(todos[0].dueDate).isEqualTo(dueDate)
        assertThat(todos[0].completed).isFalse()
    }

    @Test
    fun `should update an existing todo`() {
        // Create a todo first
        val todo = todoRepository.save(Todo(
            title = "Original Todo",
            dueDate = LocalDate.now(),
            completed = false
        ))

        // Update the todo
        val updatedTitle = "Updated Todo"
        val updatedDueDate = LocalDate.now().plusDays(2)

        mockMvc.perform(post("/todos/{id}", todo.id)
                .param("title", updatedTitle)
                .param("dueDate", updatedDueDate.toString())
                .param("completed", "true"))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/todos"))

        // Verify the todo was updated
        val updatedTodo = todoRepository.findById(todo.id).orElseThrow()
        assertThat(updatedTodo.title).isEqualTo(updatedTitle)
        assertThat(updatedTodo.dueDate).isEqualTo(updatedDueDate)
        assertThat(updatedTodo.completed).isTrue()
    }

    @Test
    fun `should toggle todo completion status`() {
        // Create a todo first (not completed)
        val todo = todoRepository.save(Todo(
            title = "Toggle Test Todo",
            dueDate = LocalDate.now(),
            completed = false
        ))

        // Toggle the todo status
        mockMvc.perform(post("/todos/{id}/toggle", todo.id))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/todos"))

        // Verify the todo status was toggled to completed
        var toggledTodo = todoRepository.findById(todo.id).orElseThrow()
        assertThat(toggledTodo.completed).isTrue()

        // Toggle again
        mockMvc.perform(post("/todos/{id}/toggle", todo.id))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/todos"))

        // Verify the todo status was toggled back to not completed
        toggledTodo = todoRepository.findById(todo.id).orElseThrow()
        assertThat(toggledTodo.completed).isFalse()
    }

    @Test
    fun `should delete a todo`() {
        // Create a todo first
        val todo = todoRepository.save(Todo(
            title = "Todo to Delete",
            dueDate = LocalDate.now(),
            completed = false
        ))

        // Verify the todo exists
        assertThat(todoRepository.findById(todo.id).isPresent).isTrue()

        // Delete the todo
        mockMvc.perform(post("/todos/{id}/delete", todo.id))
            .andExpect(status().is3xxRedirection)
            .andExpect(redirectedUrl("/todos"))

        // Verify the todo was deleted
        assertThat(todoRepository.findById(todo.id).isPresent).isFalse()
    }

    @Test
    fun `should display only completed todos`() {
        // Create completed and incomplete todos
        todoRepository.save(Todo(
            title = "Completed Todo",
            dueDate = LocalDate.now(),
            completed = true
        ))
        todoRepository.save(Todo(
            title = "Incomplete Todo",
            dueDate = LocalDate.now(),
            completed = false
        ))

        // Verify only completed todos are shown
        mockMvc.perform(get("/todos/completed"))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("todos"))
            .andExpect(model().attributeExists("todoForm"))

        val completedTodos = todoRepository.findByCompleted(true)
        assertThat(completedTodos).hasSize(1)
        assertThat(completedTodos[0].title).isEqualTo("Completed Todo")
        assertThat(completedTodos[0].completed).isTrue()
    }

    @Test
    fun `should display only incomplete todos`() {
        // Create completed and incomplete todos
        todoRepository.save(Todo(
            title = "Completed Todo",
            dueDate = LocalDate.now(),
            completed = true
        ))
        todoRepository.save(Todo(
            title = "Incomplete Todo",
            dueDate = LocalDate.now(),
            completed = false
        ))

        // Verify only incomplete todos are shown
        mockMvc.perform(get("/todos/incomplete"))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("todos"))
            .andExpect(model().attributeExists("todoForm"))

        val incompleteTodos = todoRepository.findByCompleted(false)
        assertThat(incompleteTodos).hasSize(1)
        assertThat(incompleteTodos[0].title).isEqualTo("Incomplete Todo")
        assertThat(incompleteTodos[0].completed).isFalse()
    }
}
