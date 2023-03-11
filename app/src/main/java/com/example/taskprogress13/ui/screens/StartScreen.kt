package com.example.taskprogress13.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskprogress13.R
import com.example.taskprogress13.data.TaskReportData
import com.example.taskprogress13.ui.components.transformTohhmm
import com.example.taskprogress13.ui.theme.AppTheme
import com.example.taskprogress13.ui.theme.Orientation
import com.example.taskprogress13.ui.theme.largeDimensions
import com.example.taskprogress13.ui.theme.mediumDimensions
import com.example.taskprogress13.ui.viewmodel.TaskProgressViewModel

@Composable
fun StartScreen(
    navigateToTaskExecutionEntryScreen: () -> Unit,
    navigateToAllTasksScreen: () -> Unit,
    navigateToAllEligibleAwardsScreen: () -> Unit,
    onDettagliButtonClicked: (String) -> Unit,
    navigateToUsedAwardsByTaskNameScreen:  (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if(AppTheme.orientation == Orientation.Portrait)
    {
        if (AppTheme.dimens==largeDimensions || AppTheme.dimens==mediumDimensions) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
       ) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .weight(4f)
            )
            {
                Column(
                    Modifier
                        //   .verticalScroll(rememberScrollState()
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        TaskCard(
                            taskImageId = R.drawable.inglese,
                            onButtonClicked = onDettagliButtonClicked,
                            navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                            navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                            taskName = "Inglese",
                            modifier = Modifier
                                .weight(1f)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        TaskCard(
                            taskImageId = R.drawable.syncro3,
                            onButtonClicked = onDettagliButtonClicked,
                            navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                            navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                            taskName = "Syncro",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.weight(1f)) {
                        TaskCard(
                            taskImageId = R.drawable.compiti1,
                            onButtonClicked = onDettagliButtonClicked,
                            navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                            navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                            taskName = "Compiti",
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        TaskCard(
                            taskImageId = R.drawable.attivita_varie,
                            onButtonClicked = onDettagliButtonClicked,
                            navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                            navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                            taskName = "Altro",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            /*
        Divider()
        Box(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth()
        )
        {
            Button(
                onClick = navigateToAllTasksScreen,
                modifier
                    .align(Alignment.Center)
                    .size(width = 300.dp, height = 35.dp)
            )
            { Text(
                text = "Visualizza tutte le attività inserite",
                textAlign = Center
            ) }
        }
        */
        }

    }
    else
        {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            TaskCard(
                taskImageId = R.drawable.inglese,
                onButtonClicked = onDettagliButtonClicked,
                navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                taskName = "Inglese",
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.height(10.dp))
            TaskCard(
                taskImageId = R.drawable.syncro3,
                onButtonClicked = onDettagliButtonClicked,
                navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                taskName = "Syncro",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(10.dp))
            TaskCard(
                taskImageId = R.drawable.compiti1,
                onButtonClicked = onDettagliButtonClicked,
                navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                taskName = "Compiti",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(10.dp))
            TaskCard(
                taskImageId = R.drawable.attivita_varie,
                onButtonClicked = onDettagliButtonClicked,
                navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                taskName = "Altro",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
} else {  // Orientation is Landscape
        Row(
            Modifier
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            TaskCard(
                taskImageId = R.drawable.inglese,
                onButtonClicked = onDettagliButtonClicked,
                navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                taskName = "Inglese",
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            TaskCard(
                taskImageId = R.drawable.syncro3,
                onButtonClicked = onDettagliButtonClicked,
                navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                taskName = "Syncro",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            TaskCard(
                taskImageId = R.drawable.compiti1,
                onButtonClicked = onDettagliButtonClicked,
                navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                taskName = "Compiti",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            TaskCard(
                taskImageId = R.drawable.attivita_varie,
                onButtonClicked = onDettagliButtonClicked,
                navigateToAllEligibleAwardsScreen = navigateToAllEligibleAwardsScreen,
                navigateToUsedAwardsByTaskNameScreen = navigateToUsedAwardsByTaskNameScreen,
                taskName = "Altro",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}


@Composable
private fun TaskCard(
    taskImageId:Int,
    onButtonClicked: (String) -> Unit,
    navigateToAllEligibleAwardsScreen: () -> Unit,
    navigateToUsedAwardsByTaskNameScreen: (String) -> Unit,
    taskName:String,
    modifier: Modifier = Modifier
){
    val taskImage = painterResource(taskImageId)
    val taskReportData = taskReport(taskName = taskName)

    Card(elevation=10.dp){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
         //   modifier = Modifier.padding(30.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text=taskName,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            //    modifier=Modifier.padding(top=10.dp)
           )
        //    Spacer(modifier = Modifier.height(10.dp))
            Box(
                contentAlignment = Alignment.Center
            ){
            Image(
                painter = taskImage,
                contentDescription = taskName,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(190.dp)
                    .padding(10.dp)
            )}

            Text(
                text = "Totale (7/30 gg)",
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${transformTohhmm(taskReportData.last7Days_duration.toString())} (${taskReportData.last7Days_progress}%)/${transformTohhmm(taskReportData.last30Days_duration.toString())} (${taskReportData.last30Days_progress}%)",
                style = MaterialTheme.typography.body1
            )

            Spacer(modifier = Modifier.height(10.dp))

            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colors.onBackground,fontWeight = FontWeight.Bold)) {
                    append("Al netto dei ")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colors.primary,fontWeight = FontWeight.Bold,)) {
                    append("premi fruiti")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colors.onBackground, fontWeight = FontWeight.Bold,)) {
                    append(" (7/30 gg)")
                }
            }

            ClickableText(
                text = annotatedString,
                onClick = {navigateToUsedAwardsByTaskNameScreen(taskName)},
                style = MaterialTheme.typography.body2
            )

            Text(
                text = "${transformTohhmm(taskReportData.last7Days_netDuration.toString())} (${taskReportData.last7Days_netProgress}%)/${transformTohhmm(taskReportData.last30Days_netDuration.toString())} (${taskReportData.last30Days_netProgress}%)",
                style = MaterialTheme.typography.body1
            )

            Spacer(modifier = Modifier.height(10.dp))

            val infiniteTransition = rememberInfiniteTransition()
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Reverse
                )
            )
            val awardIsEligible= (taskReportData.last7Days_netProgress >=100 || taskReportData.last30Days_netProgress >=100)
            IconButton(
                onClick = {
                    if (awardIsEligible) {
                        navigateToAllEligibleAwardsScreen()
                    } else {}
                          },
                modifier = Modifier
                    .size(60.dp)
                    .scale(scale)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.workspace_premium_black_24dp),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = if (awardIsEligible) Color(0xFFD2AC47) else Color.Transparent
                )
            }

 //           Spacer(modifier = Modifier.height(20.dp))
            Box(modifier=Modifier.padding(horizontal = 110.dp, vertical = 10.dp)) {
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
    var taskReportData: TaskReportData = TaskReportData(0,0,0,0,0, 0, 0, 0, 0)
    val dailyTargetInMinutes=10

// UT variables
    val currentUT = System.currentTimeMillis()
    val _8DaysAgoUT= currentUT - 691200000 // per poter prendere in considerazione anche la parte di giornata iniziale
     val _31DaysAgoUT = currentUT - 2678400000 // per poter prendere in considerazione anche la parte di giornata iniziale

//total durations
    val last7Days_duration by viewModel.getdurationSumByexecutionDateUTANDtaskName(min_executionDateUT=_8DaysAgoUT,max_executionDateUT=currentUT, taskName = "$taskName").collectAsState(0)
    val last30Days_duration by viewModel.getdurationSumByexecutionDateUTANDtaskName(min_executionDateUT=_31DaysAgoUT,max_executionDateUT=currentUT, taskName = "$taskName").collectAsState(0)
    val total_duration by viewModel.getdurationSumByexecutionDateUTANDtaskName(min_executionDateUT=0,max_executionDateUT=currentUT, taskName = "$taskName").collectAsState(0)

//progresses
    val last7Days_progress = 100*last7Days_duration/(7*dailyTargetInMinutes)
    val last30Days_progress = 100*last30Days_duration/(30*dailyTargetInMinutes)

//Awards durations
    val last7Days_awardDuration by viewModel.getDurationSumByDateOfUseUTANDtaskName(min_dateOfUseUT=_8DaysAgoUT,max_dateOfUseUT=currentUT, taskName = "$taskName").collectAsState(0)
    val last30Days_awardDuration by viewModel.getDurationSumByDateOfUseUTANDtaskName(min_dateOfUseUT=_31DaysAgoUT,max_dateOfUseUT=currentUT, taskName = "$taskName").collectAsState(0)

//netDurations
    val last7Days_netDuration = last7Days_duration - last7Days_awardDuration
    val last30Days_netDuration = last30Days_duration - last30Days_awardDuration
//net progresses
    val last7Days_netProgress = 100*(last7Days_netDuration)/(7*dailyTargetInMinutes)
    val last30Days_netProgress = 100*(last30Days_netDuration)/(30*dailyTargetInMinutes)

    taskReportData = TaskReportData(total_duration,last30Days_duration, last7Days_duration,last30Days_progress,last7Days_progress,last7Days_netDuration,last30Days_netDuration,last30Days_netProgress,last7Days_netProgress)
//    println("taskReportData: $taskReportData")
    return taskReportData

}


@Preview
@Composable
fun StartScreenPreview(){
    StartScreen(
        //navigateToEnglishTasksScreen = {},
        navigateToTaskExecutionEntryScreen={},
        navigateToAllTasksScreen = {},
        onDettagliButtonClicked = {},
        navigateToAllEligibleAwardsScreen = {},
        navigateToUsedAwardsByTaskNameScreen={},
        modifier = Modifier
    )
}