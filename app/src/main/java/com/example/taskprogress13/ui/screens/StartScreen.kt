package com.example.taskprogress13.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskprogress13.R
import com.example.taskprogress13.data.Award
import com.example.taskprogress13.data.TaskReportData
import com.example.taskprogress13.ui.components.transformTohhmm
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StartScreen(
    navigateToTaskExecutionEntryScreen: () -> Unit,
    navigateToAllTasksScreen: () -> Unit,
    onButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
            .weight(4f)
        )
        {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    TaskCard(
                        taskImageId = R.drawable.inglese,
                        taskDescription = "Inglese",
                        taskReportData=taskReport(taskName = "Inglese"),
                        onButtonClicked = onButtonClicked,
                        taskName="Inglese",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(40.dp))
                    TaskCard(
                        taskImageId = R.drawable.syncro3,
                        taskDescription = "Nuoto sincronizzato",
                        taskReportData=taskReport(taskName = "Syncro"),
                        onButtonClicked = onButtonClicked,
                        taskName="Syncro",
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.weight(1f)) {
                    TaskCard(
                        taskImageId = R.drawable.compiti1,
                        taskDescription = "Compiti",
                        taskReportData=taskReport(taskName = "Compiti"),
                        onButtonClicked = onButtonClicked,
                        taskName="Compiti",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(40.dp))
                    TaskCard(
                        taskImageId = R.drawable.attivita_varie,
                        taskDescription = "Altre attività",
                        taskReportData=taskReport(taskName = "Altro"),
                        onButtonClicked = onButtonClicked,
                        taskName="Altro",
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        Divider()
        /*
        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
        )
        {
            Button(
                onClick = navigateToTaskExecutionEntryScreen,
                modifier
                    .align(Alignment.Center)
                    .size(width = 300.dp,height = 35.dp)
            )
            { Text(text = "Inserisci nuovo task") }
        }*/
        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
        )
        {
            Button(
                onClick = navigateToAllTasksScreen,
                modifier
                    .align(Alignment.Center)
                    .size(width = 300.dp,height = 35.dp)
            )
            { Text(
                text = "Visualizza tutte le attività inserite",
                textAlign = Center
            ) }
        }
    }


}



@Composable
private fun TaskCard(
    taskImageId:Int,
    taskDescription: String,
    taskReportData: TaskReportData,
    onButtonClicked: (String) -> Unit,
    taskName:String,
    modifier: Modifier = Modifier
){
    val taskImage = painterResource(taskImageId)
    //val taskProgressTer = englishProgress(timeIntervalInDays = 7)
    Card(elevation=10.dp){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
         //   modifier = Modifier.padding(30.dp)
        ) {
            Text(
                text=taskName,
                style = MaterialTheme.typography.h1,
                modifier=Modifier.padding(top=10.dp)
           )
        //    Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = taskImage,
                contentDescription = taskDescription,
                modifier = Modifier
                    .size(190.dp)
                    .padding(10.dp)
            )
            Text(
                text = "Totale minuti: ${transformTohhmm(taskReportData.total_duration.toString())}",
                fontSize = 15.sp
            )
            Text(
                text = "Ultimi 7 giorni: ${transformTohhmm(taskReportData.last7Days_duration.toString())} (${taskReportData.last7Days_progress}%)",
                fontSize = 15.sp
            )
            Text(
                text = "Ultimi 30 giorni: ${transformTohhmm(taskReportData.last30Days_duration.toString())} (${taskReportData.last30Days_progress}%)",
                fontSize = 15.sp
            )
            Box()
            {
                Row(){
                    Image(
                        painter = painterResource(R.drawable.trofeo_3),
                        contentDescription = "Premio",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "-"
                    )
                }
            }
 //           Spacer(modifier = Modifier.height(20.dp))
            Box(modifier=Modifier.padding(horizontal = 100.dp, vertical = 10.dp)) {
                Button(onClick = { onButtonClicked(taskName) }) { Text(text = "dettagli") }
            }
        }
    }
}

@Composable
fun taskReport(
    viewModel: TaskProgressViewModel = viewModel(factory = TaskProgressViewModel.factory),
    taskName: String
) : TaskReportData {
    var taskReportData: TaskReportData = TaskReportData(0,0,0,0,0)
    val dailyTargetInMinutes=10

// UT variables
    val currentUT = System.currentTimeMillis()
    val _8DaysAgoUT= currentUT - 691200000 // per poter prendere in considerazione anche la parte di giornata iniziale
     val _31DaysAgoUT = currentUT - 2678400000 // per poter prendere in considerazione anche la parte di giornata iniziale

//durations
    val last7Days_duration by viewModel.getdurationSumByexecutionDateUTANDtaskName(min_executionDateUT=_8DaysAgoUT,max_executionDateUT=currentUT, taskName = "$taskName").collectAsState(0)
    val last30Days_duration by viewModel.getdurationSumByexecutionDateUTANDtaskName(min_executionDateUT=_31DaysAgoUT,max_executionDateUT=currentUT, taskName = "$taskName").collectAsState(0)
    val total_duration by viewModel.getdurationSumByexecutionDateUTANDtaskName(min_executionDateUT=0,max_executionDateUT=currentUT, taskName = "$taskName").collectAsState(0)

//progresses
    val last7Days_progress = 100*last7Days_duration/(7*dailyTargetInMinutes)
    val last30Days_progress = 100*last30Days_duration/(30*dailyTargetInMinutes)


    taskReportData = TaskReportData(total_duration,last30Days_duration, last7Days_duration,last30Days_progress,last7Days_progress)
//    println("taskReportData: $taskReportData")
    return taskReportData

}

@Composable
fun checkEntitledAwards(
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

@Preview
@Composable
fun StartScreenPreview(){
    StartScreen(
        //navigateToEnglishTasksScreen = {},
        navigateToTaskExecutionEntryScreen={},
        navigateToAllTasksScreen = {},
        onButtonClicked = { },
        modifier = Modifier
    )
}