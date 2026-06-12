package org.example

import java.io.File
import kotlinx.serialization.json.Json

import org.example.model.Task

val file = File("src/main/kotlin/data/tasks.json")
val json = Json { prettyPrint = true }

fun loadsTasks():MutableList<Task> =
    if (file.exists()) json.decodeFromString(file.readText()) else mutableListOf()

fun saveTasks(tasks: List<Task>) =
    file.writeText(json.encodeToString(tasks))

fun handleCommand(args: List<String>) {
    when (args.getOrNull(0)) {
        "add" -> {
            val name = args.getOrNull(1) ?: run {
                println("Usage: type 'help'")
                return
            }
            val dueDate = args.getOrNull(2).toString()

            val tasks = loadsTasks()
            tasks.add(Task(id = tasks.size, name = name, dueDate = dueDate))
            saveTasks(tasks)
            println("Added: $name")
        }

        "list" -> {
            val tasks = loadsTasks()
            val filter = args.getOrNull(1)
            val filtered = when (filter) {
                "pending" -> tasks.filter { !it.done }
                "done" -> tasks.filter { it.done }
                null -> tasks
                else -> {
                    println("Usage: type 'help'")
                    return
                }
            }

            if (filtered.isEmpty()) println("No tasks")
            filtered.forEach { println(
                "[${it.id}] ${it.name}(Due:${it.dueDate}) - done: ${it.done}"
            ) }
        }

        "done" -> {
            val id = args.getOrNull(1)?.toIntOrNull() ?: run {
                println("Usage: done <id>")
                return
            }
            val tasks = loadsTasks()
            val doneTask = tasks.find { it.id == id } ?: run {
                println("Not found: $id")
                return
            }
            tasks[tasks.indexOf(doneTask)] = doneTask.copy(done = true)
            saveTasks(tasks)
            println("Done: ${doneTask.name}")
        }

        "help" -> {
            println("===  Commands  ===")
            println("Add task : add <name> <due-date>")
            println("List task: list <done/pending>")
            println("Done task: done <id>")
            println("Exit here: exit")
            println("")
            println("> 'add ManageTask'")
        }

        "exit" -> {
            System.exit(0)
        }
    }
}

fun main() {
    println("Task Manager - type 'help' for commands")
    while (true) {
        print(">")
        val input = readLine() ?: break
        val args = input.trim().split(" ")
        if (args.isEmpty() || args[0].isBlank()) continue
        handleCommand(args)
    }
}
