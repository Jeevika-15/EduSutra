
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
import com.edusutra.app.data.repository.UserRepository
import com.edusutra.app.navigation.AppNavigation
import com.edusutra.app.ui.theme.EduSutraTheme
import com.edusutra.app.viewmodel.ProgressViewModel
import com.edusutra.app.viewmodel.ProgressViewModelFactory
import com.edusutra.app.viewmodel.UserViewModel
import com.edusutra.app.viewmodel.UserViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseProvider.getDatabase(applicationContext)

        val progressRepository = ProgressRepository(
            database.progressDao(),
            database.masteryDao(),
            database.revisionDao()
        )

        val userRepository = UserRepository(
            database.userDao()
        )

        setContent {
            EduSutraTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    val progressViewModel: ProgressViewModel =
                        viewModel(
                            factory = ProgressViewModelFactory(
                                progressRepository
                            )
                        )

                    val userViewModel: UserViewModel =
                        viewModel(
                            factory = UserViewModelFactory(
                                userRepository
                            )
                        )

                    LaunchedEffect(Unit) {
                        DemoDataSeeder.seedIfEmpty(progressRepository, userRepository)

                        progressViewModel.loadProgress(userId = 1)
                        userViewModel.loadUser(userId = 1)
                    }

                    val progressState by
                    progressViewModel.uiState.collectAsState()

                    val userState by
                    userViewModel.uiState.collectAsState()

                    AppNavigation(
                        progressUiState = progressState,

                        onCompleteRevision = { revisionId ->
                            progressViewModel.completeRevision(revisionId)
                        },

                        user = userState.user,

                        onSaveProfile = { updatedUser ->
                            userViewModel.updateUser(updatedUser)
                        }
                    )
                }
            }
        }
    }
}
