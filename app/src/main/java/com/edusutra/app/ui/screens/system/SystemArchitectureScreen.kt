package com.edusutra.app.ui.screens.system

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.ui.components.BadgeTag
import com.edusutra.app.ui.components.EduSutraTopBar
import com.edusutra.app.ui.theme.*

@Composable
fun SystemArchitectureScreen(
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "System Architecture",
            subtitle = "Data Sources & Flows",
            onBackClick = onBackClick,
            badgeText = "SYSTEM VIEW"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Intro summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Where every screen gets its data & sends learner results",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Clean MVVM layered architecture with offline-first Room caching.",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }

            // Layer 1: External Sources
            ArchitectureBox(
                title = "1 · EXTERNAL SOURCES",
                items = listOf(
                    "Learner device" to "Syllabus PDF/photo upload, quiz answers, hours/day, simulation values",
                    "Firebase Authentication" to "Google / phone OTP / email — ID token for every API call",
                    "Content partners" to "NPTEL videos + transcripts, SWAYAM / DIKSHA, YouTube playlists, Coursera links",
                    "Edu Sutra CMS" to "Question bank, path graphs, notes PDFs, simulations presets",
                    "Razorpay (payments)" to "UPI / card / net banking for Pro enrollment"
                ),
                headerColor = PrimaryBlue
            )

            // Layer 2: Backend & Intelligence
            ArchitectureBox(
                title = "2 · BACKEND & INTELLIGENCE",
                items = listOf(
                    "Edu Sutra REST API" to "/courses • /path • /questions • /attempts • /enrollment • /progress • /revisions",
                    "AI recommendation service" to "diagnostic → gap map → ordered path; adaptive difficulty; spaced-revision dates",
                    "Cloud database" to "Learner profile, enrollments & entitlement, attempts, mastery scores, receipts",
                    "Notification service (FCM)" to "Revision due and renewal reminder pushes"
                ),
                headerColor = SuccessGreen
            )

            // Layer 3: Android App (MVVM)
            ArchitectureBox(
                title = "3 · ANDROID APP (MVVM)",
                items = listOf(
                    "Jetpack Compose UI" to "18 modern, connected screens following Material 3 guidelines",
                    "ViewModel + StateFlow" to "Holds screen state; ready to deliver live data changes",
                    "Repository" to "Decides: serve from network or local Room cache",
                    "Room Database (offline)" to "Users, progress, quiz results, path, downloaded notes — full app works offline"
                ),
                headerColor = WarningAmber
            )

            // Data Flow Legend
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("DATA PIPELINE PROTOCOLS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SlateMuted)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        BadgeTag("GET", BlueBadge, PrimaryBlue)
                        Text("Courses, syllabus trees, lessons, notes, question bank", fontSize = 11.sp, color = Navy800)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        BadgeTag("POST", SuccessGreenSoft, SuccessGreen)
                        Text("Answers, watch time, simulation results → mastery model", fontSize = 11.sp, color = Navy800)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        BadgeTag("LOCAL", WarningAmberSoft, WarningAmber)
                        Text("Room keeps offline copy; background syncs when online", fontSize = 11.sp, color = Navy800)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun ArchitectureBox(
    title: String,
    items: List<Pair<String, String>>,
    headerColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = headerColor)
            HorizontalDivider(color = SlateBorderSubtle)
            items.forEach { (sub, desc) ->
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(sub, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Navy900)
                    Text(desc, fontSize = 11.sp, color = SlateMuted, lineHeight = 15.sp)
                }
            }
        }
    }
}

