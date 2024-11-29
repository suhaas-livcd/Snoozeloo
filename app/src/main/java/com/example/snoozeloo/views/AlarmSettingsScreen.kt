package com.example.snoozeloo.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snoozeloo.R
import com.example.snoozeloo.data.SnoozelooAlarm
import com.example.snoozeloo.data.SnoozelooAlarmEvents
import com.example.snoozeloo.data.WeekDays
import com.example.snoozeloo.ui.theme.appPrimaryColor
import com.example.snoozeloo.ui.theme.appSecondaryColor
import com.example.snoozeloo.ui.theme.appTertiaryColor
import com.example.snoozeloo.ui.theme.greyButtonColor
import com.example.snoozeloo.ui.theme.greyTextColor
import com.example.snoozeloo.ui.theme.textInputTimeBgColor
import timber.log.Timber

@Composable
fun AlarmSettingsScreen(
    alarmState: SnoozelooAlarm = SnoozelooAlarm(),
    modifier: Modifier = Modifier,
    navBackToAlarmsScreen: () -> Unit,
    navToAlarmRingtonesScreen: () -> Unit,
    onEvent: (SnoozelooAlarmEvents) -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        // Show Close and Save Button
        HeaderRow(navBackToAlarmsScreen, onEvent = onEvent)

        // Alarm Time Input
        AlarmTimeInput(alarmState, onEvent)

        // Alarm Name
        AlarmName(alarmState, onEvent)

        // Repeat Days
        AlarmRepeatDays(alarmState, onEvent)

        // Sound - Ringtone
        RingtoneOptions(navToAlarmRingtonesScreen, alarmState, onEvent)

        // Alarm Volume
        // Sound - Ringtone
        ShowVolumeSlider(alarmState, onEvent)


        // Vibrate
        ShowVibrateOptions(alarmState, onEvent)
    }
}

@Composable
private fun AlarmTimeInput(alarmState: SnoozelooAlarm, onEvent: (SnoozelooAlarmEvents) -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), colors = CardColors(
            containerColor = Color.White,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(95.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            TextField(
                value = String.format("%02d", alarmState.timeHours),
                onValueChange = {
                    if (it.isNotBlank() && it.toInt() <= 23) {
                        onEvent(SnoozelooAlarmEvents.SetAlarmTimeHours(it.toInt()))
                    } else if (it.length > 2) {
                        onEvent(SnoozelooAlarmEvents.SetAlarmTimeHours(it.last().digitToInt()))
                    }
                },
                placeholder = {
                    Text(
                        text = "00",
                        modifier = Modifier.padding(start = 12.dp),
                        fontSize = 52.sp,
                        fontWeight = FontWeight.W500,
                        color = greyTextColor,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                },
                modifier = Modifier
                    .width(128.dp)
                    .fillMaxHeight(),
                singleLine = true,
                textStyle = TextStyle.Default.copy(
                    fontSize = 52.sp,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.colors()
                    .copy(
                        unfocusedPlaceholderColor = greyTextColor,
                        focusedTextColor = appPrimaryColor,
                        unfocusedTextColor = appPrimaryColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = textInputTimeBgColor,
                        focusedContainerColor = textInputTimeBgColor,
                        cursorColor = Color.Transparent
                    ),
                shape = RoundedCornerShape(16.dp)
            )

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.time_separator),
                contentDescription = "Time Separator",
                modifier = Modifier
                    .width(24.dp)
                    .height(20.dp)
                    .padding()
            )


            TextField(
                value = String.format("%02d", alarmState.timeMinutes),

                onValueChange = {
                    if (it.isNotBlank() && it.toInt() <= 59) {
                        onEvent(SnoozelooAlarmEvents.SetAlarmTimeMinutes(it.toInt()))
                    } else if (it.length > 2) {
                        onEvent(SnoozelooAlarmEvents.SetAlarmTimeMinutes(it.last().digitToInt()))
                    }
                },
                placeholder = {
                    Text(
                        text = "00",
                        modifier = Modifier.padding(start = 12.dp),
                        fontSize = 52.sp,
                        fontWeight = FontWeight.W500,
                        color = greyTextColor,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                },
                modifier = Modifier
                    .width(128.dp)
                    .fillMaxHeight(),
                singleLine = true,
                textStyle = TextStyle.Default.copy(
                    fontSize = 52.sp,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.colors()
                    .copy(
                        unfocusedPlaceholderColor = greyTextColor,
                        focusedTextColor = appPrimaryColor,
                        unfocusedTextColor = appPrimaryColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = textInputTimeBgColor,
                        focusedContainerColor = textInputTimeBgColor,
                        cursorColor = Color.Transparent
                    ),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Composable
private fun AlarmName(alarmState: SnoozelooAlarm, onEvent: (SnoozelooAlarmEvents) -> Unit) {
    Card(
        modifier = Modifier.padding(top = 16.dp),
        shape = RoundedCornerShape(16.dp), colors = CardColors(
            containerColor = Color.White,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = "Alarm Name",
                fontWeight = FontWeight.W600,
                fontSize = 16.sp
            )
            var showDialog by remember { mutableStateOf(false) } // State to control dialog visibility
            if (showDialog) {

                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    }, // Dismiss dialog on outside click
                    title = {
                        Text(
                            "Alarm Name", fontWeight = FontWeight.W600,
                            fontSize = 16.sp
                        )
                    },
                    text = {
                        TextField(
                            modifier = Modifier
                                .border(
                                    BorderStroke(width = 1.dp, color = greyButtonColor),
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            value = alarmState.name,
                            onValueChange = { onEvent(SnoozelooAlarmEvents.SetAlarmNameDialogText(it)) },
                            colors = TextFieldDefaults.colors().copy(
                                cursorColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                            ),
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W500
                            )
                        )
                    },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors().copy(
                                containerColor = appPrimaryColor,
                                contentColor = Color.White
                            ),
                            onClick = {
                                showDialog = false
                            }) {
                            Text("Save")
                        }
                    },
                    containerColor = Color.White
                )
            }

            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable {
                        showDialog = true
                    },
                text = alarmState.name,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                color = greyTextColor
            )
        }
    }
}

