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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskprogress13.R
import com.example.taskprogress13.data.UsedAward
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel
import kotlinx.coroutines.launch

val usedAwardNameWeight = 1.5f
val usedAwardTaskExecutionMinutesNeededWeight = 1f
val dateOfUseWeight = 1.5f
val usedAwardTaskNameWeight = 1f

@Composable
fun UsedAwardList(
    modifier: Modifier = Modifier,
    usedAwardList: List<UsedAward>,
    iconType: String
) {
   Column(
       horizontalAlignment = Alignment.CenterHorizontally,
       modifier = Modifier.fillMaxWidth()
   )
    {
        // Divider()
        Spacer(modifier = Modifier)
        //   AwardListHeader()
        LazyColumn(
            modifier = Modifier
                .padding(1.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                UsedAwardListHeader(
                )
            }
            items(
                items = usedAwardList,
                //    key = {it.id}
            ) { usedAward ->
                UsedAwardItem(
                    usedAward = usedAward,
                    iconType = iconType
                    //           onAwardClick = onAwardClick
                )
                Divider()
            }
        }
    }
}


@Composable
fun UsedAwardListHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.awardName),
            modifier = Modifier.weight(usedAwardNameWeight),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.taskExecutionMinutesNeeded),
            modifier = Modifier.weight(usedAwardTaskExecutionMinutesNeededWeight),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.dateOfUse),
            modifier = Modifier.weight(dateOfUseWeight),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.taskName),
            modifier = Modifier.weight(usedAwardTaskNameWeight),
            fontWeight = FontWeight.Bold
        )
        Box(modifier=Modifier.width(30.dp))
        { Text(text = "")}
    }
}




@Composable
private fun UsedAwardItem(
    modifier: Modifier = Modifier,
    usedAward: UsedAward,
    iconType:String,
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
            text = usedAward.awardName,
            modifier = Modifier.weight(usedAwardNameWeight),
        )
        Text(
            text = usedAward.taskExecutionMinutesNeeded.toString(),
            modifier = Modifier.weight(usedAwardTaskExecutionMinutesNeededWeight),
        )
        Text(
            text = usedAward.dateOfUse.toString(),
            modifier = Modifier.weight(dateOfUseWeight),
        )
        Text(
            text = usedAward.taskName.toString(),
            modifier = Modifier.weight(usedAwardTaskNameWeight),
        )
        if(iconType!="Nulla") {
            IconButton(
                onClick = { deleteConfirmationRequired = true },
                modifier = Modifier.size(30.dp)
            ) {
                if (iconType == "CheckBox") {
                    Checkbox(checked = false, onCheckedChange = {})
                } else {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.Red
                    )
                }
            }
        }
        else {
            Box(modifier = Modifier.width(30.dp))
            { Text(text = "") }
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    coroutineScope.launch {
                        viewModel.deleteUsedAward(usedAward=usedAward)
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
        text = { Text(stringResource(R.string.delete_used_award_question)) },
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
fun UsedAwardListPreview(){
    UsedAwardList(
        modifier = Modifier,
        usedAwardList = arrayListOf(
            UsedAward(awardName="Film", taskExecutionMinutesNeeded=30, dateOfUse = "25-02-2023 09:23",dateOfUseUT = 123456678,taskName = "English"),
            UsedAward(awardName="Episodio Serie", taskExecutionMinutesNeeded=15, dateOfUse = "23-02-2023 17:02",dateOfUseUT = 123456678, taskName  = "English")
        ),
        iconType = "Delete"
    )
}