package com.lineup.app.ui.screens.match.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lineup.app.R
import com.lineup.app.data.mapper.PlayerMapper
import com.lineup.app.data.mapper.TeamLogoMapper
import com.lineup.app.data.model.Match

@Composable
fun PlayerMatchGroup(
    playerIds: List<String>,
    matches: List<Match>,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    onLineupToggle: (String, Boolean) -> Unit,
    isMyPick: Boolean = false,
    modifier: Modifier = Modifier
) {
    val playerNames = playerIds.mapNotNull {
        PlayerMapper.getPlayerInfo(it)?.name
    }.joinToString(", ")

    val playerTeam = playerIds.firstOrNull()?.let {
        PlayerMapper.getPlayerInfo(it)?.team
    } ?: ""

    val teamLogo = TeamLogoMapper.getTeamLogo(playerTeam)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    if (isExpanded) {
                        RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    } else {
                        RoundedCornerShape(12.dp)
                    }
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onToggleExpanded()
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_primary),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = playerNames,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )

                if (teamLogo != null) {
                    Image(
                        painter = painterResource(id = teamLogo),
                        contentDescription = "$playerTeam 로고",
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = playerTeam,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = if (isExpanded) {
                        Icons.Filled.KeyboardArrowUp
                    } else {
                        Icons.Filled.KeyboardArrowDown
                    },
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )
                    .background(Color.White)
            ) {
                matches.forEachIndexed { index, match ->
                    MatchScreenListItem(
                        match = match,
                        onLineupToggle = onLineupToggle,
                        isMyPick = isMyPick
                    )

                    if (index < matches.lastIndex) {
                        Divider(
                            color = Color(0xFFE0E0E0),
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        }
    }
}