@Composable
private fun AlarmRepeatDays(alarmState: SnoozelooAlarm, onEvent: (SnoozelooAlarmEvents) -> Unit) {
    Card(
        modifier = Modifier.padding(top = 16.dp),
        shape = RoundedCornerShape(16.dp), colors = CardColors(
            containerColor = Color.White,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = "Repeat",
                fontWeight = FontWeight.W600,
                fontSize = 16.sp
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            AlarmRepeatDayItemEditable(
                dayText = "Mo",
                alarmState = alarmState,
                onEvent = onEvent,
                selectedDay = WeekDays.MONDAY
            )
            AlarmRepeatDayItemEditable(
                dayText = "Tu",
                alarmState = alarmState,
                onEvent = onEvent,
                selectedDay = WeekDays.TUESDAY
            )
            AlarmRepeatDayItemEditable(
                dayText = "We",
                alarmState = alarmState,
                onEvent = onEvent,
                selectedDay = WeekDays.WEDNESDAY
            )
            AlarmRepeatDayItemEditable(
                dayText = "Th",
                alarmState = alarmState,
                onEvent = onEvent,
                selectedDay = WeekDays.THURSDAY
            )
            AlarmRepeatDayItemEditable(
                dayText = "Fr",
                alarmState = alarmState,
                onEvent = onEvent,
                selectedDay = WeekDays.FRIDAY
            )
            AlarmRepeatDayItemEditable(
                dayText = "Sa",
                alarmState = alarmState,
                onEvent = onEvent,
                selectedDay = WeekDays.SATURDAY
            )
            AlarmRepeatDayItemEditable(
                dayText = "Su",
                alarmState = alarmState,
                onEvent = onEvent,
                selectedDay = WeekDays.SUNDAY
            )
        }
    }
}

