package com.example.taskprogress13.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = arrayOf(TaskExecution::class), version = 8)
@Database(entities = [(TaskExecution::class),(Award::class),(UsedAward::class)], version = 12)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskExecutionDao(): TaskExecutionDao
    abstract fun awardDao(): AwardDao
    abstract fun usedAwardDao(): UsedAwardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
              //      .createFromAsset("database/tasks.db")
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}
