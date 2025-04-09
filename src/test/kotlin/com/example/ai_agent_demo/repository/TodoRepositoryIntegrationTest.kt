package com.example.ai_agent_demo.repository

import com.example.ai_agent_demo.model.Todo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat

@DataJpaTest
class TodoRepositoryIntegrationTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var todoRepository: TodoRepository

    @Test
    fun `should save and find todo by id`() {
        // Arrange
        val todo = Todo(
            title = "Test Todo",
            dueDate = LocalDate.now().plusDays(1),
            completed = false
        )
        
        // Act
        val savedTodo = todoRepository.save(todo)
        entityManager.flush()
        entityManager.clear()
        
        val foundTodo = todoRepository.findById(savedTodo.id).orElse(null)
        
        // Assert
        assertThat(foundTodo).isNotNull
        assertThat(foundTodo.id).isEqualTo(savedTodo.id)
        assertThat(foundTodo.title).isEqualTo(todo.title)
        assertThat(foundTodo.dueDate).isEqualTo(todo.dueDate)
        assertThat(foundTodo.completed).isEqualTo(todo.completed)
    }

    @Test
    fun `should find all todos`() {
        // Arrange
        val todo1 = Todo(
            title = "First Todo",
            dueDate = LocalDate.now().plusDays(1),
            completed = false
        )
        val todo2 = Todo(
            title = "Second Todo",
            dueDate = LocalDate.now().plusDays(2),
            completed = true
        )
        
        entityManager.persist(todo1)
        entityManager.persist(todo2)
        entityManager.flush()
        
        // Act
        val todos = todoRepository.findAll()
        
        // Assert
        assertThat(todos).hasSize(2)
        assertThat(todos.map { it.title }).containsExactlyInAnyOrder("First Todo", "Second Todo")
    }

    @Test
    fun `should find todos by completed status`() {
        // Arrange
        val completedTodo = Todo(
            title = "Completed Todo",
            dueDate = LocalDate.now().plusDays(1),
            completed = true
        )
        val incompleteTodo = Todo(
            title = "Incomplete Todo",
            dueDate = LocalDate.now().plusDays(2),
            completed = false
        )
        
        entityManager.persist(completedTodo)
        entityManager.persist(incompleteTodo)
        entityManager.flush()
        
        // Act
        val completedTodos = todoRepository.findByCompleted(true)
        val incompleteTodos = todoRepository.findByCompleted(false)
        
        // Assert
        assertThat(completedTodos).hasSize(1)
        assertThat(completedTodos[0].title).isEqualTo("Completed Todo")
        assertThat(completedTodos[0].completed).isTrue()
        
        assertThat(incompleteTodos).hasSize(1)
        assertThat(incompleteTodos[0].title).isEqualTo("Incomplete Todo")
        assertThat(incompleteTodos[0].completed).isFalse()
    }

    @Test
    fun `should update todo`() {
        // Arrange
        val todo = Todo(
            title = "Original Title",
            dueDate = LocalDate.now().plusDays(1),
            completed = false
        )
        
        entityManager.persist(todo)
        entityManager.flush()
        
        // Act
        val updatedTodo = todo.copy(
            title = "Updated Title",
            completed = true
        )
        
        todoRepository.save(updatedTodo)
        entityManager.flush()
        entityManager.clear()
        
        val foundTodo = todoRepository.findById(todo.id).orElse(null)
        
        // Assert
        assertThat(foundTodo).isNotNull
        assertThat(foundTodo.title).isEqualTo("Updated Title")
        assertThat(foundTodo.completed).isTrue()
    }

    @Test
    fun `should delete todo`() {
        // Arrange
        val todo = Todo(
            title = "Todo to Delete",
            dueDate = LocalDate.now().plusDays(1),
            completed = false
        )
        
        entityManager.persist(todo)
        entityManager.flush()
        
        // Act
        todoRepository.deleteById(todo.id)
        entityManager.flush()
        
        val foundTodo = todoRepository.findById(todo.id).orElse(null)
        
        // Assert
        assertThat(foundTodo).isNull()
    }
}