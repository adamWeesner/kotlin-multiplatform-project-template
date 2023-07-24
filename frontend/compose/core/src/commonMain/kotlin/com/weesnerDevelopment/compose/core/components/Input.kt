package com.weesnerDevelopment.compose.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.weesnerDevelopment.common.InputData
import com.weesnerDevelopment.compose.core.Image
import com.weesnerDevelopment.compose.core.imagePainter

data class IconData(
    val icon: Painter,
    val description: String? = null,
    val enabled: Boolean = false,
    val onClick: (() -> Unit?)? = null,
)

@Composable
fun ClearIconData(
    enabled: Boolean,
    onClick: () -> Unit
): IconData {
    return IconData(
        icon = imagePainter(Image.Cancel),
        description = "Clear",
        enabled = enabled,
        onClick = { onClick() }
    )
}

@Composable
fun Input(
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    trailingIcon: IconData? = null,
    leadingIcon: IconData? = null,
    label: String,
    input: InputData,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier,
        label = { Text(text = label) },
        maxLines = maxLines,
        value = input.value,
        isError = input.hasError,
        onValueChange = onValueChange,
        leadingIcon = OptionalImage(leadingIcon),
        trailingIcon = OptionalImage(trailingIcon),
    )
    if (input.hasError)
        Text(
            style = MaterialTheme.typography.labelSmall,
            text = input.errorMessage(input.value),
            color = MaterialTheme.colorScheme.error
        )
}

@Composable
private fun OptionalImage(data: IconData?): @Composable() (() -> Unit)? =
    if (data == null || !data.enabled) {
        null
    } else {
        {
            Image(
                modifier = data.onClick?.let {
                    Modifier.clickable { it() }
                } ?: Modifier,
                painter = data.icon,
                contentDescription = data.description
            )
        }
    }