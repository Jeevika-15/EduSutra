package com.edusutra.app.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.ui.components.BadgeTag
import com.edusutra.app.ui.components.EduSutraTopBar
import com.edusutra.app.ui.theme.*

@Composable
fun EnrollmentScreen(
    courseTitle: String = "Data Structures & Algorithms",
    onConfirmEnrollment: () -> Unit,
    onBackClick: () -> Unit = {}
) {
    var selectedPlan by remember { mutableStateOf("Pro") }
    var selectedProTier by remember { mutableStateOf("Monthly") }
    var selectedPaymentMethod by remember { mutableStateOf("UPI (G Pay)") }
    var isProcessing by remember { mutableStateOf(false) }

    val amount = if (selectedPlan == "Free") "₹0" else if (selectedProTier == "Monthly") "₹149.00" else "₹999.00"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Enroll & payment",
            subtitle = "Secure Mock Flow",
            onBackClick = onBackClick,
            badgeText = "UPI / Card"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Selected Course Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = courseTitle,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy900
                        )
                        Text(
                            text = "Source: NPTEL · 48 hrs self-paced · 12 modules",
                            fontSize = 11.sp,
                            color = SlateMuted
                        )
                    }
                    BadgeTag("ENROL", BlueBadge, PrimaryBlue)
                }
            }

            // Choose Plan Section
            Text(
                text = "CHOOSE PLAN",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Navy700
            )

            // Free Plan Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedPlan = "Free" },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedPlan == "Free") PrimaryBlueSoft else Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (selectedPlan == "Free") PrimaryBlue else SlateBorder
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedPlan == "Free",
                        onClick = { selectedPlan = "Free" },
                        colors = RadioButtonDefaults.colors(selectedColor = PrimaryBlue)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Free", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Navy900)
                        Text("All 18hr+ path · 5 quizzes/day · Free preview", fontSize = 11.sp, color = SlateMuted)
                    }
                }
            }

            // Pro Plan Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedPlan = "Pro" },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedPlan == "Pro") PrimaryBlueSoft else Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (selectedPlan == "Pro") PrimaryBlue else SlateBorder
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedPlan == "Pro",
                            onClick = { selectedPlan = "Pro" },
                            colors = RadioButtonDefaults.colors(selectedColor = PrimaryBlue)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pro — recommended", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Navy900)
                        Spacer(modifier = Modifier.weight(1f))
                        BadgeTag("POPULAR", SuccessGreenSoft, SuccessGreen)
                    }
                    Text(
                        text = "Entire path + unlimited quizzes, AI tutor, offline, simulation",
                        fontSize = 11.sp,
                        color = SlateMuted,
                        modifier = Modifier.padding(start = 40.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Monthly / Yearly toggles
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedProTier == "Monthly") PrimaryBlue else Color.White,
                            modifier = Modifier
                                .clickable {
                                    selectedPlan = "Pro"
                                    selectedProTier = "Monthly"
                                }
                                .border(1.dp, if (selectedProTier == "Monthly") PrimaryBlue else SlateBorder, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "Monthly ₹149",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedProTier == "Monthly") Color.White else Navy800,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedProTier == "Yearly") PrimaryBlue else Color.White,
                            modifier = Modifier
                                .clickable {
                                    selectedPlan = "Pro"
                                    selectedProTier = "Yearly"
                                }
                                .border(1.dp, if (selectedProTier == "Yearly") PrimaryBlue else SlateBorder, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "Yearly ₹999 -44%",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedProTier == "Yearly") Color.White else Navy800,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // Payment Methods
            if (selectedPlan == "Pro") {
                Text(
                    text = "PAYMENT METHOD",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy700
                )

                val methods = listOf("UPI (G Pay)", "PhonePe", "Card", "Net banking")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    methods.forEach { method ->
                        val isSelected = selectedPaymentMethod == method
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) Navy900 else Color.White,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedPaymentMethod = method }
                                .border(1.dp, if (isSelected) Navy900 else SlateBorder, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = method,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Navy800,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }

                // Pricing Summary
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Plan", fontSize = 12.sp, color = SlateMuted)
                            Text(amount, fontSize = 12.sp, color = Navy900)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("GST (18%)", fontSize = 12.sp, color = SlateMuted)
                            Text("Included", fontSize = 12.sp, color = SuccessGreen)
                        }
                        HorizontalDivider(color = SlateBorder)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total today", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Navy900)
                            Text(amount, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                        }
                    }
                }
            }

            // CTAs
            Button(
                onClick = {
                    isProcessing = true
                    onConfirmEnrollment()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text(
                        text = if (selectedPlan == "Free") "Enroll Free" else "Pay $amount & Enroll",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            if (selectedPlan == "Pro") {
                TextButton(
                    onClick = {
                        selectedPlan = "Free"
                        onConfirmEnrollment()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enroll free instead", color = SlateMuted, fontSize = 12.sp)
                }
            }

            // Cancellation policy note
            Text(
                text = "No silent auto-renew: we remind you 3 days before renewal and cancel takes one tap.",
                fontSize = 11.sp,
                color = SlateMuted,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
