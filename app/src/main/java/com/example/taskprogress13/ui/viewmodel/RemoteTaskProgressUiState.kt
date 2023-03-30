package com.example.taskprogress13.ui.viewmodel

import com.example.taskprogress13.data.TaskExecution
import com.example.taskprogress13.network.RemoteTaskExecution
import kotlinx.coroutines.flow.Flow


sealed interface RemoteTaskExecutionListUiState {
    data class Success(
        val remoteTaskExecutions: List<TaskExecution>
        //val taskExecutions: String
    ) : RemoteTaskExecutionListUiState
    object Error : RemoteTaskExecutionListUiState
    object Loading : RemoteTaskExecutionListUiState
}



/**
 * Represents Ui State for an Item.
 */
data class RemoteTaskProgressUiState(
    val id: Long = 0,
    val taskName: String = "",
    val subTaskName: String = "",
    val duration: String = "",
    val executionDate: String = "",
    val executionDateUT: Long = 0,
    var note: String = "",
    val actionEnabled: Boolean = false,
    val taskExecutionEntrySaved: Boolean = false,
    val visualizeTaskExecutionNotSavedErrorMessageEnabled: Boolean = false
)

/**
 * Extension function to convert [TaskProgressUiState] to [TaskExecution].
 **/
fun RemoteTaskProgressUiState.toTaskExecution(): TaskExecution = TaskExecution(
    id = id,
    taskName = taskName,
    subTaskName = subTaskName,
    duration = duration.toInt(),
    executionDate = executionDate,
    executionDateUT = convertToUT(executionDate),
    note = note
)

/**
 * Extension function to convert [TaskExecution] to [RemoteTaskProgressUiState]
 */
fun TaskExecution.toRemoteTaskProgressUiState(actionEnabled: Boolean = false): RemoteTaskProgressUiState = RemoteTaskProgressUiState(
    id = id,
    taskName = taskName,
    subTaskName = subTaskName,
    duration = duration.toString(),
    executionDate = executionDate,
    executionDateUT = executionDateUT,
    note = note,
    actionEnabled = actionEnabled
)

fun RemoteTaskProgressUiState.isValid() : Boolean {
    return taskName.isNotBlank() && subTaskName.isNotBlank() && duration.isNotBlank() && executionDate.isNotBlank()
}

