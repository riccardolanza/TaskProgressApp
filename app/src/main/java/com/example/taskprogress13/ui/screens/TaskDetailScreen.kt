package com.example.taskprogress13.ui.screens

import android.text.Layout
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.taskprogress13.DATABASE
import com.example.taskprogress13.R
import com.example.taskprogress13.data.TaskExecution
import com.example.taskprogress13.ui.components.TaskExecutionList
import com.example.taskprogress13.ui.theme.Blu200
import com.example.taskprogress13.ui.viewmodel.RemoteTaskExecutionListUiState
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel


@Composable
fun TaskDetailScreen(
    taskName: String,
    navigateToTaskExecutionEntryScreen: () -> Unit,
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory),
    navController:NavController
)
{
    if (DATABASE == "local") {
        val taskExecutionList by viewModel.getTaskExecutionsByTaskName(taskName)
            .collectAsState(emptyList())
        TaskExecutionListScreen(
            taskExecutionList = taskExecutionList,
            modifier = Modifier,
            taskName = taskName,
            navigateToTaskExecutionEntryScreen=navigateToTaskExecutionEntryScreen,
            navController=navController
        )
    } else {
        LaunchedEffect(Unit, block = { viewModel.getRemoteTaskExecutionsByTaskName(taskName) })
        //val retryAction = viewModel::getAllRemoteTaskExecutions
        val retryAction = {} //TODO
        val remoteTaskExecutionListUiState = viewModel.remoteTaskExecutionListUiState
        when (remoteTaskExecutionListUiState) {
            is RemoteTaskExecutionListUiState.Loading -> LoadingScreen(modifier = Modifier)
            is RemoteTaskExecutionListUiState.Success -> TaskExecutionListScreen(
                taskExecutionList = remoteTaskExecutionListUiState.remoteTaskExecutions,
                modifier = Modifier,
                taskName = taskName,
                navigateToTaskExecutionEntryScreen = navigateToTaskExecutionEntryScreen,
                navController = navController
            )
            is RemoteTaskExecutionListUiState.Error -> ErrorScreen(retryAction, modifier = Modifier)
        }
    }
}

@Composable
fun TaskExecutionListScreen(
    taskExecutionList: List<TaskExecution>,
    modifier: Any,
    taskName: String,
    navigateToTaskExecutionEntryScreen: () -> Unit,
    navController: NavController
) {
   val contextForToast = LocalContext.current.applicationContext
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(5.dp)
        ) {
            if (taskExecutionList.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_item_description),
                    style = MaterialTheme.typography.subtitle2
                )
            } else {
                TaskExecutionList(
                    taskExecutionList = taskExecutionList,
                    navController = navController
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

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}