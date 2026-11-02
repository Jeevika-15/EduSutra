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
import com.edusutra.app.data.repository.ProgressRepository
import com.edusutra.app.navigation.AppNavigation
import com.edusutra.app.ui.theme.EduSutraTheme
import com.edusutra.app.viewmodel.ProgressViewModel
import com.edusutra.app.viewmodel.ProgressViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseProvider.getDatabase(applicationContext)

        val repository = ProgressRepository(
            database.progressDao(),
            database.masteryDao(),
            database.revisionDao(),
        )

        setContent {
            EduSutraTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {

                    val progressViewModel: ProgressViewModel =
                        viewModel(
                            factory = ProgressViewModelFactory(repository),
                        )

                    LaunchedEffect(Unit) {
                        DemoDataSeeder.seedIfEmpty(repository)
                        progressViewModel.loadProgress(userId = 1)
                    }

                    val uiState by progressViewModel.uiState.collectAsState()

                    AppNavigation(
                        progressUiState = uiState,
                        onCompleteRevision = progressViewModel::completeRevision,
                    )
                }
            }
        }
    }
}
