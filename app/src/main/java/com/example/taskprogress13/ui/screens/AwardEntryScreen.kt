package com.example.taskprogress13.ui.screens

import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Place

import androidx.compose.runtime.*
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
import com.example.taskprogress13.ui.components.AwardList
import com.example.taskprogress13.ui.theme.Blu200
import com.example.taskprogress13.ui.theme.TaskProgress13Theme
import com.example.taskprogress13.ui.viewmodel.AwardUiState
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel


@Composable
fun AwardEntryScreen(
    awardUiState: AwardUiState,
    onAwardValueChange: (AwardUiState) -> Unit,
    onSaveClick: () -> Unit,
    onFABclick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory)
) {
    val awardList by viewModel.getByAwardName(awardUiState.awardName).collectAsState(emptyList())
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        AwardInputForm(awardUiState = awardUiState, onValueChange = onAwardValueChange)
        Box(Modifier.fillMaxSize()){
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp)            ,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Button(
                    onClick = onSaveClick,
                    enabled = awardUiState.actionEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.save_action))
                }

                VisualizeTaskExecutionEntrySaved(
                    modifier = Modifier,
                    awardList=awardList,
                    awardEntrySaved=awardUiState.awardEntrySaved,
                    onFABclick=onFABclick,
                )
            }
        }
    }
}



@Composable
fun AwardInputForm(
    awardUiState: AwardUiState,
    modifier: Modifier = Modifier,
    onValueChange: (AwardUiState) -> Unit = {},
    enabled: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {


        OutlinedTextField(
            value = awardUiState.awardName,
            onValueChange = { onValueChange(awardUiState.copy(awardName = it)) },
            label = { Text(stringResource(R.string.awardName)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = awardUiState.taskExecutionMinutesNeeded,
            onValueChange = {
                if (it == "") {
                    onValueChange(awardUiState.copy(taskExecutionMinutesNeeded = ""))
                }
                else if (it.toDoubleOrNull() != null)
                {
                    onValueChange(awardUiState.copy(taskExecutionMinutesNeeded = it))
                } //
                else {}

            },
            label = { Text(stringResource(R.string.taskExecutionMinutesNeeded_req)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}

@Composable
fun VisualizeTaskExecutionEntrySaved(
    modifier: Modifier=Modifier,
    awardList: List<Award>,
    awardEntrySaved: Boolean,
    onFABclick: () -> Unit,
) {
    if(awardEntrySaved) {
        if (awardList.isEmpty()) {
            Box(){
                Text(
                    text = stringResource(R.string.no_available_awards),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        } else {
            Box(Modifier.fillMaxSize()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text(
                        text = stringResource(R.string.esito_salvataggio),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    AwardList(
                        awardList = awardList,
                        iconType = "Delete"
                    )
                    Box(Modifier.fillMaxSize()) {
                        FloatingActionButton(
                            modifier = Modifier
                                .padding(all = 16.dp)
                                .align(alignment = Alignment.BottomEnd),
                            onClick = onFABclick,
                            backgroundColor = Blu200,
                            //    contentColor = Color.White
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "OK",
                                //    tint= Color.Green
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    TaskProgress13Theme() {
        AwardEntryScreen(
            awardUiState = AwardUiState(
                awardName = "Film",
                taskExecutionMinutesNeeded = "30"
            ),
            onAwardValueChange = {},
            onSaveClick = {},
            onFABclick = {}
        )
    }
}