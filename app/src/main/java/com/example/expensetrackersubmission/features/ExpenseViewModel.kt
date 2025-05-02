package com.example.expensetrackersubmission.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackersubmission.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val observeExpenses: ObserveExpenses,
    private val addExpense: AddExpense,
    private val deleteExpense: DeleteExpense
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseState())
    val state: StateFlow<ExpenseState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeExpenses()
                .map { list -> list.map { it.toUi() } }
                .collect { ui ->
                    _state.update { it.copy(items = ui) }
                }
        }
    }

    fun onIntent(intent: ExpenseIntent) = when (intent) {
        ExpenseIntent.AddClicked -> _state.update { it.copy(showAddSheet = true, errorMessage = null) }
        ExpenseIntent.DismissAdd -> _state.update { it.copy(showAddSheet = false, errorMessage = null) }

        is ExpenseIntent.SaveExpense -> viewModelScope.launch {
            val amountVal = intent.amount.toDoubleOrNull()
            if (intent.label.isBlank() || amountVal == null) {
                _state.update { it.copy(errorMessage = "Enter valid label & amount") }
                return@launch
            }
            addExpense(
                Expense(
                    label = intent.label.trim(),
                    amount = amountVal,
                    category = intent.category,
                    date = LocalDate.now()
                )
            )
            _state.update { it.copy(showAddSheet = false, errorMessage = null) }
        }

        is ExpenseIntent.DeleteClicked -> viewModelScope.launch {
            deleteExpense(intent.expense.toDomain())
        }

        is ExpenseIntent.FilterByCategory ->
            _state.update { it.copy(selectedCategory = intent.category) }

        is ExpenseIntent.DeleteClicked -> viewModelScope.launch {
            deleteExpense(intent.expense.toDomain())
        }
    }

    private fun Expense.toUi() =
        ExpenseUiModel(id, label, amount, category, date)
    private fun ExpenseUiModel.toDomain() =
        Expense(id, label, amount, category, date)
}
