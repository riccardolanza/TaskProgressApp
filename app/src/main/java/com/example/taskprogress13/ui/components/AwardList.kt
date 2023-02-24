package com.example.taskprogress13.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskprogress13.R
import com.example.taskprogress13.data.Award
import com.example.taskprogress13.data.TaskExecution
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel
import kotlinx.coroutines.launch

val awardNameWeight = 1f
val taskExecutionMinutesNeededWeight = 1.7f


@Composable
fun AwardList(
    modifier: Modifier = Modifier,
    awardList: List<Award>
//    onAwardClick: (Award) -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally)
    {
        Divider()
        //   AwardListHeader()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                AwardListHeader(
                )
            }
            items(
                items = awardList,
                //    key = {it.id}
            ) { Award ->
                AwardItem(
                    Award = Award,
                    //           onAwardClick = onAwardClick
                )
                Divider()
            }
        }
    }
}


@Composable
fun AwardListHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.awardName),
            modifier = Modifier.weight(awardNameWeight),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.taskExecutionMinutesNeeded),
            modifier = Modifier.weight(taskExecutionMinutesNeededWeight),
            fontWeight = FontWeight.Bold
        )
        Box(modifier=Modifier.width(30.dp))
        { Text(text = "")}
    }
}




@Composable
private fun AwardItem(
    modifier: Modifier = Modifier,
    Award: Award,
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory),
//    onAwardClick: (Award) -> Unit,
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    //   var deleteConfirmationRequired:  Boolean =false
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = Award.awardName,
            modifier = Modifier.weight(awardNameWeight),
        )
        Text(
            text = Award.taskExecutionMinutesNeeded.toString(),
            modifier = Modifier.weight(taskExecutionMinutesNeededWeight),
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
                        viewModel.deleteAward(award=Award)
                    }
                },
                onDeleteCancel = { deleteConfirmationRequired = false }
            )
        }
    }
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
        text = { Text(stringResource(R.string.delete_award_question)) },
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




@Preview
@Composable
fun AwardListPreview(){
    AwardList(
        modifier = Modifier,
        awardList = arrayListOf(
            Award(awardName="Film", taskExecutionMinutesNeeded=30,),
            Award(awardName="Episodio Serie TV", taskExecutionMinutesNeeded=15)
        )
    )
}