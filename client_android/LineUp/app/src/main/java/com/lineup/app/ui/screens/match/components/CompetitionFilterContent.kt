package com.lineup.app.ui.screens.match.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun CompetitionFilterContent(
    selectedCompetition: String,
    onCompetitionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val competitions = listOf(
        "전체",
        "프리미어리그",
        "분데스리가",
        "리그 1",
        "챔피언스리그",
        "유로파리그",
        "국가대표"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        competitions.forEach { competition ->
            CompetitionChip(
                text = competition,
                isSelected = selectedCompetition == competition,
                onClick = { onCompetitionSelected(competition) }
            )
        }
    }
}

@Composable
private fun CompetitionChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .then(
                if (isSelected) {
                    Modifier
                } else {
                    Modifier.border(
                        width = 1.dp,
                        color = Color(0xFFCCCCCC),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Image(
                painter = painterResource(id = R.drawable.bg_primary),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else Color(0xFF999999),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        )
    }
}