@Composable
fun AlarmRepeatDayItemEditable(
    dayText: String,
    alarmState: SnoozelooAlarm,
    onEvent: (SnoozelooAlarmEvents) -> Unit,
    selectedDay: Int

) {
    val isSelected = WeekDays.isDaySelected(alarmState.repeatDays, selectedDay)
    Button(
        modifier = Modifier
            .width(38.dp)
            .height(26.dp),
        colors = ButtonColors(
            containerColor = if (isSelected) appPrimaryColor else appTertiaryColor,
            contentColor = if (isSelected) Color.White else Color.Black,
            disabledContentColor = Color.Black,
            disabledContainerColor = appTertiaryColor
        ),
        onClick = {
            Timber.d("Click on $dayText")
            onEvent(
                SnoozelooAlarmEvents.SetRepeatDay(
                    dayText = selectedDay,
                    isDaySelected = !isSelected
                )
            )
        },
        contentPadding = PaddingValues(start = 9.dp, end = 9.dp),
        enabled = true
    ) {
        Text(
            dayText,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun RingtoneOptions(
    navToAlarmRingtonesScreen: () -> Unit,
    alarmState: SnoozelooAlarm,
    onEvent: (SnoozelooAlarmEvents) -> Unit
) {
    Card(
        modifier = Modifier.padding(top = 16.dp),
        shape = RoundedCornerShape(16.dp), colors = CardColors(
            containerColor = Color.White,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = "Alarm Ringtone",
                fontWeight = FontWeight.W600,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable {
                        navToAlarmRingtonesScreen()
                    },
                text = "Default",
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                color = greyTextColor
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ShowVolumeSlider(alarmState: SnoozelooAlarm, onEvent: (SnoozelooAlarmEvents) -> Unit) {
    Card(
        modifier = Modifier.padding(top = 16.dp),
        shape = RoundedCornerShape(16.dp), colors = CardColors(
            containerColor = Color.White,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = "Alarm Volume",
                fontWeight = FontWeight.W600,
                fontSize = 16.sp
            )
        }
        Slider(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                .fillMaxWidth(),
            value = alarmState.alarmVolume,
            onValueChange = { onEvent(SnoozelooAlarmEvents.SetAlarmVolume(it)) },
            thumb = {
                Box(modifier = Modifier.size(5.dp)) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = appPrimaryColor,
                            radius = 40f,
                            center = Offset(10F, 20F)
                        )
                    }
                }
            },
            colors = SliderDefaults.colors().copy(
                thumbColor = appPrimaryColor,
                activeTrackColor = appPrimaryColor,
                inactiveTrackColor = appTertiaryColor,
            )
        )
    }
}

@Composable
private fun ShowVibrateOptions(
    alarmState: SnoozelooAlarm,
    onEvent: (SnoozelooAlarmEvents) -> Unit
) {
    Card(
        modifier = Modifier.padding(top = 16.dp),
        shape = RoundedCornerShape(16.dp), colors = CardColors(
            containerColor = Color.White,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = "Vibrate",
                fontWeight = FontWeight.W600,
                fontSize = 16.sp
            )

            Switch(
                checked = alarmState.vibrate, onCheckedChange = {
                    Timber.d("Alarm is enabled : $it")
                    onEvent(SnoozelooAlarmEvents.SetVibrateIsEnabled(it))
                }, colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = appPrimaryColor,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = appSecondaryColor,
                    uncheckedBorderColor = appSecondaryColor
                )
            )
        }
    }
}

@Composable
private fun HeaderRow(navBackToAlarmsScreen: () -> Unit, onEvent: (SnoozelooAlarmEvents) -> Unit) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.close_icon),
            contentDescription = "Close Icon",
            modifier = Modifier.clickable {
                navBackToAlarmsScreen()
                onEvent(SnoozelooAlarmEvents.CancelAlarm)
            })
        Button(
            onClick = {
                navBackToAlarmsScreen()
                onEvent(SnoozelooAlarmEvents.SaveAlarm)
            },
            shape = CircleShape,
            colors = ButtonColors(
                contentColor = Color.White,
                containerColor = appPrimaryColor,
                disabledContainerColor = greyButtonColor,
                disabledContentColor = Color.White
            )
        ) {
            Text("Save", color = Color.White, fontWeight = FontWeight.W600, fontSize = 16.sp)
        }
    }
}