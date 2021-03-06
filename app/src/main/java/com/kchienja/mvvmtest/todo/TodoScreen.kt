package com.kchienja.mvvmtest.todo


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kchienja.mvvmtest.util.generateRandomTodoItem
import kotlin.random.Random
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.kchienja.mvvmtest.Database.TodoItemDataClass
import com.kchienja.mvvmtest.ui.theme.ButtonBlue
import com.kchienja.mvvmtest.ui.theme.LightRed
import com.kchienja.mvvmtest.ui.theme.OrangeYellow1
import com.kchienja.mvvmtest.ui.theme.TextWhite
import com.kchienja.mvvmtest.util.getCurrentDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun TodoDataClassScreen(
    items: List<TodoItemDataClass>,
    currentlyEditing: TodoItemDataClass?,
    onAddItem: (TodoItemDataClass) -> Unit,
    onRemoveItem: (TodoItemDataClass) -> Unit,
    onStartEdit: (TodoItemDataClass) -> Unit,
    onEditItemChange: (TodoItemDataClass) -> Unit,
    onEditDone: () -> Unit,
) {
    Column {
        val enableTopSection = currentlyEditing == null
        TodoItemInputBackground(elevate = enableTopSection) {
            if(!enableTopSection)
            {
                Text(
                    "Editing item",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    color = TextWhite,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
        TodoItemInputBackground(elevate = enableTopSection) {
            if (enableTopSection){
                TodoItemDataClassEntryInput(onItemComplete = onAddItem)
            }
        }

    LazyColumn(
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(top = 8.dp)
    ) {
        items(items) { todo ->
//            ElevatedTodoDataClassRow(todo,{ onStartEdit(it) },Modifier.fillParentMaxWidth(),)
            if (currentlyEditing?.itemId == todo.itemId){
                TodoItemInlineDataClassEditor(
                    item = currentlyEditing,
                    onEditItemChange = onEditItemChange,
                    onEditDone = onEditDone,
                    onRemoveItem = { onRemoveItem(todo) }
                )

            }else{
                ElevatedTodoDataClassRow(todo,{ onStartEdit(it) },Modifier.fillParentMaxWidth(),)


            }
            }
        }
    }
}
/**
 * Stateless component that is responsible for the entire todo screen.
 *
 * @param items (state) list of [TodoItem] to display
 * @param onAddItem (event) request an item be added
 * @param onRemoveItem (event) request an item be removed
 */
@Composable
fun TodoScreen(
    items: List<TodoItem>,
    currentlyEditing: TodoItem?,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit,
    onStartEdit: (TodoItem) -> Unit,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
) {
//    val currentDate = LocalDateTime.now()
//    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
//    val formatted = currentDate.format(formatter)



    Column {
//        TodoItemInputBackground(elevate = true, modifier = Modifier.fillMaxWidth()) {
//            TodoItemEntryInput(onItemComplete = onAddItem)
//        }

        val enableTopSection = currentlyEditing == null
        TodoItemInputBackground(elevate = enableTopSection) {
            if(!enableTopSection)
            {
                Text(
                    "Editing item",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    color = TextWhite,
                    modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(16.dp)
                            .fillMaxWidth()
                )
            }
        }


        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items){
                    todo ->
                if (currentlyEditing?.id == todo.id){
                    TodoItemInlineEditor(
                        item = currentlyEditing,
                        onEditItemChange = onEditItemChange,
                        onEditDone = onEditDone,
                        onRemoveItem = { onRemoveItem(todo) }
                    )

                }else{
                    ElevatedTodoRow(
                        todo,
                        { onStartEdit(it) },
                        Modifier.fillParentMaxWidth(),
                    )


                }
            }
        }

        TodoItemInputBackground(elevate = enableTopSection) {
            if (enableTopSection){
                TodoItemEntryInput(onItemComplete = onAddItem)
            }
        }

        // For quick testing, a random item generator button
        Button(
            onClick = { onAddItem(generateRandomTodoItem()) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text("Add random item")
        }
    }
}

/**
 * Stateless composable that displays a full-width [TodoItem].
 *
 * @param todo item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 */
@Composable
fun TodoRow(
    todo: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha : Float = remember(todo.id) { randomTint() }
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(todo.task)
        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = todo.icon.contentDescription)
        )
    }
}
/**
 * Stateless composable that displays a full-width [TodoItem].
 *
 * @param todo item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 * @param color color for this element
 */
@Composable
fun ElevatedTodoRow(
    todo: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha : Float = remember(todo.id) { randomTint() },
    color: Color = LightRed,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clickable { onItemClicked(todo) }
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = todo.task,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "Start Time ??? ${todo.time}",
                style = MaterialTheme.typography.body1,
                color = TextWhite
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(ButtonBlue)
                .padding(10.dp)
        ) {
            Icon(
                imageVector = todo.icon.imageVector,
                tint = LocalContentColor.current.copy(alpha = iconAlpha),
                contentDescription = stringResource(id = todo.icon.contentDescription),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun TodoItemInlineEditor(
    item: TodoItem,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) = TodoItemInput(
    text = item.task,
    onTextChange = { onEditItemChange(item.copy(task = it)) },
    icon = item.icon,
    onIconChange = { onEditItemChange(item.copy(icon = it)) },
    submit = onEditDone,
    iconsVisible = true,
    buttonSlot = {
        Row {
            val shrinkButtons = Modifier.widthIn(20.dp)
            TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                Text(
                    text = "\uD83D\uDCBE", // floppy disk
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(30.dp)
                )
            }
            TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
                Text(
                    text = "???",
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(30.dp)
                )
            }
        }
    }
)


