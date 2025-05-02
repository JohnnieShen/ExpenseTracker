package com.example.expensetrackersubmission

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.filters.LargeTest
import com.example.expensetrackersubmission.data.ExpenseRepositoryImpl
import com.example.expensetrackersubmission.data.local.ExpenseDatabase
import com.example.expensetrackersubmission.domain.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class ExpenseRepositoryInstrumentedTest {

    private lateinit var db: ExpenseDatabase
    private lateinit var repo: ExpenseRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ExpenseDatabase::class.java
        ).allowMainThreadQueries().build()

        repo = ExpenseRepositoryImpl(db.dao())
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun insertAndObserve() = runBlocking {
        val expense = Expense(label = "Coffee", category = Category.Food, amount = 3.50)

        repo.add(expense)
        val list = repo.observe().first()

        Assert.assertEquals(1, list.size)
        Assert.assertEquals("Coffee", list[0].label)
        Assert.assertEquals(3.50, list[0].amount, 0.0)
    }
}

