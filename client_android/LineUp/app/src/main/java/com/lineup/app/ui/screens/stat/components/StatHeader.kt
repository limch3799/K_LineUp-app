package com.lineup.app.ui.screens.stat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.lineup.app.R
import com.lineup.app.ui.theme.font_gothic_3
import com.lineup.app.ui.theme.font_gothic_4
import com.lineup.app.ui.theme.font_gothic_5
import com.lineup.app.ui.theme.font_paperlogy_6
import com.lineup.app.ui.theme.font_paperlogy_9

@Composable
fun StatHeader(
    modifier: Modifier = Modifier,
    logoWidth: Dp = 80.dp,
    logoHeight: Dp = 32.dp
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
            .shadow(
                elevation = 4.dp,
                clip = false
            ),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 로고 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        top = 36.dp,
                        end = 12.dp,
                        bottom = 16.dp
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_black),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(width = logoWidth, height = logoHeight)
                )
            }

            // 선수 기록 텍스트 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 18.dp,
                        bottom = 8.dp
                    )
            ) {
                Text(
                    text = "선수 기록",
                    fontFamily = font_gothic_4,
                    fontSize = 16.sp,

                    color = Color(0xFF21272A)
                )
            }
        }
    }
}