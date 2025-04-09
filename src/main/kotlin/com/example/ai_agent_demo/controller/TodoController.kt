package com.example.ai_agent_demo.controller

import com.example.ai_agent_demo.controller.form.TodoForm
import com.example.ai_agent_demo.service.TodoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Controller
@RequestMapping("/todos")
class TodoController(private val todoService: TodoService) {

    @GetMapping
    fun listTodos(model: Model): String {
        model.addAttribute("todos", todoService.getAllTodos())
        model.addAttribute("todoForm", TodoForm())
        return "index"
    }

    @GetMapping("/completed")
    fun listCompletedTodos(model: Model): String {
        model.addAttribute("todos", todoService.getCompletedTodos())
        model.addAttribute("todoForm", TodoForm())
        return "index"
    }

    @GetMapping("/incomplete")
    fun listIncompleteTodos(model: Model): String {
        model.addAttribute("todos", todoService.getIncompleteTodos())
        model.addAttribute("todoForm", TodoForm())
        return "index"
    }

    @PostMapping
    fun createTodo(@ModelAttribute todoForm: TodoForm): String {
        todoService.createTodo(todoForm.title, todoForm.dueDate)
        return "redirect:/todos"
    }

    @GetMapping("/{id}/edit")
    fun editTodoForm(@PathVariable id: Long, model: Model): String {
        val todo = todoService.getTodoById(id)
        model.addAttribute("todoForm", TodoForm(
            id = todo.id,
            title = todo.title,
            dueDate = todo.dueDate,
            completed = todo.completed
        ))
        model.addAttribute("todos", todoService.getAllTodos())
        return "index"
    }

    @PostMapping("/{id}")
    fun updateTodo(@PathVariable id: Long, @ModelAttribute todoForm: TodoForm): String {
        todoService.updateTodo(
            id = id,
            title = todoForm.title,
            dueDate = todoForm.dueDate,
            completed = todoForm.completed
        )
        return "redirect:/todos"
    }

    @PostMapping("/{id}/toggle")
    fun toggleTodoStatus(@PathVariable id: Long): String {
        todoService.toggleTodoStatus(id)
        return "redirect:/todos"
    }

    @PostMapping("/{id}/delete")
    fun deleteTodo(@PathVariable id: Long): String {
        todoService.deleteTodo(id)
        return "redirect:/todos"
    }

    @GetMapping("/")
    fun redirectToTodos(): String {
        return "redirect:/todos"
    }
}
