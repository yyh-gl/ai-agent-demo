package com.example.ai_agent_demo.controller

import com.example.ai_agent_demo.controller.form.TodoForm
import com.example.ai_agent_demo.service.TodoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(private val todoService: TodoService) {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("todos", todoService.getAllTodos())
        model.addAttribute("todoForm", TodoForm())
        return "index"
    }
}
