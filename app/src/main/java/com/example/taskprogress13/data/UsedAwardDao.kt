package com.example.taskprogress13.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsedAwardDao {

    @Query(
        """
        SELECT * FROM used_award 
        ORDER BY date_of_use_ut DESC    
        """
    )
    fun getAll(): Flow<List<UsedAward>>

    @Query(
        """
        SELECT  SUM(task_execution_minutes_needed) FROM used_award 
        WHERE date_of_use_ut > :min_dateOfUseUT 
        AND date_of_use_ut < :max_dateOfUseUT
        AND task_name = :taskName
        """
    )
    fun getDurationSumByDateOfUseUTANDtaskName(min_dateOfUseUT:Long, max_dateOfUseUT:Long, taskName: String): Flow<Int>

    @Query(
        """
        SELECT * FROM used_award 
        WHERE task_name = :taskName 
        ORDER BY date_of_use_ut DESC 
        """
    )
    fun getUsedAwardsByTaskName(taskName: String): Flow<List<UsedAward>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(usedAward: UsedAward)

    @Delete
    suspend fun delete(usedAward: UsedAward)

}


