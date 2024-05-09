package com.example.taskprogress13.data

import com.example.taskprogress13.network.NetworkResult
import com.example.taskprogress13.network.RemoteTaskExecution
import com.example.taskprogress13.network.TaskProgressApi
import com.example.taskprogress13.network.handleApi
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path

interface RemoteTaskExecutionRepository {
 //suspend fun getAllRemoteTaskExecutionsResponse(): Response<List<TaskExecution>>
 suspend fun invokeResponse(): Response<List<TaskExecution>>
 suspend fun getAllRemoteTaskExecutions(): List<TaskExecution>
 suspend fun getRemoteTaskExecutionsByTaskName(taskName:String): List<TaskExecution>
 suspend fun getRemoteTaskExecutionsBy_taskName_subTaskName_executionDate(taskName: String,subTaskName: String,executionDate: String): List<TaskExecution>
 suspend fun saveRemoteTaskExecution(taskExecution: TaskExecution)
 suspend fun deleteRemoteTaskExecution(id: Long);
 suspend fun getRemoteDurationSumByexecutionDateUTANDtaskName(taskName: String, minExecutiondateut: Long, maxExecutiondateut: Long): Int

}


class DefaultRemoteTaskExecutionRepository: RemoteTaskExecutionRepository{
    suspend operator fun invoke(): NetworkResult<List<TaskExecution>> =
        handleApi { TaskProgressApi.retrofitService.getAllRemoteTaskExecutionsResponse() }
    override suspend fun invokeResponse(): Response<List<TaskExecution>> =
        TaskProgressApi.retrofitService.getAllRemoteTaskExecutionsResponse()

      override suspend fun getAllRemoteTaskExecutions(): List<TaskExecution> {
            return TaskProgressApi.retrofitService.getAllRemoteTaskExecutions()
    }

    override suspend fun getRemoteTaskExecutionsByTaskName(taskName:String): List<TaskExecution> {
        println("getRemoteTaskExecutionsByTaskName(taskName:String) - sono quì")
        return TaskProgressApi.retrofitService.getRemoteTaskExecutionsByTaskName(taskName)
    }

    override suspend fun getRemoteTaskExecutionsBy_taskName_subTaskName_executionDate(
    taskName: String,
    subTaskName: String,
    executionDate: String
    ): List<TaskExecution> {
        return TaskProgressApi.retrofitService.getRemoteTaskExecutionsBy_taskName_subTaskName_executionDate(
            taskName,
            subTaskName,
            executionDate
        )
    }

    override suspend fun saveRemoteTaskExecution(taskExecution: TaskExecution){
        //TaskProgressApi.retrofitService.saveTaskExecution(Gson().toJson(taskExecution).toString().replace("\\", ""))
        TaskProgressApi.retrofitService.saveTaskExecution(taskExecution)
    }

    override suspend fun deleteRemoteTaskExecution(id: Long){
        TaskProgressApi.retrofitService.deleteTaskExecution(id)
    }

    override suspend fun getRemoteDurationSumByexecutionDateUTANDtaskName(taskName: String, minExecutiondateut: Long, maxExecutiondateut: Long): Int {
        return TaskProgressApi.retrofitService.getRemoteDurationSumByexecutionDateUTANDtaskName(taskName,minExecutiondateut,maxExecutiondateut)
    }

}

