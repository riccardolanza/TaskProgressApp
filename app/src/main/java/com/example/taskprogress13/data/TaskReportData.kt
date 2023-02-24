package com.example.taskprogress13.data

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

data class TaskReportData(
    val total_duration: Int,
    val last30Days_duration: Int,
    val last7Days_duration: Int,
    val last30Days_progress: Int,
    val last7Days_progress: Int
)
