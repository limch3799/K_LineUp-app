package com.lineup.app.ui.screens.match.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.lineup.app.data.mapper.PlayerMapper
import com.lineup.app.data.model.Match
import com.lineup.app.ui.theme.Primary
import com.lineup.app.ui.theme.Primary_sub
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LineupNotificationDialog(
    match: Match,
    playerId: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val playerInfo = PlayerMapper.getPlayerInfo(playerId)
    val playerName = playerInfo?.name ?: "알 수 없는 선수"

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. 일시 정보
                match.matchTime?.let { timestamp ->
                    val dateFormat = SimpleDateFormat("MM월 dd일 (E) HH:mm", Locale.KOREA)
                    Text(
                        text = dateFormat.format(timestamp.toDate()),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF666666)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 2. 팀 vs 팀
                Text(
                    text = "${match.homeTeam} vs ${match.awayTeam}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 3. 메시지 영역 (총 3줄 구성)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // 첫 번째 줄: [이름 배지] 의 해당 경기의
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            color = Primary_sub,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = playerName,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = " 의 해당 경기",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF333333)
                        )
                    }

                    // 두 번째 줄
                    Text(
                        text = "선발 여부 알림을",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF333333)
                    )

                    // 세 번째 줄
                    Text(
                        text = "받아보시겠습니까?",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF333333)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 4. 버튼 영역
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFF999999)),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "취소",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF999999)
                        )
                    }

                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "확인",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}