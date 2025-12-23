package com.lineup.app.ui.navigation

sealed class BottomNavItem(
    val title: String,
    val route: String
) {
    data object Match : BottomNavItem("매치", "match")
    data object MyPick : BottomNavItem("마이픽", "mypick")
    data object Stat : BottomNavItem("통계", "stat")
    data object Setting : BottomNavItem("설정", "setting")

    companion object {
        fun getAllItems() = listOf(Match, MyPick, Stat, Setting)

        fun getItemByRoute(route: String?): BottomNavItem? {
            return getAllItems().find { it.route == route }
        }
    }
}