package com.example.snoozeloo.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.snoozeloo.ui.theme.appPrimaryColor
import com.example.snoozeloo.ui.theme.appSecondaryColor
import com.example.snoozeloo.ui.theme.appTertiaryColor
import com.example.snoozeloo.ui.theme.greyButtonColor
import com.example.snoozeloo.ui.theme.greyTextColor
import com.example.snoozeloo.ui.theme.textInputTimeBgColor
import com.example.snoozeloo.viewmodel.AppViewModel
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSettingsScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier,
    navBackToAlarmsScreen: () -> Unit,
    navToAlarmRingtonesScreen: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
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
                })
            Button(
                onClick = { navBackToAlarmsScreen() },
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

        // Alarm Time Input
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
                var label1 by remember { mutableStateOf("") }

                TextField(
                    value = label1,
                    onValueChange = { if (it.length <= 2) label1 = it },
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

                var label2 by remember { mutableStateOf("") }

                TextField(
                    value = label2,
                    onValueChange = { if (it.length <= 2) label2 = it },
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

        // Alarm Name
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
                    var inputText by remember { mutableStateOf("") } // State to hold text field input

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
                                value = inputText,
                                onValueChange = { inputText = it },
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
                            // Show dialog

                        },
                    text = "Work",
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    color = greyTextColor
                )


            }
        }


        // Repeat Days
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
                AlarmRepeatDays(dayText = "Mo")
                AlarmRepeatDays(dayText = "Tu")
                AlarmRepeatDays(dayText = "We")
                AlarmRepeatDays(dayText = "Th")
                AlarmRepeatDays(dayText = "Fr")
                AlarmRepeatDays(dayText = "Sa")
                AlarmRepeatDays(dayText = "Su")
            }
        }


        // Sound - Ringtone
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

        // Alarm Volume
        // Sound - Ringtone
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
            var sliderPosition by remember { mutableFloatStateOf(0f) }
            Slider(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
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


        // Vibrate
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
                    checked = true, onCheckedChange = {
                        Timber.d("Alarm is enabled : $it")
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
}