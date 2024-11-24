package com.example.snoozeloo.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snoozeloo.R
import com.example.snoozeloo.ui.theme.appPrimaryColor
import com.example.snoozeloo.ui.theme.greyButtonColor
import com.example.snoozeloo.ui.theme.greyTextColor
import com.example.snoozeloo.viewmodel.AppViewModel

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
            horizontalArrangement = Arrangement.SpaceBetween
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

        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .matchParentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .wrapContentWidth(),
                    text = "Alarm Ringtone",
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .wrapContentWidth()
                        .clickable {
                            navToAlarmRingtonesScreen()
                        },
                    text = "Default",
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    color = greyTextColor
                )
            }
        }
    }
}