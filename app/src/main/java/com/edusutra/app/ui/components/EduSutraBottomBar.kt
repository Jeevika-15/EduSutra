package com.edusutra.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.navigation.Screen
import com.edusutra.app.ui.theme.PrimaryBlue
import com.edusutra.app.ui.theme.SlateMuted

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val iconGlyph: String
) {
    data object Home : BottomNavItem(Screen.Home.route, "Home", "🏠")
    data object Learn : BottomNavItem(Screen.Courses.route, "Learn", "📖")
    data object Progress : BottomNavItem(Screen.Progress.route, "Progress", "📈")
    data object Profile : BottomNavItem(Screen.Profile.route, "Profile", "👤")
}

@Composable
fun EduSutraBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Learn,
        BottomNavItem.Progress,
        BottomNavItem.Profile
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        color = Color.White,
        tonalElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route ||
                        (item == BottomNavItem.Learn && (currentRoute?.startsWith(Screen.CourseDetails.route) == true ||
                                currentRoute?.startsWith(Screen.LearningPath.route) == true ||
                                currentRoute == Screen.Lesson.route ||
                                currentRoute == Screen.Simulation.route))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { onNavigate(item.route) }
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.iconGlyph,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) PrimaryBlue else SlateMuted
                    )
                }
            }
        }
    }
}
