package com.example.taskprogress13.ui.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taskprogress13.TaskProgressApplication
import com.example.taskprogress13.data.TaskExecution
import com.example.taskprogress13.data.TaskExecutionDao
import kotlinx.coroutines.flow.Flow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.taskprogress13.data.Award
import com.example.taskprogress13.data.AwardDao
import kotlinx.coroutines.flow.forEach

class TaskProgressViewModel(
    private val taskExecutionDao: TaskExecutionDao,
    private val awardDao: AwardDao
    ): ViewModel() {

    fun getAllTaskExecutions(): Flow<List<TaskExecution>> = taskExecutionDao.getAll()

    fun getTaskExecutionForTaskName(taskName: String): Flow<List<TaskExecution>> =
        taskExecutionDao.getByTaskName(taskName)

    fun getTaskExecutionFor_taskName_subTaskName_executionDate(taskName: String, subTaskName: String, executionDate: String): Flow<List<TaskExecution>> =
        taskExecutionDao.getBy_taskName_subTaskName_executionDate(taskName, subTaskName, executionDate)

    fun getdurationSumByexecutionDateUTANDtaskName(min_executionDateUT:Long,max_executionDateUT:Long,taskName:String): Flow<Int> =
        taskExecutionDao.getdurationSumByexecutionDateUT(min_executionDateUT,max_executionDateUT,taskName)

    fun getAllAwards(): Flow<List<Award>> = awardDao.getAll()

    fun getByTaskExecutionMinutesNeeded(taskExecutionMinutesNeeded: Int): Flow<List<Award>> =
        awardDao.getByTaskExecutionMinutesNeeded(taskExecutionMinutesNeeded)

    fun getByAwardName(awardName: String): Flow<List<Award>> =
        awardDao.getByAwardName(awardName)

    suspend fun deleteAward(award: Award) {
        awardDao.delete(award)
    }

    suspend fun saveAward() {
        if (awardUiState.isValid()) {
            awardDao.insert(awardUiState.toAward())
            awardUiState = awardUiState.copy(awardEntrySaved = true)
        }
    }

    fun updateAwardUiState(newAwardUiState: AwardUiState) {
        awardUiState = newAwardUiState.copy( actionEnabled = newAwardUiState.isValid())
    }
    /**
     * Holds current item ui state
     */
    var taskProgressUiState by mutableStateOf(TaskProgressUiState())
        private set
    var awardUiState by mutableStateOf(AwardUiState())
        private set

    /**
     * Updates the [taskProgressUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateTaskProgressUiState(newTaskProgressUiState: TaskProgressUiState) {
        taskProgressUiState = newTaskProgressUiState.copy( actionEnabled = newTaskProgressUiState.isValid())
    }

    fun updateUiStateTaskName(taskName:String) {
        taskProgressUiState = taskProgressUiState.copy( taskName = taskName)
    }

    suspend fun deleteTaskExecution(taskExecution: TaskExecution) {
        taskExecutionDao.delete(taskExecution)
    }

    suspend fun saveTaskExecution() {
        if (taskProgressUiState.isValid()) {
            taskExecutionDao.insert(taskProgressUiState.toTaskExecution())
            taskProgressUiState = taskProgressUiState.copy(taskExecutionEntrySaved = true)
        }
        //  taskProgressUiState.taskExecutionEntrySaved()
    }

    fun resetTaskProgressUiState() {
        taskProgressUiState = taskProgressUiState.copy(
            taskName = "",
            subTaskName = "",
            duration = "",
            executionDate = "",
            note = "",
            actionEnabled = false,
            taskExecutionEntrySaved = false,
            )
    }

    fun resetAwardUiState() {
        awardUiState = awardUiState.copy(
            awardName = "",
            taskExecutionMinutesNeeded = ""
        )
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskProgressApplication)
                TaskProgressViewModel(
                    application.database.taskExecutionDao(),
                    application.database.awardDao()
                )
            }
        }
    }
}

