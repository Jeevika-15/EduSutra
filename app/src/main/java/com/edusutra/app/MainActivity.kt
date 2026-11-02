
package com.edusutra.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

import com.edusutra.app.data.DemoDataSeeder
import com.edusutra.app.data.local.DatabaseProvider
import com.edusutra.app.data.repository.CourseRepository
import com.edusutra.app.data.repository.EnrollmentRepository
import com.edusutra.app.data.repository.LearningPathRepository
import com.edusutra.app.data.repository.ProgressRepository

import com.edusutra.app.navigation.AppNavigation
import com.edusutra.app.ui.theme.EduSutraTheme

import com.edusutra.app.viewmodel.CourseCatalogueViewModel
import com.edusutra.app.viewmodel.CourseCatalogueViewModelFactory
import com.edusutra.app.viewmodel.CourseDetailsViewModel
import com.edusutra.app.viewmodel.CourseDetailsViewModelFactory
import com.edusutra.app.viewmodel.LearningPathViewModel
import com.edusutra.app.viewmodel.LearningPathViewModelFactory
import com.edusutra.app.viewmodel.ProgressViewModel
import com.edusutra.app.viewmodel.ProgressViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseProvider.getDatabase(applicationContext)

        // Repositories
        val repository = ProgressRepository(
            database.progressDao(),
            database.masteryDao(),
            database.revisionDao()
        )

        val courseRepository =
            CourseRepository(database.courseDao())

        val enrollmentRepository =
            EnrollmentRepository(database.enrollmentDao())

        val learningPathRepository =
            LearningPathRepository(database.learningPathDao())

        setContent {
            EduSutraTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Progress ViewModel
                    val progressViewModel: ProgressViewModel =
                        viewModel(
                            factory = ProgressViewModelFactory(repository)
                        )

                    // Course Catalogue ViewModel
                    val courseCatalogueViewModel: CourseCatalogueViewModel =
                        viewModel(
                            factory = CourseCatalogueViewModelFactory(
                                courseRepository
                            )
                        )

                    // Course Details ViewModel
                    val courseDetailsViewModel: CourseDetailsViewModel =
                        viewModel(
                            factory = CourseDetailsViewModelFactory(
                                courseRepository,
                                enrollmentRepository
                            )
                        )

                    // Learning Path ViewModel
                    val learningPathViewModel: LearningPathViewModel =
                        viewModel(
                            factory = LearningPathViewModelFactory(
                                learningPathRepository
                            )
                        )

                    // Seed all sample data
                    LaunchedEffect(Unit) {

                        DemoDataSeeder.seedIfEmpty(repository)

                        DemoDataSeeder.seedCoursesIfEmpty(
                            courseRepository
                        )

                        DemoDataSeeder.seedLearningPathIfEmpty(
                            courseRepository,
                            learningPathRepository
                        )

                        progressViewModel.loadProgress(userId = 1)
                    }

                    val uiState by
                    progressViewModel.uiState.collectAsState()

                    // App Navigation
                    AppNavigation(
                        progressUiState = uiState,

                        onCompleteRevision = { revisionId ->
                            progressViewModel.completeRevision(
                                revisionId
                            )
                        },

                        courseCatalogueViewModel =
                            courseCatalogueViewModel,

                        courseDetailsViewModel =
                            courseDetailsViewModel,

                        learningPathViewModel =
                            learningPathViewModel
                    )
                }
            }
        }
    }
}
