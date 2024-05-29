package com.example.findlostitemapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlin.reflect.jvm.internal.impl.types.checker.TypeRefinementSupport.Enabled

enum class CustomTextFieldType {
    Default,
    Outline
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit,
    label: String = "", error: String? = null, isPassword: Boolean = false,
    leadingIcon: @Composable (() ->
    Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Next
    ),
    type: CustomTextFieldType = CustomTextFieldType.Outline,
    colors: TextFieldColors? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    isLasted: Boolean = false,
    enabled: Boolean = true
) {
    Column {
        when (type) {
            CustomTextFieldType.Default -> {
                TextField(
                    readOnly = readOnly,
                    value = value,
                    onValueChange = onValueChange,
                    label = { Text(text = label) },
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    isError = error != null,
                    keyboardOptions = if (isLasted) KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ) else keyboardOptions,
                    visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = colors ?: TextFieldDefaults.colors(),
                    singleLine = singleLine,
                    enabled = enabled
                )
            }

            CustomTextFieldType.Outline -> {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    label = { Text(text = label) },
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    isError = error != null,
                    keyboardOptions = if (isLasted) KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ) else keyboardOptions,
                    visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = colors ?: OutlinedTextFieldDefaults.colors(),
                    singleLine = singleLine,
                    enabled = enabled
                )
            }
        }

        error?.let {
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}