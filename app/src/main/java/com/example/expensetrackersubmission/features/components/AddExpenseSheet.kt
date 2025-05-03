package com.example.expensetrackersubmission.features.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.example.expensetrackersubmission.domain.Category
import com.example.expensetrackersubmission.features.ExpenseIntent
import com.example.expensetrackersubmission.features.ExpenseUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseSheet(
    initial: ExpenseUiModel?,
    error: String?,
    onDismiss: () -> Unit,
    onSaveNew: (ExpenseIntent.SaveExpense) -> Unit,
    onSaveEdit: (ExpenseIntent.SaveEdit) -> Unit
) {
    var label by remember { mutableStateOf(initial?.label ?: "") }
    var amount  by remember { mutableStateOf(
        initial?.amount?.toString() ?: "") }
    var selected by remember { mutableStateOf(
        initial?.category ?: Category.Food) }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(24.dp)) {
            //Title
            Text(
                text = if (initial == null) "Add Expense" else "Edit Expense",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(16.dp))
            //Label field
            OutlinedTextField(
                value = label,
                onValueChange = { label = it },
                label = { Text("Label") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
            //Amount field
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
            //Category enum
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Category.entries.forEach { cat ->
                    FilterChip(
                        selected = selected == cat,
                        onClick = { selected = cat },
                        label = { Text(cat.name) }
                    )
                }
            }

            error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(24.dp))
            //Button at the end of the row in the last row
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (initial == null) {
                            onSaveNew(ExpenseIntent.SaveExpense(label, amount, selected))
                        } else {
                            onSaveEdit(
                                ExpenseIntent.SaveEdit(initial.id, label, amount, selected)
                            )
                        }
                    }
                ) { Text(if (initial == null) "Save" else "Update") }
            }
        }
    }
}
