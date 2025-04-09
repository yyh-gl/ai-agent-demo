package com.example.ai_agent_demo.controller.form

import java.time.LocalDate

data class TodoForm(
    val id: Long? = null,
    val title: String = "",
    val dueDate: LocalDate = LocalDate.now(),
    val completed: Boolean = false
)