package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val name: String,
    val dueDate: String? = null,
    val done: Boolean = false
)
