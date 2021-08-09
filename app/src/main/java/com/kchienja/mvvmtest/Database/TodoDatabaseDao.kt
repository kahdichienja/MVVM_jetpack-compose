package com.kchienja.mvvmtest.Database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TodoDatabaseDao {

    @Query("SELECT * From my_todo_list")
    fun getAll(): LiveData<List<TodoItemDataClass>>

    @Query("SELECT * From my_todo_list where itemId = :id")
    fun getById(id: Int): TodoItemDataClass?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TodoItemDataClass)

    @Update
    suspend fun update(item: TodoItemDataClass)

    @Delete
    suspend fun delete(item: TodoItemDataClass)

//    @Delete
//    suspend fun deleteAllTodos()
}