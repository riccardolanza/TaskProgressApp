package com.example.taskprogress13.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskprogress13.R
import com.example.taskprogress13.data.TaskExecution
import com.example.taskprogress13.ui.components.TaskExecutionList

@Composable
fun AllTaskExecutionsScreen(
    taskExecutionList: List<TaskExecution>
)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(30.dp)
    ) {
        if (taskExecutionList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                style = MaterialTheme.typography.subtitle2
            )
        } else {
            TaskExecutionList(
                taskExecutionList = taskExecutionList
            )
        }

    }
}


@Preview
@Composable
fun AllTasksScreenPreview(){
    AllTaskExecutionsScreen(
        taskExecutionList = arrayListOf(
            TaskExecution(taskName="Inglese", subTaskName="Speexx", duration=10, executionDate="2023-01-12",executionDateUT=0, note = ""),
            TaskExecution(taskName="Compiti", subTaskName="-", duration=10, executionDate="2023-01-13",executionDateUT=0,note="Me lo sono inventato ;-)"),
            TaskExecution(taskName="Altro", subTaskName="Boh", duration=30, executionDate="2023-01-13",executionDateUT=0, note="Mamma può testimoniare!")
       )
    )
}