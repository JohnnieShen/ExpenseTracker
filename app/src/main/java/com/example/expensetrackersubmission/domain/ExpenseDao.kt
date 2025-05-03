package com.example.expensetrackersubmission.domain

import com.example.expensetrackersubmission.data.local.ExpenseEntity
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM ExpenseEntity ORDER BY date DESC")
    fun observeAll(): Flow<List<ExpenseEntity>>

    @Insert suspend fun insert(entity: ExpenseEntity)
    @Delete suspend fun delete(entity: ExpenseEntity)
    @Update suspend fun update(entity: ExpenseEntity)
}
