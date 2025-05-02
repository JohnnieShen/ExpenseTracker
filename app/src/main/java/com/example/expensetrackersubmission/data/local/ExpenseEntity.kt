package com.example.expensetrackersubmission.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    val amount: Double,
    val category: String,
    val date: LocalDate
)
