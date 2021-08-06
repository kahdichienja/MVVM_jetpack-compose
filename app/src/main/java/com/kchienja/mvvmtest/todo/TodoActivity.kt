package com.kchienja.mvvmtest.todo


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.kchienja.mvvmtest.ui.theme.MVVMTestTheme

class TodoActivity : AppCompatActivity() {

    val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMTestTheme {
                Surface {
                    // TODO: build the screen in compose
                    TodoActivityScreen(todoViewModel)
                }
            }
        }
    }
}

@Composable
fun TodoActivityNavigationEntryScreen(navController: NavController) {

}



@Composable
fun TodoActivityScreen(todoViewModel: TodoViewModel){
//    val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())

    TodoScreen(
        items = todoViewModel.todoItems,
        currentlyEditing = todoViewModel.currentEditItem,
        onAddItem = todoViewModel::addItem,
        onRemoveItem = todoViewModel::removeItem,
        onStartEdit = todoViewModel::onEditItemSelected,
        onEditItemChange = todoViewModel::onEditItemChange,
        onEditDone = todoViewModel::onEditDone
    )


//    TodoScreen(
//        items = items,
//        onAddItem = //todoViewModel::addItem,
//        {
//            todoItem -> todoViewModel.addItem(todoItem)
//        },
//        onRemoveItem ={
//
//            todoItem -> todoViewModel.removeItem(todoItem)
//        }
//    )


}