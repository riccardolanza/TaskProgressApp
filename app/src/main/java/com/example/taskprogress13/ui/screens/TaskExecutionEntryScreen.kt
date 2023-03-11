package com.example.taskprogress13.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskprogress13.R
import com.example.taskprogress13.data.TaskExecution
import com.example.taskprogress13.ui.components.TaskExecutionList
import com.example.taskprogress13.ui.theme.Blu200
import com.example.taskprogress13.ui.viewmodel.TaskProgressUiState
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel

import kotlinx.coroutines.launch

import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskExecutionEntryScreen(
 //   taskProgressUiState: TaskProgressUiState,
//    onTaskExecutionValueChange: (TaskProgressUiState) -> Unit,
//    onSaveClick: () -> Unit,
    taskName:String,
    onFABclick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory),
    context: Context
 ) {
    val taskProgressUiState = viewModel.taskProgressUiState
    val onTaskExecutionValueChange = viewModel::updateTaskProgressUiState
    val taskExecutionList by viewModel.getTaskExecutionFor_taskName_subTaskName_executionDate(taskProgressUiState.taskName, taskProgressUiState.subTaskName, taskProgressUiState.executionDate).collectAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current


    viewModel.updateUiStateTaskName(taskName=taskName)
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            //    .fillMaxWidth()
            .padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)

    ) {

        TaskExecutionInputForm(taskProgressUiState = taskProgressUiState, onValueChange = onTaskExecutionValueChange)
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()

        ){

            Box(){
                 Button(
                     modifier = Modifier.padding(10.dp),
                     onClick = {
                         coroutineScope.launch {
                             if (taskExecutionList.isEmpty()) {
                                 viewModel.saveTaskExecution()
                                 Toast.makeText(context, "Execuzione salvata!", Toast.LENGTH_SHORT).show()
                             }
                             else
                                 //Toast.makeText(context, "Questa esecuzione è già stata inserita!", Toast.LENGTH_SHORT).show()
                                 //visualizeNotSavedErrorMessageEnabled=true
                                 viewModel.updateUiStateVisualizeTaskExecutionNotSavedErrorMessageEnabled(true)
                        }
                         focusManager.clearFocus()
                     },
                     enabled = taskProgressUiState.actionEnabled && !taskProgressUiState.taskExecutionEntrySaved,
                 ) {
                     Text(
                         text=stringResource(R.string.save_action),
                         style = MaterialTheme.typography.body2
                     )
                 }
             }
            if(taskProgressUiState.taskExecutionEntrySaved) {
                sendWhatsAppMessage(context = context, taskExecutionList=taskExecutionList.toString())
            }

            if(taskProgressUiState.visualizeTaskExecutionNotSavedErrorMessageEnabled==true) visualizeNotSavedErrorMessage()
/*
            visualizeTaskExecutionEntrySaved(
                modifier = Modifier,
                taskExecutionList=taskExecutionList,
                taskExecutionEntrySaved=taskProgressUiState.taskExecutionEntrySaved,
                onFABclick=onFABclick,
                context = context
            )
            */
        }
    }
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskExecutionInputForm(
    taskProgressUiState: TaskProgressUiState,
    modifier: Modifier = Modifier,
    onValueChange: (TaskProgressUiState) -> Unit = {},
    enabled: Boolean = true
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {

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
                    label = { Text(text=stringResource(R.string.task_name_req),style = MaterialTheme.typography.body1) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(10.dp),
                    trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                    readOnly = true,
                    textStyle = MaterialTheme.typography.body1
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
                                    style = MaterialTheme.typography.body1,
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
                    .padding(5.dp)
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
            val list: List<Pair<String, String>>
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
                    label = { Text(text=stringResource(R.string.sub_task_name_req),style = MaterialTheme.typography.body1) },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                    readOnly = true,
                    textStyle = MaterialTheme.typography.body1
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
                                    style = MaterialTheme.typography.body1,
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
            label = { Text(text=stringResource(R.string.duration_req),style = MaterialTheme.typography.body1) },
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
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val source = remember { MutableInteractionSource()  }
            OutlinedTextField(
                value = taskProgressUiState.executionDate,
                onValueChange = {
                    onValueChange(taskProgressUiState.copy(executionDate = "${mDate.value}"))
                //    focusManager.clearFocus()
                     },
                label = { Text(text = "Data di esecuzione",style = MaterialTheme.typography.body1) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mDatePickerDialog.show() },
                interactionSource = source,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                )
            if ( source.collectIsPressedAsState().value) mDatePickerDialog.show()
        }
 //------------------fine inserimento executionDate---------------------------------


        OutlinedTextField(
            value = taskProgressUiState.note,
            onValueChange = { onValueChange(taskProgressUiState.copy(note = it)) },
            label = { Text(text=stringResource(R.string.note_req),style = MaterialTheme.typography.body1) },
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
fun visualizeNotSavedErrorMessage(
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        contentAlignment = Center
    ) {
        Text(
            text = "L'esecuzione non è stata salvata perchè ne è presente già una relativa a stessa attività, sotto-attività e giorno",
            style = MaterialTheme.typography.body1,
            color = Color.Red
        )
    }
}


@Composable
fun visualizeTaskExecutionEntrySaved(
        modifier: Modifier=Modifier,
        taskExecutionList: List<TaskExecution>,
        taskExecutionEntrySaved: Boolean,
        onFABclick: () -> Unit,
        context: Context
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
         //   Box(){
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text(
                        text = stringResource(R.string.esito_salvataggio),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TaskExecutionList(
                        taskExecutionList = taskExecutionList
                    )
                    sendWhatsAppMessage(context = context, taskExecutionList=taskExecutionList.toString())
                }
          //  }

    //

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


@Composable
fun sendWhatsAppMessage(
    context: Context,
    taskExecutionList: String
    ) {

    val phoneNumber = "3356331443"
    val message = "Ho inserito: $taskExecutionList"

    Button(
        onClick = { if (!isPackageInstalled(context.packageManager))
            Toast.makeText(context,"Whatsapp not installed",Toast.LENGTH_SHORT).show()
        else
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=$message")))
        },
        // on below line adding
        // a modifier for our button.
        modifier = Modifier
         //   .fillMaxWidth()
            .padding(10.dp)
    ) {
        // on below line adding a text for our button.
        Text(text = "Invia la notifica tramite WhatsApp",style = MaterialTheme.typography.body1)
    }
}

fun isPackageInstalled(packageManager: PackageManager): Boolean {
    return try {
        packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}


@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
        TaskExecutionEntryScreen(
            taskName = "Inglese",
/*
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
            */

            onFABclick = {},
            context = LocalContext.current
        )
    }
