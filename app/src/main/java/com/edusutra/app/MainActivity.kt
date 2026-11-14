package com.edusutra.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edusutra.app.data.DemoDataSeeder
import com.edusutra.app.data.local.DatabaseProvider
import com.edusutra.app.data.repository.*
import com.edusutra.app.navigation.AppNavigation
import com.edusutra.app.ui.theme.EduSutraTheme
import com.edusutra.app.viewmodel.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseProvider.getDatabase(applicationContext)

        // Repositories
        val progressRepository = ProgressRepository(
            database.progressDao(),
            database.masteryDao(),
            database.revisionDao()
        )
        val courseRepository = CourseRepository(database.courseDao())
        val enrollmentRepository = EnrollmentRepository(database.enrollmentDao())
        val learningPathRepository = LearningPathRepository(database.learningPathDao())
        val learnerProfileRepository = LearnerProfileRepository(database.learnerProfileDao())
        val syllabusSubjectRepository = SyllabusSubjectRepository(database.syllabusSubjectDao())
        val chatMessageRepository = ChatMessageRepository(database.chatMessageDao())

        setContent {
            EduSutraTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Seed initial data asynchronously on first launch
                    LaunchedEffect(Unit) {
                        DemoDataSeeder.seedAllIfEmpty(database)
                    }

                    // ViewModels
                    val progressViewModel: ProgressViewModel = viewModel(
                        factory = ProgressViewModelFactory(progressRepository)
                    )
                    val courseCatalogueViewModel: CourseCatalogueViewModel = viewModel(
                        factory = CourseCatalogueViewModelFactory(courseRepository)
                    )
                    val courseDetailsViewModel: CourseDetailsViewModel = viewModel(
                        factory = CourseDetailsViewModelFactory(courseRepository, enrollmentRepository)
                    )
                    val learningPathViewModel: LearningPathViewModel = viewModel(
                        factory = LearningPathViewModelFactory(learningPathRepository)
                    )
                    val learnerProfileViewModel: LearnerProfileViewModel = viewModel(
                        factory = LearnerProfileViewModelFactory(learnerProfileRepository)
                    )
                    val syllabusUploadViewModel: SyllabusUploadViewModel = viewModel(
                        factory = SyllabusUploadViewModelFactory(syllabusSubjectRepository)
                    )
                    val circuitSimulationViewModel: CircuitSimulationViewModel = viewModel()
                    val aiTutorViewModel: AiTutorViewModel = viewModel(
                        factory = AiTutorViewModelFactory(chatMessageRepository)
                    )

                    // Load initial progress
                    LaunchedEffect(Unit) {
                        progressViewModel.loadProgress(1)
                    }

                    AppNavigation(
                        database = database,
                        progressViewModel = progressViewModel,
                        courseCatalogueViewModel = courseCatalogueViewModel,
                        courseDetailsViewModel = courseDetailsViewModel,
                        learningPathViewModel = learningPathViewModel,
                        learnerProfileViewModel = learnerProfileViewModel,
                        syllabusUploadViewModel = syllabusUploadViewModel,
                        circuitSimulationViewModel = circuitSimulationViewModel,
                        aiTutorViewModel = aiTutorViewModel
                    )
                }
            }
        }
    }
}
