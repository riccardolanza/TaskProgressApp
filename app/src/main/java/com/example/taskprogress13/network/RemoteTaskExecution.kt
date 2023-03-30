package com.example.taskprogress13.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RemoteTaskExecution(

    val id: Long,

    @SerialName(value = "taskName")
    val taskName: String,

    val subTaskName: String,

    val duration: Int,

    val executionDate: String,

    val executionDateUT: Long,

    val note: String
)
