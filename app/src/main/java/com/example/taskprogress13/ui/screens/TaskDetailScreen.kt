package com.example.taskprogress13.ui.screens

import android.text.Layout
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskprogress13.R
import com.example.taskprogress13.ui.components.TaskExecutionList
import com.example.taskprogress13.ui.theme.Blu200
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel


@Composable
fun TaskDetailScreen(
    taskName: String,
    navigateToTaskExecutionEntryScreen: () -> Unit,
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory))
{
    val contextForToast = LocalContext.current.applicationContext
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(5.dp)
        ) {
            val taskExecutionList by viewModel.getTaskExecutionForTaskName(taskName)
                .collectAsState(emptyList())

            if (taskExecutionList.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_item_description),
                    style = MaterialTheme.typography.subtitle2
                )
            } else {
                TaskExecutionList(
                    // taskExecutionList = taskExecutionList
                    taskExecutionList = taskExecutionList
                )
            }

        }
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = navigateToTaskExecutionEntryScreen,
            backgroundColor = Blu200,
         //   contentColor = Color.White
    ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add"
            )
        }
    }
}


@Preview
@Composable
fun TaskDetailScreenPreview(){
    TaskDetailScreen(
        taskName="Inglese",
        navigateToTaskExecutionEntryScreen={}
    )
}