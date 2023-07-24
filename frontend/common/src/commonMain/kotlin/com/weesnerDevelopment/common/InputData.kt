package com.weesnerDevelopment.common

data class InputData(
    val value: String,
    val errorMessage: (currentValue: String) -> String,
    val hasError: Boolean = false,
)
