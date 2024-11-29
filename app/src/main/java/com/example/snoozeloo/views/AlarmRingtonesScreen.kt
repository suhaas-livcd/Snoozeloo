package com.example.snoozeloo.views

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snoozeloo.R
import com.example.snoozeloo.data.AppUiState
import com.example.snoozeloo.data.SnoozelooAlarm
import com.example.snoozeloo.data.SnoozelooAlarmEvents

@Composable
fun AlarmRingtoneScreen(
    uiState: AppUiState,
    modifier: Modifier = Modifier,
    navBackToAlarmSettingsScreen: () -> Unit,
    onEvent: (SnoozelooAlarmEvents) -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        HeaderRow(navBackToAlarmSettingsScreen)

        // Wrap in LazyColumn
        RingtonesList(uiState.createAlarm, uiState.ringtoneList, onEvent)
    }
}

@Composable
private fun RingtonesList(
    alarm: SnoozelooAlarm,
    ringtones: List<Pair<String, Uri>>,
    onEvent: (SnoozelooAlarmEvents) -> Unit
) {
    Column(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {
        LazyColumn() {
            items(ringtones) { ringtone ->
                RingtoneListItem(
                    alarm,
                    ringtone,
                    onEvent = onEvent,
                )
            }
        }

    }
}

@Composable
private fun HeaderRow(navBackToAlarmSettingsScreen: () -> Unit) {
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

@Composable
fun RingtoneListItem(
    alarm: SnoozelooAlarm,
    ringtone: Pair<String, Uri>,
    onEvent: (SnoozelooAlarmEvents) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onEvent(
                    SnoozelooAlarmEvents.SelectAlarmRingtone(
                        ringtone
                    )
                )
            },
        shape = RoundedCornerShape(16.dp), colors = CardColors(
            containerColor = Color.White,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(
                    if (ringtone.first == "Silent") {
                        R.drawable.ic_ringtone_silent
                    } else {
                        R.drawable.ic_ringtone_sound
                    }
                ), contentDescription = "Alarm Icon"
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 6.dp, end = 6.dp),
                text = ringtone.first,
                fontWeight = FontWeight.W600,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (alarm.alarmRingtoneURI == ringtone.second.toString()) {
                Image(
                    imageVector = ImageVector.vectorResource(
                        R.drawable.ic_ringtone_selected
                    ), contentDescription = "Alarm Icon"
                )
            }
        }
    }
}