package com.example.snoozeloo.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snoozeloo.AppUtils
import com.example.snoozeloo.R
import com.example.snoozeloo.data.AppUiState
import com.example.snoozeloo.data.SnoozelooAlarm
import com.example.snoozeloo.data.SnoozelooAlarmEvents
import com.example.snoozeloo.data.WeekDays
import com.example.snoozeloo.ui.theme.appPrimaryColor
import com.example.snoozeloo.ui.theme.appSecondaryColor
import com.example.snoozeloo.ui.theme.appTertiaryColor
import com.example.snoozeloo.ui.theme.greyTextColor

@Composable
fun AlarmsScreen(
    uiState: AppUiState,
    modifier: Modifier = Modifier,
    navToAlarmSettingsScreen: () -> Unit,
    onEvent: (SnoozelooAlarmEvents) -> Unit
) {
    when {
        uiState.isLoading -> {
            LoadingScreen(modifier)
        }

        uiState.errorMessage != null -> {
            Text("Error: ${uiState.errorMessage}")
        }

        else -> {
            AlarmListScreen(modifier, alarms = uiState.alarms, onEvent)

            // Show always floating action button
            FloatingActionButton(modifier, navToAlarmSettingsScreen = navToAlarmSettingsScreen)
        }
    }
}

@Composable
fun AlarmListScreen(
    modifier: Modifier = Modifier,
    alarms: List<SnoozelooAlarm>,
    onEvent: (SnoozelooAlarmEvents) -> Unit
) {
    Text(
        modifier = modifier.padding(16.dp),
        text = stringResource(R.string.your_alarms),
        fontWeight = FontWeight.W500,
        fontSize = 24.sp
    )

    if (alarms.isEmpty()) {
        EmptyAlarmsView(modifier)
    } else {
        AlarmListView(modifier, alarms, onEvent)
    }
}

@Composable
fun AlarmListView(
    modifier: Modifier = Modifier,
    alarms: List<SnoozelooAlarm>,
    onEvent: (SnoozelooAlarmEvents) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(top = 50.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(alarms) { alarm ->
            AlarmListItem(alarm = alarm, onEvent)
        }
    }
}

@Composable
fun AlarmListItem(alarm: SnoozelooAlarm, onEvent: (SnoozelooAlarmEvents) -> Unit) {
    val alarmMetaInfo =
        AppUtils.getAlarmMetaInfo(alarm.timeHours, alarm.timeMinutes, alarm.repeatDays)
    Card(
        shape = RoundedCornerShape(16.dp), colors = CardColors(
            containerColor = Color.White,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Name, Switch
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = alarm.name,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                )

                Switch(
                    checked = alarm.isEnabled, onCheckedChange = {
                        onEvent(
                            SnoozelooAlarmEvents.SetAlarmIsEnabled(
                                alarmId = alarm.id,
                                isEnabled = !alarm.isEnabled
                            )
                        )
                    }, colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = appPrimaryColor,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = appSecondaryColor,
                        uncheckedBorderColor = appSecondaryColor
                    )
                )
            }

            // Time, separator
            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = alarm.timeHours.toString() + ":" + alarm.timeMinutes.toString(),
                    fontWeight = FontWeight.W500,
                    fontSize = 42.sp,
                    textAlign = TextAlign.Start
                )

                Text(
                    modifier = Modifier.padding(bottom = 6.dp),
                    text = alarmMetaInfo.alarmAmPm,
                    fontWeight = FontWeight.W500,
                    fontSize = 24.sp
                )
            }

            // Alarm sleep info
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = alarmMetaInfo.nextAlarmTimeRemaining,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                color = greyTextColor
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                AlarmRepeatDayItemNonEditable(
                    dayText = "Mo",
                    buttonEnabled = WeekDays.isDaySelected(alarm.repeatDays, WeekDays.MONDAY)
                )
                AlarmRepeatDayItemNonEditable(
                    dayText = "Tu",
                    buttonEnabled = WeekDays.isDaySelected(alarm.repeatDays, WeekDays.TUESDAY)
                )
                AlarmRepeatDayItemNonEditable(
                    dayText = "We",
                    buttonEnabled = WeekDays.isDaySelected(alarm.repeatDays, WeekDays.WEDNESDAY)
                )
                AlarmRepeatDayItemNonEditable(
                    dayText = "Th",
                    buttonEnabled = WeekDays.isDaySelected(alarm.repeatDays, WeekDays.THURSDAY)
                )
                AlarmRepeatDayItemNonEditable(
                    dayText = "Fr",
                    buttonEnabled = WeekDays.isDaySelected(alarm.repeatDays, WeekDays.FRIDAY)
                )
                AlarmRepeatDayItemNonEditable(
                    dayText = "Sa",
                    buttonEnabled = WeekDays.isDaySelected(alarm.repeatDays, WeekDays.SATURDAY)
                )
                AlarmRepeatDayItemNonEditable(
                    dayText = "Su",
                    buttonEnabled = WeekDays.isDaySelected(alarm.repeatDays, WeekDays.SUNDAY)
                )
            }

            // Alarm sleep info
            alarmMetaInfo.sleepBedTime?.let {
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = it,
                    fontSize = 14.sp,
                    color = greyTextColor
                )
            }
        }
    }
}

@Composable
fun AlarmRepeatDayItemNonEditable(
    dayText: String,
    buttonEnabled: Boolean = false
) {
    Button(
        modifier = Modifier
            .width(38.dp)
            .height(26.dp),
        colors = ButtonColors(
            containerColor = appPrimaryColor,
            contentColor = Color.White,
            disabledContentColor = Color.Black,
            disabledContainerColor = appTertiaryColor
        ),
        onClick = {},
        contentPadding = PaddingValues(start = 9.dp, end = 9.dp),
        enabled = buttonEnabled
    ) {
        Text(
            dayText,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp
        )
    }
}

@Composable
fun EmptyAlarmsView(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
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