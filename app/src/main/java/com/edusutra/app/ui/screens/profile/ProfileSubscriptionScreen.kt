package com.edusutra.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun ProfileSubscriptionScreen(
    studentName: String = "Uday Kumar Kethavath",
    studentInfo: String = "B.Tech 2nd year · CSE · Sem 4",
    onLogout: () -> Unit = {},
    onMyEnrollmentsClick: () -> Unit = {},
    onArchitectureClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Profile & Subscription",
            subtitle = "cancel anytime",
            actionText = "System",
            onActionClick = onArchitectureClick
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile & Subscription Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(54.dp),
                            shape = CircleShape,
                            color = PrimaryBlue
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("U", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = studentName,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = studentInfo,
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }

                        BadgeTag("PRO", SuccessGreenSoft, SuccessGreen)
                    }

                    HorizontalDivider(color = Color.White.copy(alpha = 0.15f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Plan: Full Access", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("renews 12 Nov 2026", fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White.copy(alpha = 0.15f),
                            modifier = Modifier.clickable { }
                        ) {
                            Text(
                                text = "Manage",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // LEARNING Section
            Text("LEARNING", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Navy700)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
            ) {
                Column {
                    ProfileMenuRow("My enrollments", "3 >", onClick = onMyEnrollmentsClick)
                    HorizontalDivider(color = SlateBorderSubtle)
                    ProfileMenuRow("Downloads — offline", "12 lessons >")
                    HorizontalDivider(color = SlateBorderSubtle)
                    ProfileMenuRow("Revision reminders", "Daily 7 PM >")
                }
            }

            // ACCOUNT Section
            Text("ACCOUNT", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Navy700)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
            ) {
                Column {
                    ProfileMenuRow("Payment methods & receipts", ">")
                    HorizontalDivider(color = SlateBorderSubtle)
                    ProfileMenuRow("Help & refunds", ">")
                    HorizontalDivider(color = SlateBorderSubtle)
                    ProfileMenuRow("Privacy & my data", ">")
                    HorizontalDivider(color = SlateBorderSubtle)
                    ProfileMenuRow("Log out", ">", isDestructive = true, onClick = onLogout)
                }
            }

            // Subscription transparency note
            Text(
                text = "Subscription transparency: renewal reminder 3 days before; cancel in one tap without menu rabbit holes.",
                fontSize = 11.sp,
                color = SlateMuted,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun ProfileMenuRow(
    title: String,
    badgeOrArrow: String,
    isDestructive: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = if (isDestructive) ErrorRed else Navy900
        )

        Text(
            text = badgeOrArrow,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isDestructive) ErrorRed else SlateMuted
        )
    }
}

