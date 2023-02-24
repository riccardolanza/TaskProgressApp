package com.example.taskprogress13.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AwardDao {

    @Query(
        """
        SELECT * FROM award 
        ORDER BY award_name DESC    
        """
    )
    fun getAll(): Flow<List<Award>>

    @Query(
        """
        SELECT * FROM award 
        WHERE task_execution_minutes_needed >= :taskExecutionMinutesNeeded 
        ORDER BY award_name DESC 
        """
    )
    fun getByTaskExecutionMinutesNeeded(taskExecutionMinutesNeeded: Int): Flow<List<Award>>

    @Query(
        """
        SELECT * FROM award 
        WHERE award_name = :awardName
        """
    )
    fun getByAwardName(awardName: String): Flow<List<Award>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(award: Award)

    @Update
    suspend fun update(award: Award)

    @Delete
    suspend fun delete(award: Award)
}


