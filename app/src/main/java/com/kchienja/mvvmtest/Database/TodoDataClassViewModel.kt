package com.kchienja.mvvmtest.Database

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.kchienja.mvvmtest.todo.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoDataClassViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<TodoItemDataClass>>
    private val repository: TodoRepository

    //    private  state
    private var currentEditPosition by mutableStateOf(-1)

    var todoItems = mutableStateListOf<TodoItemDataClass>()
        private set

    init {
        val todoDatabase = TodoDatabase.getInstance(application).todoDao()

        repository = TodoRepository(todoDatabase)

        readAllData = repository.readAllData
    }

    fun addTodo(todoItem: TodoItemDataClass){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTodo(todoItem)
        }
    }

    fun updateTodo(todoItem: TodoItemDataClass){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTodo(todoItem)
        }
    }
    fun deleteTodo(todoItem: TodoItemDataClass){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTodo(todoItem)
        }
    }
    fun deleteAllTodo(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllTodo()
        }
    }


    //    state
    val currentEditItem: TodoItemDataClass?
        get() = todoItems.getOrNull(currentEditPosition)

    // event: onEditItemSelected
    fun onEditItemSelected(item: TodoItemDataClass){
        currentEditPosition = todoItems.indexOf(item)
    }
    // event: onEditDone
    fun onEditDone(){
        currentEditPosition = -1
    }
    // event: onEditItemChange
    fun onEditItemChange(item: TodoItemDataClass){
        val currentItem = requireNotNull(currentEditItem)

        require(currentItem.itemId ==item.itemId){
            "You can only change an item with the same id as currentEditItem"
        }
        todoItems[currentEditPosition] = item
    }

}


class TodoDataClassViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TodoDataClassViewModel::class.java)) {
            return TodoDataClassViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}