package com.example.taskprogress13.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskprogress13.data.Award
import com.example.taskprogress13.data.TaskExecution

@Composable
fun AdministrationScreen(
    //awardList:List<Award>,
    onAllAvailableAwardsButtonClick: () -> Unit,
    onAllUsedAwardsButtonClick: () -> Unit
)
{
    val contextForToast = LocalContext.current.applicationContext
    Column() {
        Box(modifier = Modifier.weight(0.3f).fillMaxWidth())
        {
            Button(
                onClick = onAllAvailableAwardsButtonClick,
                modifier = Modifier.align(Alignment.Center).size(width = 300.dp, height = 35.dp)
            )
            {Text(
                    text = "Visualizza i premi disponibili",
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(modifier = Modifier.weight(0.3f).fillMaxWidth())
        {
            Button(
                onClick = onAllUsedAwardsButtonClick,
                modifier = Modifier.align(Alignment.Center).size(width = 300.dp, height = 35.dp)
            )
            {Text(
                text = "Visualizza i premi utilizzati",
                textAlign = TextAlign.Center
            )
            }
        }
    }
}


@Preview
@Composable
fun AdministrationScreenPreview(){
    AdministrationScreen(
        /*
        awardList= arrayListOf(
            Award(awardName="Film", taskExecutionMinutesNeeded=30,),
            Award(awardName="Episodio Serie TV", taskExecutionMinutesNeeded=15)
        ),*/
        onAllAvailableAwardsButtonClick={},
        onAllUsedAwardsButtonClick={}
    )
}