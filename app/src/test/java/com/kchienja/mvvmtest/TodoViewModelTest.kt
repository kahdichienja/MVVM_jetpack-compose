package com.kchienja.mvvmtest

import com.google.common.truth.Truth.assertThat
import com.kchienja.mvvmtest.todo.TodoViewModel
import com.kchienja.mvvmtest.util.generateRandomTodoItem
import org.junit.Test

class TodoViewModelTest {
    @Test
    fun whenRemovingItem_updateLst(){
        val viewModel  = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()

        viewModel.addItem(item1)
        viewModel.addItem(item2)

        viewModel.removeItem(item1)

        assertThat(viewModel.todoItems).isEqualTo(listOf(item2))
    }
}