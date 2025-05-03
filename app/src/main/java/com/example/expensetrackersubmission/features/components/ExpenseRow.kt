package com.example.expensetrackersubmission.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
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
    onTap: (ExpenseUiModel) -> Unit,
    onLongPress: (ExpenseUiModel) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("MMM d")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onAskDelete(item) },
                    onTap = { onTap(item) }
                )
            }
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(item.label, style = MaterialTheme.typography.titleMedium)
            Text(item.category.name, style = MaterialTheme.typography.bodySmall)
            Text(item.date.format(formatter), style = MaterialTheme.typography.bodySmall)
        }
        Row {
            Text("$%.2f".format(item.amount), style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = { onAskDelete(item) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
