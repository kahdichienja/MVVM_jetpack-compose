package com.kchienja.mvvmtest.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {
//    private  state
    private var currentEditPosition by mutableStateOf(-1)
//    private var _todoItems = MutableLiveData(listOf<TodoItem>())
//    val todoItems: LiveData<List<TodoItem>> = _todoItems
    var todoItems = mutableStateListOf<TodoItem>()
        private set

    fun addItem(item: TodoItem) {
//        _todoItems.value = _todoItems.value!! + listOf(item)

        todoItems.add(item)
    }

    fun removeItem(item: TodoItem) {
//        _todoItems.value = _todoItems.value!!.toMutableList().also {
//            it.remove(item)
        todoItems.remove(item)
        onEditDone()
    }

//    state
    val currentEditItem: TodoItem?
    get() = todoItems.getOrNull(currentEditPosition)

// event: onEditItemSelected
    fun onEditItemSelected(item: TodoItem){
        currentEditPosition = todoItems.indexOf(item)
    }
// event: onEditDone
    fun onEditDone(){
        currentEditPosition = -1
    }
// event: onEditItemChange
    fun onEditItemChange(item: TodoItem){
        val currentItem = requireNotNull(currentEditItem)

        require(currentItem.id ==item.id){
            "You can only change an item with the same id as currentEditItem"
        }
        todoItems[currentEditPosition] = item
    }

}