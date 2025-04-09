package com.example.ai_agent_demo.service

import com.example.ai_agent_demo.model.Todo
import com.example.ai_agent_demo.repository.TodoRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.Optional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows

@ExtendWith(MockitoExtension::class)
class TodoServiceTest {

    @Mock
    private lateinit var todoRepository: TodoRepository

    private lateinit var todoService: TodoService

    @BeforeEach
    fun setUp() {
        todoService = TodoService(todoRepository)
    }

    @Test
    fun `getAllTodos should return all todos from repository`() {
        // Arrange
        val todo1 = Todo(id = 1, title = "Test Todo 1", dueDate = LocalDate.now(), completed = false)
        val todo2 = Todo(id = 2, title = "Test Todo 2", dueDate = LocalDate.now().plusDays(1), completed = true)
        val expectedTodos = listOf(todo1, todo2)
        
        `when`(todoRepository.findAll()).thenReturn(expectedTodos)

        // Act
        val actualTodos = todoService.getAllTodos()

        // Assert
        assertThat(actualTodos).isEqualTo(expectedTodos)
        verify(todoRepository, times(1)).findAll()
    }

    @Test
    fun `getTodoById should return todo when id exists`() {
        // Arrange
        val todoId = 1L
        val expectedTodo = Todo(id = todoId, title = "Test Todo", dueDate = LocalDate.now(), completed = false)
        
        `when`(todoRepository.findById(todoId)).thenReturn(Optional.of(expectedTodo))

        // Act
        val actualTodo = todoService.getTodoById(todoId)

        // Assert
        assertThat(actualTodo).isEqualTo(expectedTodo)
        verify(todoRepository, times(1)).findById(todoId)
    }

    @Test
    fun `getTodoById should throw NoSuchElementException when id does not exist`() {
        // Arrange
        val todoId = 999L
        
        `when`(todoRepository.findById(todoId)).thenReturn(Optional.empty())

        // Act & Assert
        val exception = assertThrows(NoSuchElementException::class.java) {
            todoService.getTodoById(todoId)
        }
        
        assertThat(exception.message).isEqualTo("Todo not found with id: $todoId")
        verify(todoRepository, times(1)).findById(todoId)
    }

    @Test
    fun `getCompletedTodos should return completed todos from repository`() {
        // Arrange
        val todo1 = Todo(id = 1, title = "Completed Todo", dueDate = LocalDate.now(), completed = true)
        val todo2 = Todo(id = 2, title = "Another Completed Todo", dueDate = LocalDate.now().plusDays(1), completed = true)
        val expectedTodos = listOf(todo1, todo2)
        
        `when`(todoRepository.findByCompleted(true)).thenReturn(expectedTodos)

        // Act
        val actualTodos = todoService.getCompletedTodos()

        // Assert
        assertThat(actualTodos).isEqualTo(expectedTodos)
        verify(todoRepository, times(1)).findByCompleted(true)
    }

    @Test
    fun `getIncompleteTodos should return incomplete todos from repository`() {
        // Arrange
        val todo1 = Todo(id = 1, title = "Incomplete Todo", dueDate = LocalDate.now(), completed = false)
        val todo2 = Todo(id = 2, title = "Another Incomplete Todo", dueDate = LocalDate.now().plusDays(1), completed = false)
        val expectedTodos = listOf(todo1, todo2)
        
        `when`(todoRepository.findByCompleted(false)).thenReturn(expectedTodos)

        // Act
        val actualTodos = todoService.getIncompleteTodos()

        // Assert
        assertThat(actualTodos).isEqualTo(expectedTodos)
        verify(todoRepository, times(1)).findByCompleted(false)
    }

    @Test
    fun `createTodo should save and return new todo`() {
        // Arrange
        val title = "New Todo"
        val dueDate = LocalDate.now().plusDays(3)
        val newTodo = Todo(title = title, dueDate = dueDate)
        val savedTodo = Todo(id = 1, title = title, dueDate = dueDate)
        
        `when`(todoRepository.save(any(Todo::class.java))).thenReturn(savedTodo)

        // Act
        val result = todoService.createTodo(title, dueDate)

        // Assert
        assertThat(result).isEqualTo(savedTodo)
        verify(todoRepository, times(1)).save(any(Todo::class.java))
    }

    @Test
    fun `updateTodo should update and return existing todo`() {
        // Arrange
        val todoId = 1L
        val existingTodo = Todo(id = todoId, title = "Original Title", dueDate = LocalDate.now(), completed = false)
        val updatedTitle = "Updated Title"
        val updatedDueDate = LocalDate.now().plusDays(5)
        val updatedCompleted = true
        val updatedTodo = existingTodo.copy(title = updatedTitle, dueDate = updatedDueDate, completed = updatedCompleted)
        
        `when`(todoRepository.findById(todoId)).thenReturn(Optional.of(existingTodo))
        `when`(todoRepository.save(any(Todo::class.java))).thenReturn(updatedTodo)

        // Act
        val result = todoService.updateTodo(todoId, updatedTitle, updatedDueDate, updatedCompleted)

        // Assert
        assertThat(result).isEqualTo(updatedTodo)
        verify(todoRepository, times(1)).findById(todoId)
        verify(todoRepository, times(1)).save(any(Todo::class.java))
    }

    @Test
    fun `toggleTodoStatus should toggle completion status of todo`() {
        // Arrange
        val todoId = 1L
        val existingTodo = Todo(id = todoId, title = "Test Todo", dueDate = LocalDate.now(), completed = false)
        val toggledTodo = existingTodo.copy(completed = true)
        
        `when`(todoRepository.findById(todoId)).thenReturn(Optional.of(existingTodo))
        `when`(todoRepository.save(any(Todo::class.java))).thenReturn(toggledTodo)

        // Act
        val result = todoService.toggleTodoStatus(todoId)

        // Assert
        assertThat(result.completed).isEqualTo(!existingTodo.completed)
        verify(todoRepository, times(1)).findById(todoId)
        verify(todoRepository, times(1)).save(any(Todo::class.java))
    }

    @Test
    fun `deleteTodo should call repository deleteById`() {
        // Arrange
        val todoId = 1L
        doNothing().`when`(todoRepository).deleteById(todoId)

        // Act
        todoService.deleteTodo(todoId)

        // Assert
        verify(todoRepository, times(1)).deleteById(todoId)
    }
}