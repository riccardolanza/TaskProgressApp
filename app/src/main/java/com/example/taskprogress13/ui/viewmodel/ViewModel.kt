package com.example.taskprogress13.ui.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taskprogress13.TaskProgressApplication
import kotlinx.coroutines.flow.Flow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.taskprogress13.data.*
import kotlinx.coroutines.flow.forEach
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskProgressViewModel(
    private val taskExecutionDao: TaskExecutionDao,
    private val awardDao: AwardDao,
    private val usedAwardDao: UsedAwardDao
    ): ViewModel() {

    /**
     * Holds current item ui state
     */
    var taskProgressUiState by mutableStateOf(TaskProgressUiState())
        private set
    var awardUiState by mutableStateOf(AwardUiState())
        private set
    var usedAwardUiState by mutableStateOf(UsedAwardUiState())
        private set

/* -------------------------------------- Inizio TaskExecution ---------------------------------- */
    fun getAllTaskExecutions(): Flow<List<TaskExecution>> = taskExecutionDao.getAll()

    fun getTaskExecutionForTaskName(taskName: String): Flow<List<TaskExecution>> =
        taskExecutionDao.getByTaskName(taskName)

    fun getTaskExecutionFor_taskName_subTaskName_executionDate(taskName: String, subTaskName: String, executionDate: String): Flow<List<TaskExecution>> =
        taskExecutionDao.getBy_taskName_subTaskName_executionDate(taskName, subTaskName, executionDate)

    fun getdurationSumByexecutionDateUTANDtaskName(min_executionDateUT:Long,max_executionDateUT:Long,taskName:String): Flow<Int> =
        taskExecutionDao.getdurationSumByexecutionDateUT(min_executionDateUT,max_executionDateUT,taskName)


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

/* -------------------------------------- Fine TaskExecution ------------------------------------ */
/* -------------------------------------- Inizio Award ------------------------------------------ */
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


    fun resetAwardUiState() {
        awardUiState = awardUiState.copy(
            awardName = "",
            taskExecutionMinutesNeeded = ""
        )
    }


/* -------------------------------------- Fine Award -------------------------------------------- */

/* -------------------------------------- Inizio UsedAward -------------------------------------- */
    fun getAllUsedAwards(): Flow<List<UsedAward>> = usedAwardDao.getAll()
    suspend fun saveUsedAward(usedAward:Award, dateOfUse: String, taskName: String) {
            usedAwardUiState = usedAwardUiState.copy(
                awardName=usedAward.awardName,
                dateOfUse = dateOfUse,
                taskExecutionMinutesNeeded = usedAward.taskExecutionMinutesNeeded.toString(),
                taskName = taskName
                )
            usedAwardDao.insert(usedAwardUiState.toUsedAward())
    }

    suspend fun deleteUsedAward(usedAward: UsedAward) {
        usedAwardDao.delete(usedAward)
    }

    fun getDurationSumByDateOfUseUTANDtaskName(min_dateOfUseUT:Long, max_dateOfUseUT:Long, taskName: String) : Flow<Int> =
        usedAwardDao.getDurationSumByDateOfUseUTANDtaskName(min_dateOfUseUT, max_dateOfUseUT, taskName)

    fun getUsedAwardsByTaskName(taskName: String): Flow<List<UsedAward>> =
    usedAwardDao.getUsedAwardsByTaskName(taskName)

/* -------------------------------------- Fine UsedAward -------------------------------------- */


    /**
     * Updates the [taskProgressUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */




    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskProgressApplication)
                TaskProgressViewModel(
                    application.database.taskExecutionDao(),
                    application.database.awardDao(),
                    application.database.usedAwardDao()
                )
            }
        }
    }
}

