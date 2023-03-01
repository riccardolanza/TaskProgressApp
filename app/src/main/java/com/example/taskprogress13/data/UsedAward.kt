package com.example.taskprogress13.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "used_award",
    primaryKeys = ["award_name","date_of_use"]
)
data class UsedAward(
//    @PrimaryKey (autoGenerate = true)
//    val id: Int,
    @NonNull
    @ColumnInfo(name = "award_name")
    val awardName: String,
    @NonNull
    @ColumnInfo(name = "task_execution_minutes_needed")
    val taskExecutionMinutesNeeded: Int,
    @NonNull
    @ColumnInfo(name = "date_of_use")
    val dateOfUse: String,
    @NonNull
    @ColumnInfo(name = "date_of_use_ut")
    val dateOfUseUT: Long,
    @NonNull
    @ColumnInfo(name = "task_name")
    val taskName: String
)
