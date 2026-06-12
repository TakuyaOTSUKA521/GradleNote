package org.example

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString


@Serializable
data class Task(
    val id: Int,
    val name: String
)

fun main() {
    val task = Task(
        id = 0,
        name = "First Serialize"
    )
    
    println(task.id)
}
