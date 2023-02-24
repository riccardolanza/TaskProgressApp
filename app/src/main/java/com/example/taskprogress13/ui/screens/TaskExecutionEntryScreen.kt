package com.example.taskprogress13.ui.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskprogress13.R
import com.example.taskprogress13.data.TaskExecution
import com.example.taskprogress13.ui.components.TaskExecutionList
import com.example.taskprogress13.ui.theme.Blu200
import com.example.taskprogress13.ui.theme.TaskProgress13Theme
import com.example.taskprogress13.ui.viewmodel.TaskProgressUiState
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel

import java.util.*

@Composable
fun TaskExecutionEntryScreen(
    taskProgressUiState: TaskProgressUiState,
    onTaskExecutionValueChange: (TaskProgressUiState) -> Unit,
    onSaveClick: () -> Unit,
    onFABclick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory),
    navController: NavHostController = rememberNavController()
) {
    val taskExecutionList by viewModel.getTaskExecutionFor_taskName_subTaskName_executionDate(taskProgressUiState.taskName, taskProgressUiState.subTaskName, taskProgressUiState.executionDate).collectAsState(emptyList())
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        TaskExecutionInputForm(taskProgressUiState = taskProgressUiState, onValueChange = onTaskExecutionValueChange)
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp)            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
             Button(
                 onClick = onSaveClick,
                 enabled = taskProgressUiState.actionEnabled,
                 modifier = Modifier.fillMaxWidth()
             ) {
                 Text(stringResource(R.string.save_action))
             }

            visualizeTaskExecutionEntrySaved(
                modifier = Modifier,
                taskExecutionList=taskExecutionList,
                taskExecutionEntrySaved=taskProgressUiState.taskExecutionEntrySaved,
                onFABclick=onFABclick,
            )
        }
    }
}



