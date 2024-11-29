package com.example.snoozeloo

import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.snoozeloo.ui.theme.appPrimaryColor
import com.example.snoozeloo.ui.theme.appTertiaryColor
import timber.log.Timber

class FullScreenActivity : ComponentActivity() {
    var alarmName: String? = null
    var timeHours: Int? = null
    var timeMinutes: Int? = null
    var alarmURI: String? = null
    var ringtoneManager: RingtoneManager? = null
    private var currentRingtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
        val bundle = intent.extras

        if (bundle != null) {
            alarmName = bundle.getString("EXTRA_NAME")
            timeHours = bundle.getInt("EXTRA_TIME_HOURS")
            timeMinutes = bundle.getInt("EXTRA_TIME_MINUTES")
            alarmURI = bundle.getString("EXTRA_ALARM_RINGTONE_URI")
            Timber.d("onCreate : $alarmName, $timeHours:$timeMinutes, $alarmURI")
        }
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.alarm_icon),
                        contentDescription = "Info",
                        tint = appPrimaryColor,
                        modifier = Modifier.size(62.dp)
                    )

                    Text(
                        text = "$timeHours:$timeMinutes",
                        fontSize = 82.sp,
                        color = appPrimaryColor,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(top = 24.dp)
                    )

                    Text(
                        text = alarmName ?: "Alarm",
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.padding(top = 10.dp)
                    )

                    Button(
                        modifier = Modifier.padding(top = 24.dp),
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = appPrimaryColor,
                            contentColor = Color.White
                        ),
                        onClick = {
                            currentRingtone?.stop()
                            finishAndRemoveTask()
                        }) {
                        Text(
                            text = "Turn Off",
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.W600,
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        modifier = Modifier.padding(top = 10.dp),
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = appTertiaryColor,
                            contentColor = appPrimaryColor
                        ),
                        border = BorderStroke(1.dp, appPrimaryColor),
                        onClick = {
                            currentRingtone?.stop()
                            finishAndRemoveTask()
                        }) {
                        Text(
                            text = "Snooze for 5 min",
                            fontSize = 24.sp,
                            color = appPrimaryColor,
                            fontWeight = FontWeight.W600,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        ringtoneManager = RingtoneManager(this)
        currentRingtone = RingtoneManager.getRingtone(this, alarmURI?.toUri())
        currentRingtone?.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        currentRingtone?.stop()
        currentRingtone = null
        Timber.d("onDestroy")
    }
}