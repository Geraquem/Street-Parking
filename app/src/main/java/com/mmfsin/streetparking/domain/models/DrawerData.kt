package com.mmfsin.streetparking.domain.models

import com.mmfsin.streetparking.R

data class DrawerData(
    val text: Int,
    val icon: Int,
    val index: Int,
    val direction: String
)

enum class DrawerDirection(val text: Int, val icon: Int, val index: Int) {
    LEAVING_SPOT(
        text = R.string.nav_drawer_leave_spot,
        icon = R.drawable.ic_parking,
        index = 0
    ),
    SEARCHING_PLACE(
        text = R.string.nav_drawer_search_spot,
        icon = R.drawable.ic_map_spot,
        index = 1
    ),
    WHERE_PARKED(
        text = R.string.nav_drawer_where_parked,
        icon = R.drawable.ic_car,
        index = 2
    );

    companion object {
        fun getDrawerItems(): List<DrawerData> = entries.map { it.toDrawerData() }
        fun getDrawerIndex(name: String): Int = entries.find { it.name == name }?.index ?: -1
    }
}

fun DrawerDirection.toDrawerData() = DrawerData(
    text = text,
    icon = icon,
    index = index,
    direction = this.name
)
