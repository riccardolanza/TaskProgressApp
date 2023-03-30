package com.example.taskprogress13.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.*


/**
 * Represents a single table in the database. Each row is a separate instance of
 * the BusSchedule class. Each property corresponds to a column.
 * Additionally, an ID is needed as a unique identifier for
 * each row in the database.
 */
@Serializable
@Entity(
    tableName = "task_execution",
    primaryKeys = ["task_name","sub_task_name","execution_date"]
)
data class TaskExecution(
//    @PrimaryKey (autoGenerate = true)
    val id: Long,

    @NonNull
    @ColumnInfo(name = "task_name")
    val taskName: String,
    @NonNull
    @ColumnInfo(name = "sub_task_name")
    val subTaskName: String,
    @NonNull
    @ColumnInfo(name = "duration")
    val duration: Int,
    @NonNull
    @ColumnInfo(name = "execution_date")
    val executionDate: String,
    @NonNull
    @ColumnInfo(name = "execution_date_ut")
    val executionDateUT: Long,
//    @NonNull
//    @ColumnInfo(name = "executed")
//    val executed: String,
    @ColumnInfo(name = "note")
    val note: String
)
