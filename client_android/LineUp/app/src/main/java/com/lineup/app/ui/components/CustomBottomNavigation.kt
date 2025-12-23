package com.lineup.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lineup.app.R
import com.lineup.app.ui.navigation.BottomNavItem

@Composable
fun CustomBottomNavigation(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val navItems = BottomNavItem.getAllItems()
    val density = LocalDensity.current

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // 바텀 네비게이션 바 배경 및 경계선
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
        ) {
            val cornerRadius = with(density) { 25.dp.toPx() }
            val strokeWidth = with(density) { 0.3.dp.toPx() }

            // 배경
            val backgroundPath = Path().apply {
                addRoundRect(
                    RoundRect(
                        left = 0f,
                        top = 0f,
                        right = size.width,
                        bottom = size.height,
                        topLeftCornerRadius = CornerRadius(cornerRadius),
                        topRightCornerRadius = CornerRadius(cornerRadius)
                    )
                )
            }
            drawPath(path = backgroundPath, color = Color.White)

            // 상단 경계선 (둥근 모서리 포함)
            val borderPath = Path().apply {
                addRoundRect(
                    RoundRect(
                        left = strokeWidth / 2,
                        top = strokeWidth / 2,
                        right = size.width - strokeWidth / 2,
                        bottom = size.height,
                        topLeftCornerRadius = CornerRadius(cornerRadius),
                        topRightCornerRadius = CornerRadius(cornerRadius)
                    )
                )
            }
            drawPath(
                path = borderPath,
                color = Color(0xFFE0E0E0),
                style = Stroke(width = strokeWidth)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            navItems.forEachIndexed { index, item ->
                CustomNavItem(
                    item = item,
                    isSelected = index == selectedIndex,
                    onClick = { onItemSelected(index) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CustomNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = when (item.route) {
                    "match" -> if (isSelected) R.drawable.match_on else R.drawable.match_off
                    "mypick" -> if (isSelected) R.drawable.mypick_on else R.drawable.mypick_off
                    "stat" -> if (isSelected) R.drawable.stat_on else R.drawable.stat_off
                    "setting" -> if (isSelected) R.drawable.setting_on else R.drawable.setting_off
                    else -> R.drawable.match_off
                }
            ),
            contentDescription = item.title,
            modifier = Modifier.size(width = 48.dp, height = 48.dp)
        )
    }
}