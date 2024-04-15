package com.jdacodes.samplefirebaseapp.core.composable

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jdacodes.samplefirebaseapp.R

@Composable
fun BasicTextButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    action: () -> Unit
) {
    TextButton(
        onClick = action,
        modifier = modifier
    ) {
        Text(text = stringResource(id = text))
    }
}

@Composable
fun BasicButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    action: () -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary

    Button(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryColor,
            contentColor = onPrimaryColor
        )
    ) {
        Text(text = stringResource(id = text))
    }
}

@Composable
fun DialogConfirmButton(
    @StringRes text: Int,
    action: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Button(
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary
        )
    ) {
        Text(text = stringResource(id = text))
    }
}

@Composable
fun DialogCancelButton(
    @StringRes text: Int,
    action: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Button(
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary
        )
    ) {
        Text(text = stringResource(id = text))
    }
}
@Preview
@Composable
fun PreviewBasicTextButton() {
    BasicTextButton(
        text = R.string.example_text,
        action = {}
    )
}

@Preview
@Composable
fun PreviewBasicButton() {
    BasicButton(
        text = R.string.example_text,
        action = {}
    )
}

@Preview
@Composable
fun PreviewDialogConfirmButton() {
    DialogConfirmButton(
        text = R.string.confirm,
        action = {}
    )
}

@Preview
@Composable
fun PreviewDialogCancelButton() {
    DialogConfirmButton(
        text = R.string.cancel,
        action = {}
    )
}