private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}
@Composable
fun TodoInputTextField(text: String, onTextChange: (String) -> Unit,modifier: Modifier){
    TodoInputText(text, onTextChange,modifier )
}
@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    val (text, onTextChange) = remember { mutableStateOf("") }
    val (icon, onIconChange) = remember { mutableStateOf(TodoIcon.Default)}
    val time = getCurrentDate()

    val submit = {
        if (text.isNotBlank()) {
            onItemComplete(TodoItem(text,time, icon))
            onTextChange("")
            onIconChange(TodoIcon.Default)
        }
    }
    TodoItemInput(
        text = text,
        onTextChange = onTextChange,
        icon = icon,
        onIconChange = onIconChange,
        submit = submit,
        iconsVisible = text.isNotBlank()
    ) {
        TodoEditButton(onClick = submit, text = "Add", enabled = text.isNotBlank())
    }
}


@Composable
fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconsVisible: Boolean,
    buttonSlot: @Composable () ->   Unit

) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TodoInputText(
                text,
                onTextChange,
                Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                submit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(Modifier.align(Alignment.CenterVertically)) { buttonSlot() }

//            TodoEditButton(
//                onClick = submit,
//                text = "Add",
//                modifier = Modifier.align(Alignment.CenterVertically),
//                enabled = text.isNotBlank()
//            )
        }
        if (iconsVisible) {
            AnimatedIconRow(icon, onIconChange, Modifier.padding(top = 8.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

@Preview
@Composable
fun PreviewTodoScreen() {
    val time = getCurrentDate()
    val items = listOf(
        TodoItem("Learn compose", time, TodoIcon.Event),
        TodoItem("Take the code lab", time),
        TodoItem("Apply state",time, TodoIcon.Done),
        TodoItem("Build dynamic UIs", time, TodoIcon.Square)
    )
    TodoScreen(items, null, {}, {}, {}, {}, {})
}

//@Preview
@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    TodoRow(todo = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}
//@Preview
//@Composable
//fun PreviewTodoItemInput() = TodoItemInput(onItemComplete = { })




////////////////////////////////////////////////////////////////////////////
//TodoDataClass

@Composable
fun TodoItemDataClassEntryInput(onItemComplete: (TodoItemDataClass) -> Unit) {
    val (text, onTextChange) = remember { mutableStateOf("") }
    val (icon, onIconChange) = remember { mutableStateOf(TodoIcon.Default)}
    val time = getCurrentDate()

    val rnds = (0..10).random()


    val submit = {
        if (text.isNotBlank()) {
            onItemComplete(TodoItemDataClass(itemName = text, isDone = false))
            onTextChange("")
            onIconChange(TodoIcon.Default)
        }
    }
    TodoItemInput(
        text = text,
        onTextChange = onTextChange,
        icon = icon,
        onIconChange = onIconChange,
        submit = submit,
        iconsVisible = text.isNotBlank()
    ) {
        TodoEditButton(onClick = submit, text = "Add", enabled = text.isNotBlank())
    }
}

@Composable
fun ElevatedTodoDataClassRow(
    todo: TodoItemDataClass,
    onItemClicked: (TodoItemDataClass) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha : Float = remember(todo.itemId) { randomTint() },
    color: Color = LightRed,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clickable { onItemClicked(todo) }
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = todo.itemName,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "Start Time ??? " /*${todo.time}*/,
                style = MaterialTheme.typography.body1,
                color = TextWhite
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(ButtonBlue)
                .padding(10.dp)
        ) {
//            Icon(
//                imageVector = todo.icon.imageVector,
//                tint = LocalContentColor.current.copy(alpha = iconAlpha),
//                contentDescription = stringResource(id = todo.icon.contentDescription),
//                modifier = Modifier.size(16.dp)
//            )
        }
    }
}

@Composable
fun TodoItemInlineDataClassEditor(
    item: TodoItemDataClass,
    onEditItemChange: (TodoItemDataClass) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) = TodoItemDataClassInput(
    text = item.itemName,
    onTextChange = { onEditItemChange(item.copy(itemName = it)) },
//    icon = item.icon,
//    onIconChange = { onEditItemChange(item.copy(icon = it)) },
    submit = onEditDone,
    iconsVisible = true,
    buttonSlot = {
        Row {
            val shrinkButtons = Modifier.widthIn(20.dp)
            TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                Text(
                    text = "\uD83D\uDCBE", // floppy disk
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(30.dp)
                )
            }
            TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
                Text(
                    text = "???",
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(30.dp)
                )
            }
        }
    }
)

@Composable
fun TodoItemDataClassInput(
    text: String,
    onTextChange: (String) -> Unit,
//    icon: TodoIcon,
//    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconsVisible: Boolean,
    buttonSlot: @Composable () ->   Unit

) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TodoInputText(
                text,
                onTextChange,
                Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                submit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(Modifier.align(Alignment.CenterVertically)) { buttonSlot() }

//            TodoEditButton(
//                onClick = submit,
//                text = "Add",
//                modifier = Modifier.align(Alignment.CenterVertically),
//                enabled = text.isNotBlank()
//            )
        }
        if (iconsVisible) {
//            AnimatedIconRow(icon, onIconChange, Modifier.padding(top = 8.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}