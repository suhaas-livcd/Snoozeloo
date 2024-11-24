package com.example.snoozeloo.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.snoozeloo.R
import com.example.snoozeloo.viewmodel.AppViewModel

@Composable
fun AlarmRingtoneScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier,
    navBackToAlarmSettingsScreen: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.back_icon),
                contentDescription = "Back Icon",
                modifier = Modifier.clickable {
                    navBackToAlarmSettingsScreen()
                })
        }
    }
}