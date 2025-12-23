package com.lineup.app.ui.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lineup.app.ui.theme.Primary
import com.lineup.app.ui.theme.Primary_sub
import com.lineup.app.ui.theme.font_gothic_5

@Composable
fun SettingScreen(navController: NavController) {
    var isNotificationEnabled by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 헤더
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(
                        start = 16.dp,
                        top = 36.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            ) {
                Text(
                    text = "설정",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(bottom = 90.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // 알림 섹션
                Text(
                    text = "알림",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "알림 켜기",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF222222)
                        )
                        Switch(
                            checked = isNotificationEnabled,
                            onCheckedChange = { isNotificationEnabled = it },
                            modifier = Modifier.size(width = 48.dp, height = 24.dp),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Primary_sub,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Color(0xFFBDBDBD)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 고객 지원 섹션
                Text(
                    text = "고객 지원",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clickable { /* 문의 방법 클릭 */ },
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "문의 방법",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF222222)
                        )
                        Text(
                            text = "›",
                            fontSize = 22.sp,
                            color = Color(0xFF999999)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clickable { /* 앱 정보 클릭 */ },
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "앱 정보",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF222222)
                        )
                        Text(
                            text = "›",
                            fontSize = 22.sp,
                            color = Color(0xFF999999)
                        )
                    }
                }

                // 나머지 공간을 회색으로 채움
                Spacer(modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5)))
            }
        }
    }
}