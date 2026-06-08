package com.raflisalam.technicalassestment.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

fun getGenreColor(genreId: Int): Color = when (genreId) {
    28 -> Color(0xFFB71C1C)
    12 -> Color(0xFF1B5E20)
    16 -> Color(0xFFE65100)
    35 -> Color(0xFFF57F17)
    80 -> Color(0xFF0D47A1)
    99 -> Color(0xFF4E342E)
    18 -> Color(0xFF4A148C)
    10751 -> Color(0xFF880E4F)
    14 -> Color(0xFF004D40)
    36 -> Color(0xFFBF360C)
    27 -> Color(0xFF212121)
    10402 -> Color(0xFF0277BD)
    9648 -> Color(0xFF311B92)
    10749 -> Color(0xFFC62828)
    878 -> Color(0xFF006064)
    10770 -> Color(0xFF01579B)
    53 -> Color(0xFF37474F)
    10752 -> Color(0xFF33691E)
    37 -> Color(0xFF5D4037)
    else -> Color(0xFF455A64)
}

fun getGenreIcon(genreId: Int): ImageVector = when (genreId) {
    28 -> Icons.Filled.LocalFireDepartment
    12 -> Icons.Filled.Explore
    16 -> Icons.Filled.Palette
    35 -> Icons.Filled.EmojiEmotions
    80 -> Icons.Filled.Gavel
    99 -> Icons.Filled.VideoLibrary
    18 -> Icons.Filled.People
    10751 -> Icons.Filled.Favorite
    14 -> Icons.Filled.AutoAwesome
    36 -> Icons.Filled.AccountBalance
    27 -> Icons.Filled.DarkMode
    10402 -> Icons.Filled.MusicNote
    9648 -> Icons.Filled.Search
    10749 -> Icons.Filled.Favorite
    878 -> Icons.Filled.RocketLaunch
    10770 -> Icons.Filled.Tv
    53 -> Icons.Filled.Visibility
    10752 -> Icons.Filled.Shield
    37 -> Icons.Filled.Terrain
    else -> Icons.Filled.Movie
}