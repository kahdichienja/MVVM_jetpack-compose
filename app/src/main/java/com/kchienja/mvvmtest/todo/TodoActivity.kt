package com.kchienja.mvvmtest.todo


import android.app.Application
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kchienja.mvvmtest.Database.TodoDataClassViewModel
import com.kchienja.mvvmtest.Database.TodoDataClassViewModelFactory
import com.kchienja.mvvmtest.ui.theme.DeepBlue
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
    TodoDataClassMainScreen(navController = navController)
}



@Composable
fun TodoActivityScreen(todoViewModel: TodoViewModel) {
//    val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())
    Box(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {
        TodoScreen(
            items = todoViewModel.todoItems,
            currentlyEditing = todoViewModel.currentEditItem,
            onAddItem = todoViewModel::addItem,
            onRemoveItem = todoViewModel::removeItem,
            onStartEdit = todoViewModel::onEditItemSelected,
            onEditItemChange = todoViewModel::onEditItemChange,
            onEditDone = todoViewModel::onEditDone,
        )
    }
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
@Composable
fun TodoDataClassMainScreen(navController: NavController){

    val context = LocalContext.current
    val mTodoViewModel: TodoDataClassViewModel = viewModel(
        factory = TodoDataClassViewModelFactory(context.applicationContext as Application)
    )
    val items = mTodoViewModel.readAllData.observeAsState(listOf()).value
        Box(
            modifier = Modifier
                .background(DeepBlue)
                .fillMaxSize()
        ){
            TodoDataClassScreen(
                items = items,
                currentlyEditing =  mTodoViewModel.currentEditItem,// todoViewModel.currentEditItem,
                onAddItem = { todoItem -> mTodoViewModel.addTodo(todoItem) },
                onRemoveItem = {todoItem -> mTodoViewModel.deleteTodo(todoItem) },
                onStartEdit = {todoItem -> mTodoViewModel.onEditItemSelected(todoItem) },//todoViewModel::onEditItemSelected,
                onEditItemChange = {todoItem -> mTodoViewModel.onEditItemChange(todoItem) },//todoViewModel::onEditItemChange,
                onEditDone ={ mTodoViewModel.onEditDone() }// todoViewModel::onEditDone,
            )
        }
    }




