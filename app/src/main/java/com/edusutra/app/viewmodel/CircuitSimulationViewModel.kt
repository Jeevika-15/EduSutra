package com.edusutra.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

data class CircuitSimulationUiState(
    val voltage: Float = 6.0f,
    val resistance: Float = 25.0f,
    val current: Float = 0.24f,
    val power: Float = 1.44f,
    val bulbState: String = "Dim",
    val isRunning: Boolean = false,
    val message: String = "Ohm's law: I = V/R = 6.0 / 25.0 = 0.24 A. Raise the resistance and watch the current I and brightness drop in real time."
)

class CircuitSimulationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CircuitSimulationUiState())
    val uiState: StateFlow<CircuitSimulationUiState> = _uiState.asStateFlow()

    fun updateVoltage(newV: Float) {
        val v = (newV * 10f).roundToInt() / 10f
        recalculate(v, _uiState.value.resistance)
    }

    fun updateResistance(newR: Float) {
        val r = ((newR * 10f).roundToInt() / 10f).coerceAtLeast(1.0f)
        recalculate(_uiState.value.voltage, r)
    }

    private fun recalculate(v: Float, r: Float) {
        val safeR = if (r <= 0.1f) 0.1f else r
        val i = ((v / safeR) * 100f).roundToInt() / 100f
        val p = ((v * i) * 100f).roundToInt() / 100f

        val bulb = when {
            i < 0.05f -> "Off"
            i < 0.35f -> "Dim"
            i < 0.75f -> "Medium"
            else -> "Bright"
        }

        _uiState.value = _uiState.value.copy(
            voltage = v,
            resistance = safeR,
            current = i,
            power = p,
            bulbState = bulb,
            message = "Ohm's law: I = V/R = $v / $safeR = $i A. Power: P = V × I = $p W."
        )
    }

    fun runExperiment() {
        _uiState.value = _uiState.value.copy(
            isRunning = true,
            message = "Experiment running! Live values recorded: Current = ${_uiState.value.current} A, Power = ${_uiState.value.power} W."
        )
    }

    fun reset() {
        _uiState.value = CircuitSimulationUiState()
    }
}

