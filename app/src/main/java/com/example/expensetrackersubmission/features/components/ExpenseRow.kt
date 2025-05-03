package com.example.expensetrackersubmission.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.expensetrackersubmission.features.ExpenseUiModel
import java.time.format.DateTimeFormatter

@Composable
fun ExpenseRow(
    item: ExpenseUiModel,
    onAskDelete: (ExpenseUiModel) -> Unit,
    onTap: (ExpenseUiModel) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("MMM d")

    Surface(                                    // ← NEW wrapper
        color  = MaterialTheme.colorScheme.primaryContainer,
        shape  = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onAskDelete(item) },
                    onTap       = { onTap(item) }
                )
            }
    ) {
        Row(                                    // ← content stays the same
            modifier = Modifier
                .padding(16.dp),                // internal padding
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(item.label, style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(item.category.name, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(item.date.format(formatter), style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            Row {
                Text("$%.2f".format(item.amount),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = { onAskDelete(item) }) {
                    Icon(Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        }
    }
}

