
package com.edusutra.app.ui.screens.courses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.edusutra.app.data.local.entity.Course

@Composable
fun CourseCatalogueScreen(
    courses: List<Course>,
    onCourseClick: (Int) -> Unit,
    onSearch: (String) -> Unit
) {
    var searchText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Course Catalogue",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearch(it)
            },
            label = { Text("Search courses") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(courses, key = { it.courseId }) { course ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onCourseClick(course.courseId)
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(course.title, style = MaterialTheme.typography.titleMedium)
                        Text(course.description)
                        Text("Category: ${course.category}")
                        Text("Duration: ${course.duration}")
                        Text("Level: ${course.difficulty}")
                    }
                }
            }
        }
    }
}
