package com.edusutra.app.ui.screens.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.data.local.entity.Course
import com.edusutra.app.ui.components.BadgeTag
import com.edusutra.app.ui.components.EduSutraTopBar
import com.edusutra.app.ui.components.FilterChipItem
import com.edusutra.app.ui.theme.*

@Composable
fun CourseCatalogueScreen(
    courses: List<Course>,
    onCourseClick: (Int) -> Unit,
    onSearch: (String) -> Unit,
    onMyLearningClick: () -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("My syllabus") }

    val filters = listOf("My syllabus", "NPTEL", "SWAYAM", "YouTube", "Coursera", "Free")

    val filteredCourses = courses.filter { course ->
        val matchesSearch = searchText.isBlank() ||
                course.title.contains(searchText, ignoreCase = true) ||
                course.description.contains(searchText, ignoreCase = true)

        val matchesFilter = when (selectedFilter) {
            "My syllabus" -> true
            "NPTEL" -> course.instructor.contains("Smith", ignoreCase = true) || course.title.contains("Data", ignoreCase = true)
            "SWAYAM" -> course.category.contains("Computer", ignoreCase = true)
            "YouTube" -> course.category.contains("Web", ignoreCase = true)
            "Coursera" -> course.category.contains("Artificial", ignoreCase = true)
            "Free" -> true
            else -> true
        }

        matchesSearch && matchesFilter
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Explore courses",
            subtitle = "Catalogue",
            actionText = "My learning",
            onActionClick = onMyLearningClick
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // Search Bar
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    onSearch(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search courses...", fontSize = 13.sp) },
                leadingIcon = {
                    Text("🔍", fontSize = 14.sp)
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = SlateBorder,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filters) { filter ->
                    FilterChipItem(
                        text = filter,
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Courses List
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (filteredCourses.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "No courses found matching \"$searchText\".",
                            modifier = Modifier.padding(20.dp),
                            color = SlateMuted,
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                items(filteredCourses, key = { it.courseId }) { course ->
                    val matchPercent = when (course.courseId % 3) {
                        1 -> "95% MATCH"
                        2 -> "48%"
                        else -> "88% MATCH"
                    }

                    val source = when (course.courseId % 4) {
                        1 -> "NPTEL"
                        2 -> "Edu Sutra"
                        3 -> "YouTube"
                        else -> "SWAYAM"
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCourseClick(course.courseId) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Top Row: Title + Match Badge
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = course.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Navy900,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                BadgeTag(
                                    text = matchPercent,
                                    containerColor = if (matchPercent.contains("95")) SuccessGreenSoft else BlueBadge,
                                    contentColor = if (matchPercent.contains("95")) SuccessGreen else PrimaryBlue
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            // Meta info
                            Text(
                                text = "$source · ${course.duration} · 4.8★ · ${course.difficulty}",
                                fontSize = 12.sp,
                                color = SlateMuted
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = course.description,
                                fontSize = 12.sp,
                                color = Navy700,
                                maxLines = 2,
                                lineHeight = 17.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Tags and Action Button
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    BadgeTag("PATH", SlateBorderSubtle, Navy700)
                                    BadgeTag("QUIZZES", SlateBorderSubtle, Navy700)
                                    BadgeTag("NOTES PDF", SlateBorderSubtle, Navy700)
                                }

                                Button(
                                    onClick = { onCourseClick(course.courseId) },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = if (course.courseId == 2) "Continue" else "Enroll",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
