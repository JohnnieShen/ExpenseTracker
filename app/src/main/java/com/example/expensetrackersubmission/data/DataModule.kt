package com.example.expensetrackersubmission.data

import android.content.Context
import androidx.room.Room
import com.example.expensetrackersubmission.data.local.ExpenseDatabase
import com.example.expensetrackersubmission.domain.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides @Singleton
    fun provideDb(@ApplicationContext ctx: Context): ExpenseDatabase =
        Room.databaseBuilder(ctx, ExpenseDatabase::class.java, "expenses.db").build()

    @Provides
    fun provideDao(db: ExpenseDatabase) = db.dao()

    @Provides @Singleton
    fun provideRepo(impl: ExpenseRepositoryImpl): ExpenseRepository = impl
}
