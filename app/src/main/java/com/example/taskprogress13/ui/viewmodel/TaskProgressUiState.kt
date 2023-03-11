package com.example.taskprogress13.ui.viewmodel

import com.example.taskprogress13.data.TaskExecution
import java.text.SimpleDateFormat


/**
 * Represents Ui State for an Item.
 */
data class TaskProgressUiState(
 //   val id: Int = 0,
    val taskName: String = "",
    val subTaskName: String = "",
    val duration: String = "",
    val executionDate: String = "",
    val executionDateUT: Long = 0,
    val note: String = "",
    val actionEnabled: Boolean = false,
    val taskExecutionEntrySaved: Boolean = false,
    val visualizeTaskExecutionNotSavedErrorMessageEnabled: Boolean = false
)

/**
 * Extension function to convert [TaskProgressUiState] to [TaskExecution].
 **/
fun TaskProgressUiState.toTaskExecution(): TaskExecution = TaskExecution(
//    id = id,
    taskName = taskName,
    subTaskName = subTaskName,
    duration = duration.toInt(),
    executionDate = executionDate,
    executionDateUT = convertToUT(executionDate),
    note = note
)

fun convertToUT(executionDate: String): Long {
    val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    val date = format.parse("${executionDate} 00:00:00")
    val timestamp = date.time
    return timestamp
}


/**
 * Extension function to convert [TaskExecution] to [TaskProgressUiState]
 */
fun TaskExecution.toTaskProgressUiState(actionEnabled: Boolean = false): TaskProgressUiState = TaskProgressUiState(
//    id = id,
    taskName = taskName,
    subTaskName = subTaskName,
    duration = duration.toString(),
    executionDate = executionDate,
    executionDateUT = executionDateUT,
    note = note,
    actionEnabled = actionEnabled
)

fun TaskProgressUiState.isValid() : Boolean {
    return taskName.isNotBlank() && subTaskName.isNotBlank() && duration.isNotBlank() && executionDate.isNotBlank()
}

