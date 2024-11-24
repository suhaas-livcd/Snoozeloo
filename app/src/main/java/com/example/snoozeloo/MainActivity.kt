package com.example.snoozeloo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snoozeloo.ui.theme.SnoozelooTheme
import com.example.snoozeloo.viewmodel.AppViewModel
import com.example.snoozeloo.views.AlarmRingtoneScreen
import com.example.snoozeloo.views.AlarmSettingsScreen
import com.example.snoozeloo.views.AlarmsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val appViewModel by viewModels<AppViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnoozelooTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    val navController: NavHostController = rememberNavController()
                    NavHost(navController, startDestination = NavRoute.ALARMS_SCREEN) {
                        composable(NavRoute.ALARMS_SCREEN) {
                            AlarmsScreen(
                                viewModel = appViewModel,
                                modifier = Modifier.padding(innerPadding),
                                navToAlarmSettingsScreen = {
                                    navController.navigate(NavRoute.ALARM_SETTINGS_SCREEN)
                                })
                        }

                        composable(NavRoute.ALARM_SETTINGS_SCREEN) {
                            AlarmSettingsScreen(
                                viewModel = appViewModel,
                                modifier = Modifier.padding(innerPadding),
                                navBackToAlarmsScreen = {
                                    navController.popBackStack()
                                },
                                navToAlarmRingtonesScreen = {
                                    navController.navigate(NavRoute.ALARM_RINGTONE_SCREEN)
                                }
                            )
                        }

                        composable(NavRoute.ALARM_RINGTONE_SCREEN) {
                            AlarmRingtoneScreen(
                                viewModel = appViewModel,
                                modifier = Modifier.padding(innerPadding),
                                navBackToAlarmSettingsScreen = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

object NavRoute {
    const val ALARMS_SCREEN = "alarmsScreen"
    const val ALARM_SETTINGS_SCREEN = "alarmSettingsScreen"
    const val ALARM_RINGTONE_SCREEN = "alarmRingtonesScreen"
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SnoozelooTheme {}
}

