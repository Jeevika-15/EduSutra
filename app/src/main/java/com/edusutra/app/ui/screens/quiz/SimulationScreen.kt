package com.edusutra.app.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edusutra.app.ui.components.BadgeTag
import com.edusutra.app.ui.components.EduStatBox
import com.edusutra.app.ui.components.EduSutraTopBar
import com.edusutra.app.ui.theme.*
import com.edusutra.app.viewmodel.CircuitSimulationViewModel

@Composable
fun SimulationScreen(
    simulationViewModel: CircuitSimulationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBackClick: () -> Unit = {},
    onContinueToQuiz: () -> Unit = {}
) {
    val uiState by simulationViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBackground)
    ) {
        EduSutraTopBar(
            title = "Circuit Lab",
            subtitle = "Ohm's Law — topic prerequisite",
            onBackClick = onBackClick,
            badgeText = "on-device",
            actionText = "Reset",
            onActionClick = { simulationViewModel.reset() }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Interactive Simulation Canvas Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "INTERACTIVE CIRCUIT SIMULATION",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    // Circuit components line
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = PrimaryBlue,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = "🔋 ${uiState.voltage} V",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }

                        Text("━━━━", color = PrimaryBlue, fontWeight = FontWeight.Bold)

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = WarningAmber,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = "⚡ ${uiState.resistance} Ω",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }

                        Text("━━━━", color = PrimaryBlue, fontWeight = FontWeight.Bold)

                        val bulbGlowColor = when (uiState.bulbState) {
                            "Bright" -> Color(0xFFFDE047)
                            "Medium" -> Color(0xFFFBBF24)
                            "Dim" -> Color(0xFFF59E0B)
                            else -> SlateMuted
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = bulbGlowColor,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = "💡 ${uiState.bulbState}",
                                color = Navy900,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "drag voltage & resistance values to observe live reactions",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }

            // Voltage Slider
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("VOLTAGE (V)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Navy700)
                        Text("${uiState.voltage} V", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                    }
                    Slider(
                        value = uiState.voltage,
                        onValueChange = { simulationViewModel.updateVoltage(it) },
                        valueRange = 1.0f..12.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = PrimaryBlue,
                            activeTrackColor = PrimaryBlue
                        )
                    )
                }
            }

            // Resistance Slider
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("RESISTANCE (R)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Navy700)
                        Text("${uiState.resistance} Ω", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = WarningAmber)
                    }
                    Slider(
                        value = uiState.resistance,
                        onValueChange = { simulationViewModel.updateResistance(it) },
                        valueRange = 5.0f..50.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = WarningAmber,
                            activeTrackColor = WarningAmber
                        )
                    )
                }
            }

            // Live Readouts (3 Boxes)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                EduStatBox(
                    title = "CURRENT (I)",
                    value = "${uiState.current} A",
                    highlightColor = PrimaryBlue,
                    modifier = Modifier.weight(1f)
                )

                EduStatBox(
                    title = "POWER (P)",
                    value = "${uiState.power} W",
                    highlightColor = SuccessGreen,
                    modifier = Modifier.weight(1f)
                )

                EduStatBox(
                    title = "BULB",
                    value = uiState.bulbState,
                    highlightColor = WarningAmber,
                    modifier = Modifier.weight(1f)
                )
            }

            // Formula explanation box
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryBlueSoft),
                border = androidx.compose.foundation.BorderStroke(1.dp, BlueBadge)
            ) {
                Text(
                    text = uiState.message,
                    fontSize = 12.sp,
                    color = Navy900,
                    modifier = Modifier.padding(14.dp),
                    lineHeight = 17.sp
                )
            }

            // Run Experiment CTA
            Button(
                onClick = { simulationViewModel.runExperiment() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Run experiment", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Text(
                text = "Saved results attach to the topic and count toward mastery in your progress dashboard.",
                fontSize = 11.sp,
                color = SlateMuted,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
