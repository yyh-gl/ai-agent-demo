package com.example.ai_agent_demo.controller

import com.example.ai_agent_demo.controller.form.TodoForm
import com.example.ai_agent_demo.service.TodoService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Controller
@RequestMapping("/todos")
class TodoController(private val todoService: TodoService) {

    @GetMapping
    fun listTodos(model: Model, @RequestParam(required = false) success: Boolean?): String {
        model.addAttribute("todos", todoService.getAllTodos())

        // Set initial values for the form - default due date to tomorrow
        val tomorrow = LocalDate.now().plusDays(1)
        model.addAttribute("todoForm", TodoForm(dueDate = tomorrow))

        // Add success message if form was submitted successfully
        if (success == true) {
            model.addAttribute("successMessage", "Todoが正常に保存されました")
        }

        return "index"
    }

    @GetMapping("/completed")
    fun listCompletedTodos(model: Model, @RequestParam(required = false) success: Boolean?): String {
        model.addAttribute("todos", todoService.getCompletedTodos())

        // Set initial values for the form - default due date to tomorrow
        val tomorrow = LocalDate.now().plusDays(1)
        model.addAttribute("todoForm", TodoForm(dueDate = tomorrow))

        // Add success message if form was submitted successfully
        if (success == true) {
            model.addAttribute("successMessage", "Todoが正常に保存されました")
        }

        return "index"
    }

    @GetMapping("/incomplete")
    fun listIncompleteTodos(model: Model, @RequestParam(required = false) success: Boolean?): String {
        model.addAttribute("todos", todoService.getIncompleteTodos())

        // Set initial values for the form - default due date to tomorrow
        val tomorrow = LocalDate.now().plusDays(1)
        model.addAttribute("todoForm", TodoForm(dueDate = tomorrow))

        // Add success message if form was submitted successfully
        if (success == true) {
            model.addAttribute("successMessage", "Todoが正常に保存されました")
        }

        return "index"
    }

    @PostMapping
    fun createTodo(@Valid @ModelAttribute todoForm: TodoForm, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("todos", todoService.getAllTodos())
            return "index"
        }
        todoService.createTodo(todoForm.title, todoForm.dueDate)
        // Redirect with a flash attribute to clear the form
        return "redirect:/todos?success=true"
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
    fun updateTodo(@PathVariable id: Long, @Valid @ModelAttribute todoForm: TodoForm, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("todos", todoService.getAllTodos())
            return "index"
        }
        todoService.updateTodo(
            id = id,
            title = todoForm.title,
            dueDate = todoForm.dueDate,
            completed = todoForm.completed
        )
        return "redirect:/todos?success=true"
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
