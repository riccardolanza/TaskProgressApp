package com.example.taskprogress13

import android.app.Application
import com.example.taskprogress13.data.AppDatabase


class TaskProgressApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
