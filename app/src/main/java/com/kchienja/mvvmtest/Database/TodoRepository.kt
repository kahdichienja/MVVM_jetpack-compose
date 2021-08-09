package com.kchienja.mvvmtest.Database

import androidx.lifecycle.LiveData

class TodoRepository(private val todoDatabaseDao: TodoDatabaseDao) {

    val readAllData: LiveData<List<TodoItemDataClass>> = todoDatabaseDao.getAll()

    suspend fun addTodo(todoItemDataClass: TodoItemDataClass){
        todoDatabaseDao.insert(todoItemDataClass)
    }

    suspend fun updateTodo(todoItemDataClass: TodoItemDataClass){
        todoDatabaseDao.update(todoItemDataClass)
    }

    suspend fun deleteTodo(todoItemDataClass: TodoItemDataClass){
        todoDatabaseDao.delete(todoItemDataClass)
    }

    suspend fun deleteAllTodo(){
        todoDatabaseDao.deleteAllTodos()
    }
}