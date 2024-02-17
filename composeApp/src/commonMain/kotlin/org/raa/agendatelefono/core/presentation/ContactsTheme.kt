package org.raa.agendatelefono.core.presentation

import androidx.compose.runtime.Composable



@Composable
expect fun contactsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit)
