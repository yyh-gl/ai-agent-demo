package com.example.ai_agent_demo.repository

import com.example.ai_agent_demo.model.Todo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var todoRepository: TodoRepository

    @Test
    fun `should find no todos if repository is empty`() {
        val todos = todoRepository.findAll()
        assertThat(todos).isEmpty()
    }

    @Test
    fun `should store a todo`() {
        val todo = Todo(
            title = "Test Todo",
            dueDate = LocalDate.now().plusDays(1),
            completed = false
        )
        
        val savedTodo = todoRepository.save(todo)
        
        assertThat(savedTodo.id).isNotEqualTo(0)
        assertThat(savedTodo.title).isEqualTo(todo.title)
        assertThat(savedTodo.dueDate).isEqualTo(todo.dueDate)
        assertThat(savedTodo.completed).isEqualTo(todo.completed)
    }

    @Test
    fun `should find all todos`() {
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
        
        val todos = todoRepository.findAll()
        
        assertThat(todos).hasSize(2)
        assertThat(todos).extracting("title").containsExactlyInAnyOrder("First Todo", "Second Todo")
    }

    @Test
    fun `should find todo by id`() {
        val todo = Todo(
            title = "Test Todo",
            dueDate = LocalDate.now().plusDays(1),
            completed = false
        )
        
        entityManager.persist(todo)
        entityManager.flush()
        
        val found = todoRepository.findById(todo.id).orElse(null)
        
        assertThat(found).isNotNull
        assertThat(found.title).isEqualTo(todo.title)
    }

    @Test
    fun `should find todos by completed status`() {
        val todo1 = Todo(
            title = "Incomplete Todo",
            dueDate = LocalDate.now().plusDays(1),
            completed = false
        )
        val todo2 = Todo(
            title = "Completed Todo",
            dueDate = LocalDate.now().plusDays(2),
            completed = true
        )
        
        entityManager.persist(todo1)
        entityManager.persist(todo2)
        entityManager.flush()
        
        val incompleteTodos = todoRepository.findByCompleted(false)
        val completedTodos = todoRepository.findByCompleted(true)
        
        assertThat(incompleteTodos).hasSize(1)
        assertThat(incompleteTodos[0].title).isEqualTo("Incomplete Todo")
        
        assertThat(completedTodos).hasSize(1)
        assertThat(completedTodos[0].title).isEqualTo("Completed Todo")
    }

    @Test
    fun `should update todo`() {
        val todo = Todo(
            title = "Original Title",
            dueDate = LocalDate.now().plusDays(1),
            completed = false
        )
        
        entityManager.persist(todo)
        entityManager.flush()
        
        val updatedTodo = todo.copy(
            title = "Updated Title",
            completed = true
        )
        
        todoRepository.save(updatedTodo)
        
        val found = todoRepository.findById(todo.id).orElse(null)
        
        assertThat(found).isNotNull
        assertThat(found.title).isEqualTo("Updated Title")
        assertThat(found.completed).isTrue()
    }

    @Test
    fun `should delete todo`() {
        val todo = Todo(
            title = "Test Todo",
            dueDate = LocalDate.now().plusDays(1),
            completed = false
        )
        
        entityManager.persist(todo)
        entityManager.flush()
        
        todoRepository.deleteById(todo.id)
        
        val found = todoRepository.findById(todo.id).orElse(null)
        
        assertThat(found).isNull()
    }
}