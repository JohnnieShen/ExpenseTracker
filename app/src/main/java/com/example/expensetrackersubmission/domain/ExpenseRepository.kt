package com.example.expensetrackersubmission.domain

import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun observe(): Flow<List<Expense>>
    suspend fun add(expense: Expense)
    suspend fun delete(expense: Expense)
}
