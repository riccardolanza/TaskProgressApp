package com.example.taskprogress13.ui.viewmodel

import android.database.sqlite.SQLiteException
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taskprogress13.TaskProgressApplication
import kotlinx.coroutines.flow.Flow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.taskprogress13.DATABASE
import com.example.taskprogress13.data.*
import com.example.taskprogress13.network.TaskProgressApi
import com.example.taskprogress13.network.TaskProgressApiService
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


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
    var remoteTaskProgressUiState by mutableStateOf(RemoteTaskProgressUiState())
        private set
    var remoteTaskExecutionListUiState: RemoteTaskExecutionListUiState by mutableStateOf(
        RemoteTaskExecutionListUiState.Loading
    )
        private set

    /* -------------------------------------- Inizio TaskExecution ---------------------------------- */
    fun getAllTaskExecutions(): Flow<List<TaskExecution>> = taskExecutionDao.getAll()

    fun getTaskExecutionsByTaskName(taskName: String): Flow<List<TaskExecution>> =
        taskExecutionDao.getByTaskName(taskName)

    fun getTaskExecutionsBy_taskName_subTaskName_executionDate(
        taskName: String,
        subTaskName: String,
        executionDate: String
    ): Flow<List<TaskExecution>> =
                    taskExecutionDao.getBy_taskName_subTaskName_executionDate(
                taskName,
                subTaskName,
                executionDate
            )

    fun getdurationSumByexecutionDateUTANDtaskName(min_executionDateUT:Long,max_executionDateUT:Long,taskName:String): Flow<Int> =
        taskExecutionDao.getdurationSumByexecutionDateUT(min_executionDateUT,max_executionDateUT,taskName)

    fun updateTaskProgressUiState(newTaskProgressUiState: TaskProgressUiState) {
        taskProgressUiState = newTaskProgressUiState.copy( actionEnabled = newTaskProgressUiState.isValid())
    }

    fun updateUiStateTaskName(taskName:String) {
        taskProgressUiState = taskProgressUiState.copy( taskName = taskName)
    }

    fun updateUiStateVisualizeTaskExecutionNotSavedErrorMessageEnabled(value:Boolean) {
        taskProgressUiState = taskProgressUiState.copy(visualizeTaskExecutionNotSavedErrorMessageEnabled=value)
    }
/*
    suspend fun deleteTaskExecution(taskExecution: TaskExecution) {
        taskExecutionDao.delete(taskExecution)
    }
*/
    suspend fun saveTaskExecution() {
        if (taskProgressUiState.isValid()) {
            try{
                if (DATABASE == "local") {
                    taskExecutionDao.insert(taskProgressUiState.toTaskExecution())
                }
                else {
                    val remoteTaskExecutionRepository = DefaultRemoteTaskExecutionRepository()
                    remoteTaskExecutionRepository.saveRemoteTaskExecution(taskProgressUiState.toTaskExecution())
                }
                taskProgressUiState = taskProgressUiState.copy(taskExecutionEntrySaved = true)
                taskProgressUiState = taskProgressUiState.copy(visualizeTaskExecutionNotSavedErrorMessageEnabled=false)
            } catch (e: SQLiteException){println("errore Insert: " + e)}

        }

    }

    suspend fun deleteTaskExecution(taskExecution: TaskExecution) {
        println("Invocata deleteTaskExecution con taskExecution: ${taskExecution}")
        try{
            if (DATABASE == "local") {
                println("Invocata deleteTaskExecution locale con taskExecution: ${taskExecution}")
                taskExecutionDao.delete(taskExecution)
            }
            else {
                println("Invocata deleteTaskExecution remota con id: ${taskExecution.id}")
                val remoteTaskExecutionRepository = DefaultRemoteTaskExecutionRepository()
                remoteTaskExecutionRepository.deleteRemoteTaskExecution(taskExecution.id)
            }
        } catch (e: SQLiteException){println("errore Insert: " + e)}
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
/*
    init {
        getAllRemoteTaskExecutions()
    }

    fun getAllRemoteTaskExecutions() {
        viewModelScope.launch {
            remoteTaskExecutionListUiState = RemoteTaskExecutionListUiState.Loading
            remoteTaskExecutionListUiState = try {
                //RemoteTaskExecutionListUiState.Success(TaskProgressApi.retrofitService.getRemoteTaskExecutions())
                val remoteTaskExecutionRepository = DefaultRemoteTaskExecutionRepository()
                RemoteTaskExecutionListUiState.Success(remoteTaskExecutionRepository.getAllRemoteTaskExecutions())

            } catch (e: IOException) {
                RemoteTaskExecutionListUiState.Error
            } catch (e: HttpException) {
                RemoteTaskExecutionListUiState.Error
            }
        }
    }
*/
    fun getRemoteTaskExecutionsByTaskName(taskName: String) {
        viewModelScope.launch {
            remoteTaskExecutionListUiState = RemoteTaskExecutionListUiState.Loading
            remoteTaskExecutionListUiState = try {
                val remoteTaskExecutionRepository = DefaultRemoteTaskExecutionRepository()
                RemoteTaskExecutionListUiState.Success(remoteTaskExecutionRepository.getRemoteTaskExecutionsByTaskName(taskName))
            } catch (e: IOException) {
                RemoteTaskExecutionListUiState.Error
            } catch (e: HttpException) {
                RemoteTaskExecutionListUiState.Error
            }
        }
    }

    fun getRemoteTaskExecutionsBy_taskName_subTaskName_executionDate(
        taskName: String,
        subTaskName: String,
        executionDate: String
    ) {
        viewModelScope.launch {
         //   remoteTaskExecutionListUiState = RemoteTaskExecutionListUiState.Loading
            remoteTaskExecutionListUiState = try {
                val remoteTaskExecutionRepository = DefaultRemoteTaskExecutionRepository()
                RemoteTaskExecutionListUiState.Success(remoteTaskExecutionRepository.getRemoteTaskExecutionsBy_taskName_subTaskName_executionDate(taskName,subTaskName,executionDate))
       } catch (e: IOException) {
                RemoteTaskExecutionListUiState.Error
            } catch (e: HttpException) {
                RemoteTaskExecutionListUiState.Error
            }
        }
    }






    suspend fun saveRemoteTaskExecution() {
        if (taskProgressUiState.isValid()) {
            try{
                val remoteTaskExecutionRepository = DefaultRemoteTaskExecutionRepository()
                remoteTaskExecutionRepository.saveRemoteTaskExecution(taskProgressUiState.toTaskExecution())
                taskProgressUiState = taskProgressUiState.copy(taskExecutionEntrySaved = true)
                taskProgressUiState = taskProgressUiState.copy(visualizeTaskExecutionNotSavedErrorMessageEnabled=false)
            } catch (e: SQLiteException){println("errore Insert: " + e)}

        }
        //  taskProgressUiState.taskExecutionEntrySaved()
    }




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

