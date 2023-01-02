package com.rzl.flightgotiketbooking.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Container(modifier: Modifier = Modifier, body: @Composable BoxScope.() -> Unit) =
    Box(modifier = modifier.fillMaxSize(), content = body)