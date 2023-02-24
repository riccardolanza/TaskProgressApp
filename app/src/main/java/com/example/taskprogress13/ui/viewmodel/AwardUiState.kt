package com.example.taskprogress13.ui.viewmodel

import com.example.taskprogress13.data.Award
import java.text.SimpleDateFormat


/**
 * Represents Ui State for an Award.
 */
data class AwardUiState(
    //   val id: Int = 0,
    val awardName: String = "",
    val taskExecutionMinutesNeeded: String = "",
    val actionEnabled: Boolean = false,
    val awardEntrySaved: Boolean = false
)


fun AwardUiState.toAward(): Award = Award(
//    id = id,
    awardName = awardName,
    taskExecutionMinutesNeeded = taskExecutionMinutesNeeded.toInt()
)

fun Award.toAwardUiState(actionEnabled: Boolean = false): AwardUiState = AwardUiState(
//    id = id,
    awardName = awardName,
    taskExecutionMinutesNeeded = taskExecutionMinutesNeeded.toString(),
    actionEnabled = actionEnabled
)

fun AwardUiState.isValid() : Boolean {
    return awardName.isNotBlank() && taskExecutionMinutesNeeded.isNotBlank()
}

