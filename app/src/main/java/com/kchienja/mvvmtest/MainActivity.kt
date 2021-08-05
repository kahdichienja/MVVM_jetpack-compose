package com.kchienja.mvvmtest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kchienja.mvvmtest.AppNavigator.AppNavigator
import com.kchienja.mvvmtest.todo.TodoActivityScreen
import com.kchienja.mvvmtest.todo.TodoViewModel
import com.kchienja.mvvmtest.ui.theme.DeepBlue
import com.kchienja.mvvmtest.ui.theme.MVVMTestTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val todoViewModel by viewModels<TodoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    AppNavigator()
                    TodoActivityScreen(todoViewModel)

                }
            }
        }
    }
}

@Composable
fun DetailedViwPage(navController: NavController) {
    LazyColumn() {
        item {


            Image(
                painterResource(R.drawable.yt_flutter_django_daraja),
                contentDescription = "",
                modifier = Modifier.height(120.dp),
                contentScale = ContentScale.FillWidth
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Happy Meal",
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "800 Calories",
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                )
                Spacer(modifier = Modifier.padding(10.dp),)
                Text(
                    text = "$212 ",
                    style = TextStyle(
                        fontSize = 17.sp,
                        color = Color.Red
                    ),
                )
                Spacer(modifier = Modifier.padding(10.dp),)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SelectionContainer {
                        Text(
                            text = "$212 ",
                            style = TextStyle(
                                fontSize = 17.sp,
                                color = Color.Blue
                            ),
                        )

                    }
                    Spacer(modifier = Modifier.width(100.dp))
                    Icon(painter = painterResource(R.drawable.copy_icon), contentDescription = " ")

                }
                OutlinedTextFieldSample(modifier = Modifier.fillMaxWidth())

            }
        }
    }

}
@Composable
fun OutlinedTextFieldSample(modifier: Modifier, scaffoldState: ScaffoldState = rememberScaffoldState()) {
    var text by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()



    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") },
            modifier = modifier
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = modifier
        )
        Spacer(modifier = modifier.height(10.dp))

        Button(
            onClick = {
               scope.launch {
                   Log.d("TAG", "OutlinedTextFieldSample: $text")

                   scaffoldState.snackbarHostState.showSnackbar(
                       message = text,
                       actionLabel = "Ok"
                   )
               }
            },
            modifier = modifier
        ) {
            Text(text = "Click")

        }

    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MVVMTestTheme {
//        DetailedViwPage()
    }
}


@Composable
fun SnackBarShow(txt: String, scaffoldState: ScaffoldState = rememberScaffoldState()){

    LaunchedEffect(scaffoldState.snackbarHostState) {
        // Show snackbar using a coroutine, when the coroutine is cancelled the
        // snackbar will automatically dismiss. This coroutine will cancel whenever
        // `state.hasError` is false, and only start when `state.hasError` is true
        // (due to the above if-check), or if `scaffoldState.snackbarHostState` changes.
        scaffoldState.snackbarHostState.showSnackbar(
            message = txt,
            actionLabel = "Ok"
        )
    }

}