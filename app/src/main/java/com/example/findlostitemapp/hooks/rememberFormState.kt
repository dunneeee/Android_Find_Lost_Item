package com.example.findlostitemapp.hooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.TextFieldValue

data class ValidatorItem<T>(
    val key: String, val validator: ValidatorType<T>, val defaultValue: T? = null
)

typealias ValidatorType<T> = FormState<T>.(T) -> String?

class FormState<T>(private val defaultValue: T, private val defaultRule: FormState<T>.(T) -> Boolean = { false }) {
    private val fields = mutableStateMapOf<String, T>()
    private val errors = mutableStateMapOf<String, String>()
    private val validators = mutableStateMapOf<String, ValidatorType<T>>()

    fun registerField(
        key: String, defaultValue: T = this.defaultValue, validator: ValidatorType<T>
    ) {
        fields[key] = defaultValue
        validators[key] = validator
    }

    fun registerField(validatorItem: ValidatorItem<T>) {
        fields[validatorItem.key] = validatorItem.defaultValue ?: this.defaultValue
        validators[validatorItem.key] = validatorItem.validator
    }

    fun registerField(validatorItems: List<ValidatorItem<T>>) {
        validatorItems.forEach { registerField(it) }
    }

    fun unregisterField(key: String) {
        fields.remove(key)
        validators.remove(key)
        errors.remove(key)
    }

    fun setValue(key: String, value: T, rules: FormState<T>.(T) -> Boolean = this.defaultRule) {
        fields[key] = value
        val error = validators[key]?.invoke(this, value)

        if (error != null) {
            if (rules(value)) {
                errors[key] = error
            } else {
                errors.remove(key)
            }
        } else {
            errors.remove(key)
        }
    }

    fun getValue(key: String, defaultValue: T = this.defaultValue): T {
        return fields[key] ?: defaultValue
    }

    fun getError(key: String): String? {
        return errors[key]
    }

    fun isValid(): Boolean {
        return errors.isEmpty() && validators.all { (key, validator) ->
            validator(fields[key]!!) == null
        }
    }

    fun validateAll() {
        fields.forEach { (key, value) ->
            val error = validators[key]?.invoke(this, value)
            if (error != null) {
                errors[key] = error
            } else {
                errors.remove(key)
            }
        }
    }

    fun clear() {
        errors.clear()
        fields.forEach { (key, _) ->
            fields[key] = defaultValue
        }
    }

    override fun toString(): String {
        return "FormState(fields=${fields.size}, errors=${errors.size}, validators=${validators.size})"
    }
}

@Composable
fun <T> rememberFormState(defaultValue: T, defaultRuleFunc: FormState<T>.(T) -> Boolean = { false }): FormState<T> {
    return remember {
        FormState(defaultValue, defaultRuleFunc)
    }
}