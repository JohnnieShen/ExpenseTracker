package com.example.expensetrackersubmission

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.example.expensetrackersubmission.data.ExpenseRepositoryImpl
import com.example.expensetrackersubmission.data.local.ExpenseDatabase
import com.example.expensetrackersubmission.domain.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
class ExpenseViewModelTest {

    private val repo = mockk<ExpenseRepository>(relaxed = true)
    private val observe = ObserveExpenses(repo)
    private val add     = AddExpense(repo)
    private val delete  = DeleteExpense(repo)
    private val dispatcher = StandardTestDispatcher()

    @Before fun setMain() = Dispatchers.setMain(dispatcher)
    @After  fun reset()   = Dispatchers.resetMain()

    @Test fun saveIntentCallsRepo() = runTest {
        every { repo.observe() } returns emptyFlow()

        val vm = com.example.expensetrackersubmission.features.ExpenseViewModel(
            observe, add, delete
        )

        vm.state.test {
            awaitItem()
            vm.onIntent(
                com.example.expensetrackersubmission.features.ExpenseIntent
                    .SaveExpense("Lunch", "12.0", Category.Food)
            )
            advanceUntilIdle()

            io.mockk.coVerify {
                repo.add(match { it.label == "Lunch" && it.amount == 12.0 })
            }
            cancelAndIgnoreRemainingEvents()
        }
    }
}
