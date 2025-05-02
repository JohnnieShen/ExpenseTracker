package com.example.expensetrackersubmission.domain

import java.time.LocalDate

data class Expense  constructor(
    val id: Int = 0,
    val label: String,
    val amount: Double,
    val category: Category,
    val date: LocalDate = LocalDate.now()
)
