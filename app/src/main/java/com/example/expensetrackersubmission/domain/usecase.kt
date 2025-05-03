package com.example.expensetrackersubmission.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveExpenses @Inject constructor(
    private val repo: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> = repo.observe()
}

class AddExpense @Inject constructor(
    private val repo: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) = repo.add(expense)
}

class DeleteExpense @Inject constructor(
    private val repo: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) = repo.delete(expense)
}

class UpdateExpense @Inject constructor(
    private val repo: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) = repo.update(expense)
}

