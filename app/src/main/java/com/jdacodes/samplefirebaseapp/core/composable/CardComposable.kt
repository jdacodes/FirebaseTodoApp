@file:OptIn(ExperimentalMaterial3Api::class)

package com.jdacodes.samplefirebaseapp.core.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jdacodes.samplefirebaseapp.core.ext.dropdownSelector

@Composable
fun DangerousCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, MaterialTheme.colorScheme.primary, modifier)
}

@Composable
fun RegularCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, MaterialTheme.colorScheme.onSurface, modifier)
}

@Composable
fun EditCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    modifier: Modifier,
    onToggleState: (Boolean) -> Unit,
    composable: @Composable () -> Unit,

    ) {
    val isCardExpanded = remember { mutableStateOf(false) }
    EditCardEditor(
        title,
        icon,
        content,
        onToggleState = onToggleState,
        composable,
        MaterialTheme.colorScheme.onSurface,
        modifier
    )
}

@Composable
private fun CardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    onEditClick: () -> Unit,
    highlightColor: Color,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        onClick = onEditClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = title),
                    color = highlightColor
                )
            }
                if (content.isNotBlank()) {
                    Text(
                        text = content,
                        modifier = Modifier.padding(16.dp, 0.dp)
                    )
                }
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Icon",
                    tint = highlightColor
                )

        }

    }
}

@Composable
private fun EditCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    onToggleState: (Boolean) -> Unit, // Callback to toggle expanded state
    composable: @Composable () -> Unit,
    highlightColor: Color,
    modifier: Modifier
) {
    var isExpanded by remember { mutableStateOf(false) } // State for card expansion
    // Pass the expanded state to the callback for external updates
    onToggleState(isExpanded)
    Card(
        modifier = modifier
            .clickable {
                isExpanded = !isExpanded
            } // Make the entire card clickable
            .animateContentSize(), // Add animation for smooth expansion
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        // ... Card content ...
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = title),
                    color = highlightColor
                )
            }
                if (content.isNotBlank()) {
                    Text(
                        text = content,
                        modifier = Modifier.padding(16.dp, 0.dp)
                    )
                }
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Icon",
                    tint = highlightColor
                )

        }
    }
    if (isExpanded) {
        composable() // Render the date or time picker if card is expanded
    }
}


@Composable
fun CardSelector(
    @StringRes label: Int,
    options: List<String>,
    selection: String,
    modifier: Modifier,
    onNewValue: (String) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        DropdownSelector(label, options, selection, Modifier.dropdownSelector(), onNewValue)
    }
}

//@Preview
//@Composable
//fun PreviewCardEditor() {
//    CardEditor(
//        title = R.string.title_example,
//        icon = R.drawable.icon_example,
//        content = "Content Example",
//        onEditClick = {},
//        highlightColor = Color.Blue
//    )
//}