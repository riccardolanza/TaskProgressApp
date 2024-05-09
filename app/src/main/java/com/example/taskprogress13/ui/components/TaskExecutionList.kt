package com.example.taskprogress13.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.taskprogress13.AllTaskExecutionsScreenDestination
import com.example.taskprogress13.R
import com.example.taskprogress13.TaskDetailScreenDestination
import com.example.taskprogress13.data.TaskExecution
import com.example.taskprogress13.navigateSingleTopTo
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel
import kotlinx.coroutines.launch

val executionDateWeight = 1.2f
val taskNameWeight = 1f
val subTaskNameWeight = 2f
val durationWeight = 1f
val noteWeight = 2f

@Composable
fun TaskExecutionList(
    modifier: Modifier = Modifier,
    taskExecutionList: List<TaskExecution>,
    navController: NavController
) {
   Column(horizontalAlignment = Alignment.CenterHorizontally)
    {
        Divider()
     //   TaskExecutionListHeader()
        LazyColumn(
            modifier = Modifier
            //    .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                TaskExecutionListHeader(
                )
            }
             items(
                items = taskExecutionList,
                //    key = {it.id}
            ) { taskExecution ->
                TaskExecutionItem(
                    taskExecution = taskExecution,
                    navController = navController
                )
                Divider()
            }
        }
    }
}


@Composable
fun TaskExecutionListHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.execution_date),
            modifier = Modifier.weight(executionDateWeight),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.taskName),
            modifier = Modifier.weight(taskNameWeight),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.subTaskName),
            modifier = Modifier.weight(subTaskNameWeight),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.duration),
            modifier = Modifier.weight(durationWeight),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.note),
            modifier = Modifier.weight(noteWeight),
            fontWeight = FontWeight.Bold
        )
        Box(modifier=Modifier.width(30.dp))
        { Text(text = "")}
    }
}




@Composable
private fun TaskExecutionItem(
    modifier: Modifier = Modifier,
    taskExecution: TaskExecution,
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory),
    navController: NavController
    //deleteTaskExecution: () -> Unit
//    onTaskExecutionClick: (TaskExecution) -> Unit,
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = taskExecution.executionDate,
            modifier = Modifier.weight(executionDateWeight),
        )
        Text(
            text = taskExecution.taskName,
            modifier = Modifier.weight(taskNameWeight),
        )
        Text(
            text = taskExecution.subTaskName,
            modifier = Modifier.weight(subTaskNameWeight),
        )
        Text(
            text = transformTohhmm(taskExecution.duration.toString()),
            modifier = Modifier.weight(durationWeight),
            )
        Text(
            text = taskExecution.note,
            modifier = Modifier.weight(noteWeight),
        )
        IconButton(
            onClick = { deleteConfirmationRequired=true },
            modifier = Modifier.size(30.dp)
        ) {
            Icon(imageVector = Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = Color.Red)
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    coroutineScope.launch {
                        println("Cancellazione confermata sul DeleteConfirmationDialog")
                        viewModel.deleteTaskExecution(taskExecution=taskExecution)
                        println("Cancellazione avvenuta?")
                        println("currentDestination: " + navController.currentBackStackEntry?.destination?.route)
                        println("current argument taskName: " + navController.currentBackStackEntry?.arguments?.getString("task_name"))
                        if (navController.currentBackStackEntry?.arguments?.getString("task_name") == null)
                            navController.navigate(AllTaskExecutionsScreenDestination.route)
                        else navController.navigate("${TaskDetailScreenDestination.route}/${navController.currentBackStackEntry?.arguments?.getString("task_name")}")

                        //   navController.navigateUp()
                   }
               },
                onDeleteCancel = { deleteConfirmationRequired = false }
            )
        }
    }
}

fun transformTohhmm(durationInMinutes: String):String
{
 //   println("durationInMinutes: $durationInMinutes")
    val quoziente = durationInMinutes.toInt()/60
 //   println("quoziente: $quoziente")
    val resto = durationInMinutes.toInt()%60
 //   println("resto: $resto")
    var durationInhhmm = "${durationInMinutes}m"
    if(quoziente>0) {durationInhhmm = "${quoziente}h ${resto}m"}
 //   println("durationInhhmm: $durationInhhmm")
    return durationInhhmm
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_taskExecution_question)) },
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}




