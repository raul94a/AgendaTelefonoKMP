package org.raa.agendatelefono.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
actual fun contactsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}