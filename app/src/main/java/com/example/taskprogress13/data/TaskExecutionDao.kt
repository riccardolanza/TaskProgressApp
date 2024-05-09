package com.example.taskprogress13.data



import android.database.sqlite.SQLiteException
import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Provides access to read/write operations on the schedule table.
 * Used by the view models to format the query results for use in the UI.
 */
@Dao
interface TaskExecutionDao {
    @Query(
        """
        SELECT * FROM task_execution 
        ORDER BY execution_date_ut DESC    
        """
    )
    fun getAll(): Flow<List<TaskExecution>>

    @Query(
        """
        SELECT * FROM task_execution 
        WHERE task_name = :taskName 
        ORDER BY execution_date_ut DESC 
        """
    )
    fun getByTaskName(taskName: String): List<TaskExecution>

    @Query(
        """
        SELECT * FROM task_execution 
        WHERE task_name = :taskName
        AND sub_task_name = :subTaskName
        AND execution_date = :executionDate
        """
    )
    fun getBy_taskName_subTaskName_executionDate(taskName: String, subTaskName: String, executionDate:String): Flow<List<TaskExecution>>

    @Query(
        """
        SELECT  SUM(duration) FROM task_execution 
        WHERE  execution_date_ut > :min_executionDateUT 
        AND execution_date_ut < :max_executionDateUT
        AND task_name = :taskName
        """
    )
    fun getdurationSumByexecutionDateUT(
        min_executionDateUT:Long,max_executionDateUT: Long,taskName: String
    ): Flow<Int>


    @Insert(onConflict = OnConflictStrategy.ABORT)
  //  suspend fun insert(taskExecution: TaskExecution)
    @Throws(SQLiteException::class)
    suspend fun insert(taskExecution: TaskExecution)

    @Update
    suspend fun update(taskExecution: TaskExecution)

    @Delete
    suspend fun delete(taskExecution: TaskExecution)
}
