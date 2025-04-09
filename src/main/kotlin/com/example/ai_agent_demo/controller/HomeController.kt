package com.example.ai_agent_demo.controller

import com.example.ai_agent_demo.controller.form.TodoForm
import com.example.ai_agent_demo.service.TodoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDate

@Controller
class HomeController(private val todoService: TodoService) {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("todos", todoService.getAllTodos())

        // Set initial values for the form - default due date to tomorrow
        val tomorrow = LocalDate.now().plusDays(1)
        model.addAttribute("todoForm", TodoForm(dueDate = tomorrow))

        return "index"
    }
}
