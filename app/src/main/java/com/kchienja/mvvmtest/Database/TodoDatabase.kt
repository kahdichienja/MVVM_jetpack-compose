package com.kchienja.mvvmtest.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItemDataClass::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDatabaseDao

    companion object{

        private var INSTANCE: TodoDatabase?  = null

        fun getInstance(context: Context): TodoDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance ==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "my_todo_list"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}