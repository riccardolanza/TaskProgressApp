package com.example.taskprogress13.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "award",
    primaryKeys = ["award_name"]
)
data class Award(
//    @PrimaryKey (autoGenerate = true)
//    val id: Int,
    @NonNull
    @ColumnInfo(name = "award_name")
    val awardName: String,
    @NonNull
    @ColumnInfo(name = "task_execution_minutes_needed")
    val taskExecutionMinutesNeeded: Int
)
