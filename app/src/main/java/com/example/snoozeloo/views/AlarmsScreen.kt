package com.example.snoozeloo.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snoozeloo.R
import com.example.snoozeloo.data.AppUiState
import com.example.snoozeloo.data.SnoozelooAlarm
import com.example.snoozeloo.ui.theme.appPrimaryColor
import com.example.snoozeloo.viewmodel.AppViewModel

@Composable
fun AlarmsScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier,
    navToAlarmSettingsScreen: () -> Unit
) {
    val uiState: AppUiState by viewModel.uiState.collectAsState()
    when {
        uiState.isLoading -> {
            LoadingScreen(modifier)
        }

        uiState.errorMessage != null -> {
            Text("Error: ${uiState.errorMessage}")
        }

        else -> {
            AlarmListScreen(modifier, alarms = uiState.items)

            // Show always floating action button
            FloatingActionButton(modifier, navToAlarmSettingsScreen = navToAlarmSettingsScreen)
        }
    }
}

@Composable
fun AlarmListScreen(modifier: Modifier = Modifier, alarms: List<SnoozelooAlarm>) {
    Text(
        modifier = modifier.padding(16.dp),
        text = stringResource(R.string.your_alarms),
        fontWeight = FontWeight.W500,
        fontSize = 24.sp
    )

    if (alarms.isEmpty()) {
        EmptyAlarmsView(modifier)
    } else {
        AlarmListView(modifier, alarms)
    }
}

@Composable
fun AlarmListView(modifier: Modifier = Modifier, alarms: List<SnoozelooAlarm>) {

}

@Composable
fun EmptyAlarmsView(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.alarm_icon),
                contentDescription = "Alarm Icon",
                colorFilter = ColorFilter.tint(appPrimaryColor),
                modifier = Modifier
                    .padding(bottom = 38.dp)
                    .size(62.dp)
            )
            Text(text = stringResource(R.string.empty_alarms), fontWeight = FontWeight.W500)
        }
    }
}

@Composable
fun FloatingActionButton(modifier: Modifier = Modifier, navToAlarmSettingsScreen: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        androidx.compose.material3.FloatingActionButton(
            containerColor = appPrimaryColor,
            onClick = { navToAlarmSettingsScreen() },
            shape = CircleShape
        ) {
            Icon(Icons.Filled.Add, "Create Alarm", tint = Color.White)
        }
    }
}