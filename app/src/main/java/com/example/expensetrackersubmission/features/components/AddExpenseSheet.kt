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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseSheet(
    onSave: (ExpenseIntent.SaveExpense) -> Unit,
    onDismiss: () -> Unit,
    error: String?
) {
    var label by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(Category.Food) }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(24.dp)) {
            Text("Add Expense", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = label,
                onValueChange = { label = it },
                label = { Text("Label") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

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

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        onSave(
                            ExpenseIntent.SaveExpense(label, amount, selected)
                        )
                    }
                ) { Text("Save") }
            }
        }
    }
}
