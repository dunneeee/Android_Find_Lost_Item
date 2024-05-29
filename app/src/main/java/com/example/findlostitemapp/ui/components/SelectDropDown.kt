package com.example.findlostitemapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.geometry.Size

data class SelectOption<T>(val value: T, val label: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> OutlineSelectDropDown(
    modifier: Modifier = Modifier,
    title: String = "Select an option",
    options: List<SelectOption<T>>, selectedOption:
    SelectOption<T>, onOptionSelected: (SelectOption<T>) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    OutlinedTextField(
        leadingIcon = leadingIcon,
        value = selectedOption.label,
        onValueChange = {},
        readOnly = true,
        label = label,
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
            }
        },
        colors = colors,
        textStyle = textStyle,
        modifier = modifier.fillMaxWidth()
    )

    if (expanded) {
        ModalBottomSheet(onDismissRequest = { expanded = false }, dragHandle = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                BottomSheetDefaults.DragHandle()
                Text(text = title)
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
            }
        }) {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(options.size) { index ->
                    val option = options[index]
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onOptionSelected(option)
                            expanded = false
                        }) {
                        Text(text = option.label, modifier = Modifier.padding(8.dp))
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SelectDropDown(
    modifier: Modifier = Modifier,
    title: String = "Select an option",
    options: List<SelectOption<T>>, selectedOption:
    SelectOption<T>, onOptionSelected: (SelectOption<T>) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    type: CustomTextFieldType = CustomTextFieldType.Outline,
    error: String? = null
) {
    var expanded by remember { mutableStateOf(false) }


    CustomTextField(
        leadingIcon = leadingIcon,
        value = TextFieldValue(selectedOption.label),
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
            }
        },
        modifier = modifier.fillMaxWidth(),
        colors = colors,
        type = type,
        error = error,
    )

    if (expanded) {
        ModalBottomSheet(onDismissRequest = { expanded = false }, dragHandle = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                BottomSheetDefaults.DragHandle()
                Text(text = title)
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
            }
        }) {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(options.size) { index ->
                    val option = options[index]
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onOptionSelected(option)
                            expanded = false
                        }) {
                        Text(text = option.label, modifier = Modifier.padding(8.dp))
                    }

                }
            }
        }
    }
}


@Composable
fun <T> SelectDropDownMenu(
    modifier: Modifier = Modifier,
    title: String = "Select an option",
    options: List<SelectOption<T>>, selectedOption:
    SelectOption<T>, onOptionSelected: (SelectOption<T>) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.colors(
        disabledTextColor = MaterialTheme.colorScheme.onSurface,
        disabledPrefixColor = MaterialTheme.colorScheme.onSurface,
        disabledLabelColor = MaterialTheme.colorScheme.onSurface,
        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
    ),
    type: CustomTextFieldType = CustomTextFieldType.Outline,
    error: String? = null
) {

    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    Column(modifier = modifier) {
        CustomTextField(
            label = title,
            leadingIcon = leadingIcon,
            value = TextFieldValue(selectedOption.label),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // Capture the width of the TextField
                    textFieldSize = coordinates.size.toSize()
                },
            colors = colors,
            type = type,
            error = error,
            enabled = false,
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            options.forEach { option ->
                DropdownMenuItem(text = {
                    if (option.value == selectedOption.value) {
                        Text(text = option.label, fontWeight = FontWeight.Bold)
                    } else {
                        Text(text = option.label)
                    }
                }, onClick = {
                    expanded = false
                    onOptionSelected(option)
                })
            }
        }
    }
}
