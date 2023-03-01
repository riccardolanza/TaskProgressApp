package com.example.taskprogress13.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskprogress13.R

import com.example.taskprogress13.ui.components.UsedAwardList
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel

@Composable
fun AllUsedAwardsScreen(
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory),
)
{
    val usedAwardList by viewModel.getAllUsedAwards().collectAsState(emptyList())
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(30.dp)
        ) {
            //       val taskExecutionList by viewModel.getTaskExecutionForTaskName(taskName)
            //           .collectAsState(emptyList())

            if (usedAwardList.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_used_awards_description),
                    style = MaterialTheme.typography.subtitle2
                )
            } else {
                UsedAwardList(
                    usedAwardList = usedAwardList,
                    iconType = "Delete"
                )
            }

        }
        /*
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = navigateToUsedAwardEntryScreen,
            backgroundColor = Blu200,
            //   contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add"
            )
        }
        */
    }
}


@Preview
@Composable
fun AllUsedAwardsScreenPreview(){
    AllUsedAwardsScreen(
        //navigateToAwardEntryScreen={}
        /*
        awardList = arrayListOf(
            Award(awardName="Film", taskExecutionMinutesNeeded=30,),
            Award(awardName="Episodio Serie TV", taskExecutionMinutesNeeded=15)
        )
        */
    )
}