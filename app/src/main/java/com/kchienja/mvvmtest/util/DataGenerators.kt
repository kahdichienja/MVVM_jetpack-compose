package com.kchienja.mvvmtest.util

import com.kchienja.mvvmtest.todo.TodoIcon
import com.kchienja.mvvmtest.todo.TodoItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun getCurrentDate(): String {
    val currentDate = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    val formatted = currentDate.format(formatter)

    return formatted
}

fun generateRandomTodoItem(): TodoItem {
    val message = listOf(
        "Learn compose",
        "Learn state",
        "Build dynamic UIs",
        "Learn Unidirectional Data Flow",
        "Integrate LiveData",
        "Integrate ViewModel",
        "Remember to savedState!",
        "Build stateless composables",
        "Use state from stateless composables"
    ).random()
    val icon = TodoIcon.values().random()
    val time = getCurrentDate()
    return TodoItem(message, time ,icon)
}

fun main() {
    val list = listOf(1, 2, 3)
    var currentCount = 0
    for (item in list) {
        currentCount += item
        println(currentCount)
    }
}