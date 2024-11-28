package com.example.snoozeloo.views

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

        // Wrap in LazyColumn
        Column(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {
            RingtoneListItem(isSilent = true, isSelected = false, ringtoneName = "Silent")
            RingtoneListItem(
                isSilent = false,
                isSelected = true,
                ringtoneName = "Default (BrightMorning)"
            )
        }


    }
}

@Composable
fun RingtoneListItem(isSilent: Boolean = false, isSelected: Boolean = false, ringtoneName: String) {
    Card(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .height(50.dp),
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
                    if (isSilent) {
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
                text = ringtoneName, fontWeight = FontWeight.W600,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (isSelected) {
                Image(
                    imageVector = ImageVector.vectorResource(
                        R.drawable.ic_ringtone_selected
                    ), contentDescription = "Alarm Icon"
                )
            }
        }
    }
}