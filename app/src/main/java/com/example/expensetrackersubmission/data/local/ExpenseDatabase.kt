package com.example.expensetrackersubmission.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.TypeConverter
import com.example.expensetrackersubmission.domain.Category
import com.example.expensetrackersubmission.domain.ExpenseDao
import java.time.LocalDate

@Database(entities = [ExpenseEntity::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class, CategoryConverter::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun dao(): ExpenseDao
}

class LocalDateConverter {
    @TypeConverter fun toDate(epochDay: Long): LocalDate = LocalDate.ofEpochDay(epochDay)
    @TypeConverter fun fromDate(date: LocalDate): Long = date.toEpochDay()
}

class CategoryConverter {
    @TypeConverter fun toEnum(name: String): Category = Category.valueOf(name)
    @TypeConverter fun fromEnum(cat: Category): String = cat.name
}