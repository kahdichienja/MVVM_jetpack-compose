package com.kchienja.mvvmtest.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kchienja.mvvmtest.todo.TodoIcon

@Entity(tableName = "my_todo_list")
data class TodoItemDataClass(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,
    @ColumnInfo(name = "item_name")
    val itemName: String,
    @ColumnInfo(name = "Is_completed")
    val isDone: Boolean = false,

    )