package com.example.imc_rodriguezjuan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.imc_rodriguezjuan.model.ImcResult

class ImcViewModel : ViewModel() {

    var peso by mutableStateOf("")
        private set

    var altura by mutableStateOf("")
        private set

    var resultado by mutableStateOf(ImcResult())
        private set

    var mensajeError by mutableStateOf("")
        private set

    fun onPesoChange(nuevoPeso: String) {
        peso = nuevoPeso
        mensajeError = ""
    }

    fun onAlturaChange(nuevaAltura: String) {
        altura = nuevaAltura
        mensajeError = ""
    }

    fun calcularImc() {
        // Validar campos vacíos
        if (peso.isBlank() || altura.isBlank()) {
            mensajeError = "Por favor, complete todos los campos"
            resultado = ImcResult()
            return
        }

        // Convertir a números
        val pesoNum = peso.toDoubleOrNull()
        val alturaNum = altura.toDoubleOrNull()

        // Validar que sean números válidos
        if (pesoNum == null || alturaNum == null) {
            mensajeError = "Por favor, ingrese valores numéricos válidos"
            resultado = ImcResult()
            return
        }

        // Validar rangos
        if (pesoNum <= 0) {
            mensajeError = "El peso debe ser mayor a 0"
            resultado = ImcResult()
            return
        }

        if (alturaNum <= 0) {
            mensajeError = "La altura debe ser mayor a 0"
            resultado = ImcResult()
            return
        }

        // Convertir altura de cm a metros
        val alturaMetros = alturaNum / 100.0

        // Calcular IMC
        val imc = pesoNum / (alturaMetros * alturaMetros)

        // Determinar clasificación y color según OMS
        val (clasificacion, color) = when {
            imc < 18.5 -> Pair("Bajo Peso", 0xFFBBDEFB)
            imc < 25.0 -> Pair("Peso Normal", 0xFFC8E6C9)
            imc < 30.0 -> Pair("Sobrepeso", 0xFFFFF9C4)
            else -> Pair("Obesidad", 0xFFFFCDD2)
        }

        // Actualizar resultado
        resultado = ImcResult(
            imc = imc,
            clasificacion = clasificacion,
            color = color
        )

        mensajeError = ""
    }

    fun limpiarCampos() {
        peso = ""
        altura = ""
        resultado = ImcResult()
        mensajeError = ""
    }
}