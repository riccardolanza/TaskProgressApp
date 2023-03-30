@file:OptIn(ExperimentalSerializationApi::class)

package com.example.taskprogress13.network

import com.example.taskprogress13.data.TaskExecution
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
//import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL =
    //"https://android-kotlin-fun-mars-server.appspot.com"
     //"http://localhost:8080"
    "http://192.168.1.253:8080"

private val retrofit = Retrofit.Builder()
  //  .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .addConverterFactory(GsonConverterFactory.create())
 //   .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface TaskProgressApiService {
    //@GET("photos")

    @GET("taskexecutions")
    //suspend fun getRemoteTaskExecutions(): List<RemoteTaskExecution>
    suspend fun getAllRemoteTaskExecutions(): List<TaskExecution>

    @GET("taskexecutions/{taskName}")
    suspend fun getRemoteTaskExecutionsByTaskName(@Path("taskName") taskName : String): List<TaskExecution>

    @GET("taskexecutions")
    suspend fun getRemoteTaskExecutionsBy_taskName_subTaskName_executionDate(@Query("taskName") taskName : String, @Query("subTaskName") subTaskName : String, @Query("executionDate") executionDate : String): List<TaskExecution>

    @POST("savetaskexecution")
    //suspend fun saveTaskExecution(@Body taskExecution: String):Call<String>
    suspend fun saveTaskExecution(@Body taskExecution: TaskExecution)

    @DELETE("deletetaskexecution/{id}")
    suspend fun deleteTaskExecution(@Path("id") id: Long);

}

object TaskProgressApi {
    val retrofitService : TaskProgressApiService by lazy {
        retrofit.create(TaskProgressApiService::class.java)
    }
}

