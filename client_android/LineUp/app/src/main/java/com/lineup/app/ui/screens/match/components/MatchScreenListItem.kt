package com.lineup.app.ui.screens.match.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lineup.app.R
import com.lineup.app.data.mapper.TeamLogoMapper
import com.lineup.app.data.model.Match
import com.lineup.app.ui.screens.mypick.components.MyPickRemoveDialog
import com.lineup.app.ui.theme.Primary_sub
import com.lineup.app.ui.theme.TextTime
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MatchScreenListItem(
    match: Match,
    onLineupToggle: (String, Boolean) -> Unit,
    isMyPick: Boolean = false,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showRemoveDialog by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    // 추가 다이얼로그
    if (showAddDialog) {
        LineupNotificationDialog(
            match = match,
            playerId = match.koreanPlayerIds.firstOrNull() ?: "",
            onConfirm = {
                showAddDialog = false
                onLineupToggle(match.documentId, true)
            },
            onDismiss = {
                showAddDialog = false
            }
        )
    }

    // 제거 다이얼로그
    if (showRemoveDialog) {
        MyPickRemoveDialog(
            match = match,
            playerId = match.koreanPlayerIds.firstOrNull() ?: "",
            onConfirm = {
                showRemoveDialog = false
                onLineupToggle(match.documentId, false)
            },
            onDismiss = {
                showRemoveDialog = false
            }
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompetitionBadge(competition = match.competition)
                Text(text = "·", fontSize = 12.sp, color = Color(0xFF999999))
                match.matchTime?.let { timestamp ->
                    val dateFormat = SimpleDateFormat("MM월 dd일 (E) HH:mm", Locale.KOREA)
                    Text(
                        text = dateFormat.format(timestamp.toDate()),
                        fontSize = 12.sp,
                        color = TextTime
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamInfo(
                    teamName = match.homeTeam,
                    isHome = true,
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 0.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    MatchStatusBadge(status = match.status)

                    if (match.status == "finished" || match.status == "live") {
                        Text(
                            text = "${match.homeScore} : ${match.awayScore}",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (match.status == "live") Color(0xFFFF5252) else Color(0xFF222222)
                        )
                    } else {
                        Text(
                            text = "VS",
                            fontSize = 13.sp,
                            lineHeight = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF999999)
                        )
                    }
                }

                TeamInfo(
                    teamName = match.awayTeam,
                    isHome = false,
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Image(
            painter = painterResource(
                id = if (match.isLineupNotificationOn) R.drawable.line_up_on else R.drawable.line_up_off
            ),
            contentDescription = "라인업 알림",
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 12.dp)
                .size(36.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                    if (!match.isLineupNotificationOn) {
                        // OFF -> ON: 다이얼로그 표시
                        showAddDialog = true
                    } else {
                        // ON -> OFF: 다이얼로그 표시 (Match, MyPick 모두)
                        showRemoveDialog = true
                    }
                }
        )
    }
}

@Composable
private fun CompetitionBadge(competition: String) {
    val color_epl = Color(0xFF3D195B)
    val color_bundesliga = Color(0xFFD3010D)
    val color_league1 = Color(0xFF091C3E)
    val color_champions = Color(0xFF010362)
    val color_europa = Color(0xFFFC7100)

    val backgroundColor = when (competition) {
        "프리미어리그" -> color_epl
        "분데스리가" -> color_bundesliga
        "리그 1" -> color_league1
        "챔피언스리그" -> color_champions
        "유로파리그" -> color_europa
        else -> Color(0xFF666666)
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        Text(
            text = competition,
            fontSize = 9.sp,
            lineHeight = 9.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun TeamInfo(
    teamName: String,
    isHome: Boolean,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal
) {
    Row(
        modifier = modifier,
        horizontalArrangement = if (horizontalAlignment == Alignment.End)
            Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val logoRes = TeamLogoMapper.getTeamLogo(teamName)

        if (!isHome) {
            TeamLogo(teamName, logoRes)
            Spacer(modifier = Modifier.width(6.dp))
        }

        val nameLengthWithoutSpace = teamName.replace(" ", "").length
        val fontSize = if (nameLengthWithoutSpace >= 7) 10.sp else 12.sp

        Text(
            text = teamName,
            fontSize = fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF222222)
        )

        if (isHome) {
            Spacer(modifier = Modifier.width(6.dp))
            TeamLogo(teamName, logoRes)
        }
    }
}

@Composable
private fun TeamLogo(teamName: String, logoRes: Int?) {
    if (logoRes != null) {
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "$teamName 로고",
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = teamName.firstOrNull()?.toString() ?: "?",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF666666)
            )
        }
    }
}

@Composable
private fun MatchStatusBadge(status: String) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = when (status) {
            "live" -> Color(0xFFFF5252)
            "scheduled" -> Primary_sub
            "finished" -> Color(0xFF9E9E9E)
            else -> Color(0xFF2196F3)
        }
    ) {
        Text(
            text = when (status) {
                "live" -> "경기중"
                "scheduled" -> "예정"
                "finished" -> "종료"
                else -> status
            },
            fontSize = 8.sp,
            lineHeight = 8.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}