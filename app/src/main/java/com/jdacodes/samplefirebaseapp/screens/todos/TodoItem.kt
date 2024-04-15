package com.jdacodes.samplefirebaseapp.screens.todos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdacodes.samplefirebaseapp.core.composable.DropdownContextMenu
import com.jdacodes.samplefirebaseapp.core.ext.contextMenu
import com.jdacodes.samplefirebaseapp.core.ext.hasDueDate
import com.jdacodes.samplefirebaseapp.core.ext.hasDueTime
import com.jdacodes.samplefirebaseapp.model.Todo
import com.jdacodes.samplefirebaseapp.ui.theme.PurpleGrey80
import java.lang.StringBuilder
import com.jdacodes.samplefirebaseapp.R.drawable as AppIcon

@Composable
fun TodoItem(
    todo: Todo,
    options: List<String>,
    onCheckChange: () -> Unit,
    onActionClick: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(
                checked = todo.completed,
                onCheckedChange = { onCheckChange() },
                modifier = Modifier.padding(8.dp, 0.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = todo.title, style = MaterialTheme.typography.titleSmall)
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                    Text(text = getDueDateAndTime(todo), fontSize = 12.sp)
                }
            }

            if (todo.flag) {
                Icon(
                    painter = painterResource(AppIcon.ic_flag),
                    tint = PurpleGrey80,
                    contentDescription = "Flag"
                )
            }

            DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
        }

    }
}

private fun getDueDateAndTime(todo: Todo): String {
    val stringBuilder = StringBuilder("")

    if (todo.hasDueDate()) {
        stringBuilder.append(todo.dueDate)
        stringBuilder.append(" ")
    }

    if (todo.hasDueTime()) {
        stringBuilder.append("at ")
        stringBuilder.append(todo.dueTime)
    }

    return stringBuilder.toString()
}