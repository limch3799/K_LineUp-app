package com.lineup.app

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.*
import com.lineup.app.ui.components.CustomBottomNavigation
import com.lineup.app.ui.screens.match.MatchScreen
import com.lineup.app.ui.screens.mypick.MyPickScreen
import com.lineup.app.ui.screens.stat.StatScreen
import com.lineup.app.ui.screens.setting.SettingScreen
import com.lineup.app.ui.theme.LineUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 알림 권한 요청 (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        enableEdgeToEdge()
        window.navigationBarColor = Color.parseColor("#FFFFFF")

        setContent {
            LineUpTheme {
                LineUpApp()
            }
        }
    }
}

@Composable
fun LineUpApp() {
    val navController = rememberNavController()

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    // 현재 라우트
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 현재 라우트에 따라 선택된 탭 변경
    LaunchedEffect(currentRoute) {
        selectedTabIndex = when (currentRoute) {
            "match" -> 0
            "mypick" -> 1
            "stat" -> 2
            "setting" -> 3
            else -> selectedTabIndex
        }
    }

    // 뒤로가기 핸들링 (필요시 특정 화면에서 추가)
    BackHandler(enabled = false) {
        navController.popBackStack()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "match",
            modifier = Modifier.fillMaxSize(),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            composable("match") {
                MatchScreen(navController = navController)
            }
            composable("mypick") {
                MyPickScreen(navController = navController)
            }
            composable("stat") {
                StatScreen(navController = navController)
            }
            composable("setting") {
                SettingScreen(navController = navController)
            }
        }

        // 바텀 네비게이션
        CustomBottomNavigation(
            selectedIndex = selectedTabIndex,
            onItemSelected = { index ->
                // 이미 선택된 탭을 다시 클릭하면 아무 동작 안 함
                if (selectedTabIndex != index) {
                    selectedTabIndex = index
                    val route = getRouteForIndex(index)
                    navController.navigate(route) {
                        popUpTo("match") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// 인덱스에 따른 라우트 반환
private fun getRouteForIndex(index: Int): String {
    return when (index) {
        0 -> "match"
        1 -> "mypick"
        2 -> "stat"
        3 -> "setting"
        else -> "match"
    }
}