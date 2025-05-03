package com.example.expensetrackersubmission.features.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetrackersubmission.domain.Category
import com.example.expensetrackersubmission.features.ExpenseIntent
import com.example.expensetrackersubmission.features.ExpenseUiModel
import com.example.expensetrackersubmission.features.components.AddExpenseSheet
import com.example.expensetrackersubmission.features.components.ExpenseRow
import com.example.expensetrackersubmission.features.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(
    vm: ExpenseViewModel = hiltViewModel()
) {
    val uiState by vm.state.collectAsState()

    val filtered = remember(uiState.items, uiState.selectedCategory) {
        uiState.selectedCategory?.let { cat ->
            uiState.items.filter { it.category == cat }
        } ?: uiState.items
    }
    val total = remember(filtered) { filtered.sumOf { it.amount } }

    Scaffold(
        topBar = {
            //Using TopAppBar instead of built in implementation to avoid off center placement
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(
                            text = "Total: $%.2f".format(total),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                actions = {
                    CategoryFilterDropdown(
                        selected = uiState.selectedCategory,
                        onFilterChanged = { intent -> vm.onIntent(intent) }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { vm.onIntent(ExpenseIntent.AddClicked) }) {
                Icon(Icons.Default.Add, contentDescription = "Add expense")
            }
        }
    ) { padding ->
        ExpenseList(
            items = filtered,
            contentPadding = padding,
            onAskDelete = { vm.onIntent(ExpenseIntent.AskDelete(it)) },
            onLongPress = { vm.onIntent(ExpenseIntent.DeleteClicked(it)) },
            onTap = { vm.onIntent(ExpenseIntent.EditClicked(it)) }
        )
    }

    if (uiState.showSheet) {
        AddExpenseSheet(
            initial = uiState.editing,
            error   = uiState.errorMessage,
            onDismiss = { vm.onIntent(ExpenseIntent.DismissAdd) },
            onSaveNew = { vm.onIntent(it) },
            onSaveEdit = { vm.onIntent(it) }
        )
    }
    if (uiState.pendingDelete != null) {
        val exp = uiState.pendingDelete
        AlertDialog(
            onDismissRequest = { vm.onIntent(ExpenseIntent.CancelDelete) },
            title = { Text("Delete expense") },
            text = {
                if (exp != null) {
                    Text("Are you sure you want to delete “${exp.label}” ?")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { exp?.let { ExpenseIntent.ConfirmDelete(it) }
                        ?.let { vm.onIntent(it) } }
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(
                    onClick = { vm.onIntent(ExpenseIntent.CancelDelete) }
                ) { Text("Cancel") }
            }
        )
    }
}

//OLD VERSION, DOESN'T WORK WELL IN CERTAIN RESOLUTIONS
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TotalHeader(
    total: Double,
    selected: Category?,
    onFilterChanged: (ExpenseIntent.FilterByCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val label = selected?.name ?: "All"

    Surface(
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total: $%.2f".format(total), style = MaterialTheme.typography.titleMedium)

            Box {
                TextButton(onClick = { expanded = true }) {
                    Text(label)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All") },
                        onClick = {
                            expanded = false
                            onFilterChanged(ExpenseIntent.FilterByCategory(null))
                        }
                    )
                    Category.entries.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat.name) },
                            onClick = {
                                expanded = false
                                onFilterChanged(ExpenseIntent.FilterByCategory(cat))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpenseList(
    items: List<ExpenseUiModel>,
    contentPadding: PaddingValues,
    onAskDelete: (ExpenseUiModel) -> Unit,
    onLongPress: (ExpenseUiModel) -> Unit,
    onTap: (ExpenseUiModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding.add(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, key = { it.id }) { expense ->
            ExpenseRow(item = expense, onAskDelete = onAskDelete, onTap = onTap)
        }
    }
}

@Composable
private fun CategoryFilterDropdown(
    selected: Category?,
    onFilterChanged: (ExpenseIntent.FilterByCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val label = selected?.name ?: "All"

    Box(modifier = modifier) {
        TextButton(onClick = { expanded = true }) {
            Text(label)
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Filter expenses by category"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("All") },
                onClick = {
                    expanded = false
                    onFilterChanged(ExpenseIntent.FilterByCategory(null))
                }
            )
            Category.entries.forEach { cat ->
                DropdownMenuItem(
                    text = { Text(cat.name) },
                    onClick = {
                        expanded = false
                        onFilterChanged(ExpenseIntent.FilterByCategory(cat))
                    }
                )
            }
        }
    }
}

private fun PaddingValues.add(extra: Dp): PaddingValues = PaddingValues(
    start = calculateStartPadding(LayoutDirection.Ltr) + extra,
    top = calculateTopPadding() + extra,
    end = calculateEndPadding(LayoutDirection.Ltr) + extra,
    bottom = calculateBottomPadding() + extra
)

