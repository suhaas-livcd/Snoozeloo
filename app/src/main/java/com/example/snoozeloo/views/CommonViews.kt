package com.example.snoozeloo.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.snoozeloo.R
import com.example.snoozeloo.ui.theme.appPrimaryColor

@Composable
fun LoadingScreen(modifier: Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(appPrimaryColor)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.alarm_icon),
            contentDescription = "Alarm Splash screen",
            colorFilter = ColorFilter.tint(
                Color.White
            )
        )
    }
}