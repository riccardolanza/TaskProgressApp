package com.example.taskprogress13.ui.viewmodel

import com.example.taskprogress13.data.UsedAward

import com.example.taskprogress13.data.Award
import com.example.taskprogress13.data.TaskExecution
import java.text.SimpleDateFormat


/**
 * Represents Ui State for an Award.
 */
data class UsedAwardUiState(
    //   val id: Int = 0,
    val awardName: String = "",
    val taskExecutionMinutesNeeded: String = "",
    val dateOfUse: String = "",
    val dateOfUseUT: Long = 0,
    val taskName: String = "",
    val actionEnabled: Boolean = false,
    val awardEntrySaved: Boolean = false
)


fun UsedAwardUiState.toUsedAward(): UsedAward = UsedAward(
//    id = id,
    awardName = awardName,
    taskExecutionMinutesNeeded = taskExecutionMinutesNeeded.toInt(),
    dateOfUse = dateOfUse,
    dateOfUseUT = convertFromDateHHmmssToUT(dateOfUse),
    taskName = taskName
)

fun UsedAward.toUsedAwardUiState(actionEnabled: Boolean = false): UsedAwardUiState = UsedAwardUiState(
//    id = id,
    awardName = awardName,
    taskExecutionMinutesNeeded = taskExecutionMinutesNeeded.toString(),
    dateOfUse = dateOfUse,
    taskName = taskName,
    actionEnabled = actionEnabled
)

fun convertFromDateHHmmssToUT(dateOfUse: String): Long {
    val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    val date = format.parse("${dateOfUse}")
    val timestamp = date.time
    return timestamp
}