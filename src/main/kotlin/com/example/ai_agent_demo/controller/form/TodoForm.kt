package com.example.ai_agent_demo.controller.form

import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class TodoForm(
    val id: Long? = null,

    @field:NotBlank(message = "タイトルは必須です")
    @field:Size(max = 100, message = "タイトルは100文字以内で入力してください")
    val title: String = "",

    @field:FutureOrPresent(message = "期限は現在以降の日付を入力してください")
    val dueDate: LocalDate = LocalDate.now(),

    val completed: Boolean = false
)
