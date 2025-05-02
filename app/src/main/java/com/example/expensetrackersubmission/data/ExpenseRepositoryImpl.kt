package com.example.expensetrackersubmission.data

import com.example.expensetrackersubmission.domain.ExpenseDao
import com.example.expensetrackersubmission.data.local.ExpenseEntity
import com.example.expensetrackersubmission.domain.Category
import com.example.expensetrackersubmission.domain.Expense
import com.example.expensetrackersubmission.domain.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val dao: ExpenseDao
) : ExpenseRepository {

    override fun observe(): Flow<List<Expense>> =
        dao.observeAll().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun add(expense: Expense) =
        dao.insert(expense.toEntity())

    override suspend fun delete(expense: Expense) =
        dao.delete(expense.toEntity())

    private fun ExpenseEntity.toDomain() = Expense(
        id, label, amount, Category.valueOf(category), date
    )
    private fun Expense.toEntity() = ExpenseEntity(
        id, label, amount, category.name, date
    )
}
