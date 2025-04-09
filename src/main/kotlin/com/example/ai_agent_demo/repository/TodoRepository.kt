package com.example.ai_agent_demo.repository

import com.example.ai_agent_demo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long> {
    fun findByCompleted(completed: Boolean): List<Todo>
}