@Composable
fun TaskExecutionInputForm(
    taskProgressUiState: TaskProgressUiState,
    modifier: Modifier = Modifier,
    onValueChange: (TaskProgressUiState) -> Unit = {},
    enabled: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {

// ----------------------- Inizio inserimento task ---------------------------------
        Box {
            var selected by remember { mutableStateOf(Pair("Key0",""))}
            var expanded by remember { mutableStateOf(false) } // initial value
            val list=listOf(Pair("Key1","Inglese"),Pair("Key2","Syncro"),Pair("Key3","Compiti"),Pair("Key4","Altro"))
            Column {
                 OutlinedTextField(
                    //    value = (selected.second),
                    value = taskProgressUiState.taskName,
                    onValueChange = {onValueChange(taskProgressUiState.copy(taskName = selected.second))},
                    label = { Text(stringResource(R.string.task_name_req)) },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                    readOnly = true
                )
                DropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    list.forEach { entry ->

                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                selected = entry
                                expanded = false
                                onValueChange(taskProgressUiState.copy(taskName = entry.second))
                            },
                            content = {
                                Text(
                                    text = (entry.second),
                                    modifier = Modifier
                                        .wrapContentWidth()
                                    //      .align(Alignment.Start)
                                )
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .background(androidx.compose.ui.graphics.Color.Transparent)
                    .padding(10.dp)
                    .clickable(
                        onClick = { expanded = !expanded }
                    )
            )
        }
// ----------------------- Fine inserimento task ---------------------------------
// ----------------------- Inizio inserimento subTask ---------------------------------
        Box {
            var selected by remember { mutableStateOf(Pair("Key0",""))}
            var expanded by remember { mutableStateOf(false) } // initial value
            var list: List<Pair<String, String>>
            when (taskProgressUiState.taskName) {
                "Inglese" -> {list=listOf(
                                        Pair("Key1","Speexx"),
                                        Pair("Key2","Libro in inglese"),
                                        Pair("Key3","Speakingathome"),
                                        Pair("Key4","Inglese con mamma"),
                                        Pair("Key4","Inglese con papà")
                                        )}
                "Syncro" -> {list=listOf(
                    Pair("Key1","Remata con pesi"),
                    Pair("Key2","Addominali"),
                    Pair("Key3","Flessioni con le braccia")
                    )}
                "Compiti" -> {list=listOf(
                    Pair("Key1","-")
                )}
                else -> { list=listOf(
                    Pair("Key1","Libro letto"),
                    Pair("Key2","Kangourou")
                    )
                }
            }

           Column {
                OutlinedTextField(
                    //    value = (selected.second),
                    value = taskProgressUiState.subTaskName,
                    onValueChange = {onValueChange(taskProgressUiState.copy(subTaskName = selected.second))},
                    label = { Text(stringResource(R.string.sub_task_name_req)) },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                    readOnly = true
                )
                DropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    list.forEach { entry ->

                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                selected = entry
                                expanded = false
                                onValueChange(taskProgressUiState.copy(subTaskName = entry.second))
                            },
                            content = {
                                Text(
                                    text = (entry.second),
                                    modifier = Modifier
                                        .wrapContentWidth()
                                    //      .align(Alignment.Start)
                                )
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .background(androidx.compose.ui.graphics.Color.Transparent)
                    .padding(10.dp)
                    .clickable(
                        onClick = { expanded = !expanded }
                    )
            )
        }
// ----------------------- Fine inserimento subTask ---------------------------------

        OutlinedTextField(
            value = taskProgressUiState.duration.toString(),
            onValueChange = {
                                if (it == "") {
                                    onValueChange(taskProgressUiState.copy(duration = ""))
                                }
                                else if (it.toDoubleOrNull() != null)
                                {
                                    onValueChange(taskProgressUiState.copy(duration = it))
                                } //
                                else {}

                            },
            label = { Text(stringResource(R.string.duration_req)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


// ----------------------- Inizio inserimento executionDate ---------------------------------
        // Fetching the Local Context
        val mContext = LocalContext.current

        // Declaring integer values
        // for year, month and day
        val mYear: Int
        val mMonth: Int
        val mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Declaring a string value to
        // store date in string format
        val mDate = remember { mutableStateOf("") }

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                if ((mMonth + 1) < 10) {
                    if (mDayOfMonth < 10) {
              //          mDate.value = "$mYear-0${mMonth + 1}-0$mDayOfMonth"
                        mDate.value = "0$mDayOfMonth-0${mMonth + 1}-$mYear"
                    } else mDate.value = "$mDayOfMonth-0${mMonth + 1}-$mYear"
                } else mDate.value = "$mDayOfMonth-${mMonth + 1}-$mYear"
                //    mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
                onValueChange(taskProgressUiState.copy(executionDate = "${mDate.value}"))
            }, mYear, mMonth, mDay
        )
        Column(
            modifier = Modifier
            //           .fillMaxSize(),
            //       verticalArrangement = Arrangement.Center
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val source = remember { MutableInteractionSource()  }
            OutlinedTextField(
                value = taskProgressUiState.executionDate,
                onValueChange = { onValueChange(taskProgressUiState.copy(executionDate = "${mDate.value}")) },
                label = { Text(text = "Data di esecuzione") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mDatePickerDialog.show() },
                interactionSource = source,

                )
            if ( source.collectIsPressedAsState().value) mDatePickerDialog.show()
        }
 //------------------fine inserimento executionDate---------------------------------


        OutlinedTextField(
            value = taskProgressUiState.note,
            onValueChange = { onValueChange(taskProgressUiState.copy(note = it)) },
            label = { Text(stringResource(R.string.note_req)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

/*
        OutlinedTextField(
            value = taskProgressUiState.taskExecutionEntrySaved.toString(),
            onValueChange = { },
            label = { Text(stringResource(R.string.note_req)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        */
    }
}

@Composable
fun visualizeTaskExecutionEntrySaved(
        modifier: Modifier=Modifier,
        taskExecutionList: List<TaskExecution>,
        taskExecutionEntrySaved: Boolean,
        onFABclick: () -> Unit,
) {
    if(taskExecutionEntrySaved) {
        if (taskExecutionList.isEmpty()) {
            Box(){
                Text(
                    text = stringResource(R.string.no_item_description),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        } else {
            Box(){
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text(
                        text = stringResource(R.string.esito_salvataggio),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TaskExecutionList(
                        taskExecutionList = taskExecutionList
                    )
                }
            }
            Box(Modifier.fillMaxSize()){
                FloatingActionButton(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .align(alignment = Alignment.BottomEnd),
                    onClick = onFABclick,
                    backgroundColor = Blu200,
                    //   contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "OK",
                        tint= Color.Green
                    )
                }
             }
        }
   }
}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    TaskProgress13Theme() {
        TaskExecutionEntryScreen(
            taskProgressUiState = TaskProgressUiState(
                taskName = "Inglese",
                subTaskName = "Speexx",
                duration="10",
                executionDate = "2023-02-08",
                executionDateUT = 0,
                note = ""
            ),
            onTaskExecutionValueChange = {},
            onSaveClick = {},
            onFABclick = {}
        )
    }
}