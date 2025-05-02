package com.example.expensetrackersubmission.features

import com.example.expensetrackersubmission.domain.Category
import java.time.LocalDate

data class ExpenseUiModel(
    val id: Int,
    val label: String,
    val amount: Double,
    val category: Category,
    val date: LocalDate
)
