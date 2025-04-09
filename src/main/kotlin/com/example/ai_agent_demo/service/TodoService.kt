package com.example.ai_agent_demo.service

import com.example.ai_agent_demo.model.Todo
import com.example.ai_agent_demo.repository.TodoRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TodoService(private val todoRepository: TodoRepository) {

    fun getAllTodos(): List<Todo> = todoRepository.findAll()

    fun getTodoById(id: Long): Todo = todoRepository.findById(id)
        .orElseThrow { NoSuchElementException("Todo not found with id: $id") }

    fun getCompletedTodos(): List<Todo> = todoRepository.findByCompleted(true)

    fun getIncompleteTodos(): List<Todo> = todoRepository.findByCompleted(false)

    fun createTodo(title: String, dueDate: LocalDate): Todo {
        val todo = Todo(title = title, dueDate = dueDate)
        return todoRepository.save(todo)
    }

    fun updateTodo(id: Long, title: String, dueDate: LocalDate, completed: Boolean): Todo {
        val existingTodo = getTodoById(id)
        val updatedTodo = existingTodo.copy(
            title = title,
            dueDate = dueDate,
            completed = completed
        )
        return todoRepository.save(updatedTodo)
    }

    fun toggleTodoStatus(id: Long): Todo {
        val todo = getTodoById(id)
        val updatedTodo = todo.copy(completed = !todo.completed)
        return todoRepository.save(updatedTodo)
    }

    fun deleteTodo(id: Long) {
        todoRepository.deleteById(id)
    }
}