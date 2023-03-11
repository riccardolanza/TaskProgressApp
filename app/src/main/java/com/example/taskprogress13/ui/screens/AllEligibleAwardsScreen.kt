package com.example.taskprogress13.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskprogress13.R
import com.example.taskprogress13.data.Award
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable

fun AllEligibleAwardsScreen(
    navigateToStartScreen:() -> Unit,
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory),
    context: Context
)
{
    //val selectedValue = remember { mutableStateOf("") }
    val selectedValue = remember { mutableStateOf(value = Award("",0)) }
    //val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val isSelectedItem: (Award) -> Boolean = { selectedValue.value == it }
    //val onChangeState: (String) -> Unit = { selectedValue.value = it }
    val onChangeState: (Award) -> Unit = { selectedValue.value = it }
    val awardList by viewModel.getAllAwards().collectAsState(emptyList())
    var saveConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var usedAwardUiState = viewModel.usedAwardUiState

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
     ) {
        Image(
            painter=painterResource(R.drawable.brava_ciccipoppola),
            contentDescription = "Brava Ciccipoppola!",
            modifier = Modifier
                .size(500.dp)
                .padding(10.dp)
        )
        Text(
            text = "Complimenti Ciccipoppola," + "\n" + "hai diritto ad uno dei seguenti premi!",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "(tra parentesi i minuti che verranno scalati dalle attività eseguite)",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier=Modifier.height(50.dp))
        awardList.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                //              horizontalArrangement = Alignment.CenterHorizontally,
                modifier = Modifier
                    .selectable(
                        //    selected = isSelectedItem(item.awardName),
                        selected = isSelectedItem(item),
                        //    onClick = { onChangeState(item.awardName) },
                        onClick = { onChangeState(item) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                RadioButton(
                //    selected = isSelectedItem(item.awardName),
                    selected = isSelectedItem(item),
                    onClick = null,
                    modifier = Modifier.weight(0.5f)
                )
                Text(
                    text = "${item.awardName} (${item.taskExecutionMinutesNeeded} minuti)",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)

                )
            }
        }
      //  Text(text = "Premio selezionato: ${selectedValue.value.ifEmpty { "Nessuno" }}")
        Spacer(modifier=Modifier.height(30.dp))

        Button(
            onClick = { saveConfirmationRequired = true},
            //enabled = (selectedValue.value != ""),
            enabled = (selectedValue.value != Award("",0)),
            contentPadding = PaddingValues(horizontal = 40.dp)
                //modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action))
        }
        if (saveConfirmationRequired) {
            SaveConfirmationDialog(
                chosenAward = selectedValue.value.awardName,
                onSaveConfirm = {
                    saveConfirmationRequired = false
                    val formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                    val dateOfUse = LocalDateTime.now().format(formatter)
                    coroutineScope.launch {
                        viewModel.saveUsedAward(usedAward=selectedValue.value,dateOfUse.toString(),taskName="Inglese")
                        Toast.makeText(context, "Execuzione salvata!", Toast.LENGTH_SHORT).show()
                    }
                    navigateToStartScreen()
                },
                onSaveCancel = {saveConfirmationRequired = false}
            )
        }

    }
}




@Composable
private fun SaveConfirmationDialog(
    onSaveConfirm: () -> Unit,
    onSaveCancel: () -> Unit,
    chosenAward: String,
    //chosenAward:Award,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(
            text = stringResource(R.string.attention),
            textAlign = TextAlign.Center
        ) },
//        text = { Text(stringResource(R.string.delete_taskExecution_question)) },
        text = {Text(
            text ="Confermi che vuoi utilizzare il premio '${chosenAward}'?",
            textAlign = TextAlign.Center)},
        modifier = modifier
            .padding(16.dp),
        dismissButton = {
            TextButton(onClick = onSaveCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onSaveConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}

/*
{
    val awardList by viewModel.getAllAwards().collectAsState(emptyList())
    Column(horizontalAlignment = Alignment.CenterHorizontally)
    {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(30.dp)
            ) {
                //       val taskExecutionList by viewModel.getTaskExecutionForTaskName(taskName)
                //           .collectAsState(emptyList())

                if (awardList.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_item_description),
                        style = MaterialTheme.typography.subtitle2
                    )
                } else {
                    AwardList(
                        awardList = awardList,
                        iconType = "CheckBox"
                    )
                }

            }
        }
    }
}

*/

/*
fun entitledAwards(
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory),
    taskReportData: TaskReportData
): Flow<List<Award>>
{
    if(taskReportData.last7Days_progress>=100)
    {
        return viewModel.getByTaskExecutionMinutesNeeded(taskReportData.last7Days_duration)
    }
    else if (taskReportData.last30Days_progress>=100)
    {
        return viewModel.getByTaskExecutionMinutesNeeded(taskReportData.last30Days_duration)
    }
    else return viewModel.getByTaskExecutionMinutesNeeded(0)
}
*/
