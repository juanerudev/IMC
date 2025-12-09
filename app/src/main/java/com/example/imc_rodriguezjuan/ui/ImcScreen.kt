package com.example.imc_rodriguezjuan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imc_rodriguezjuan.viewmodel.ImcViewModel

@Composable
fun ImcScreen(viewModel: ImcViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        Text(
            text = "CALCULADORA DE IMC",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Campo de Peso
        OutlinedTextField(
            value = viewModel.peso,
            onValueChange = { viewModel.onPesoChange(it) },
            label = { Text("Peso (kg)") },
            placeholder = { Text("Ingrese su peso") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Campo de Altura
        OutlinedTextField(
            value = viewModel.altura,
            onValueChange = { viewModel.onAlturaChange(it) },
            label = { Text("Altura (cm)") },
            placeholder = { Text("Ingrese su altura") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Mensaje de error
        if (viewModel.mensajeError.isNotEmpty()) {
            Text(
                text = viewModel.mensajeError,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Botones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Botón Calcular
            Button(
                onClick = { viewModel.calcularImc() },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Calcular",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Botón Limpiar
            OutlinedButton(
                onClick = { viewModel.limpiarCampos() },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Limpiar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Mostrar resultados solo si el IMC es mayor a 0
        if (viewModel.resultado.imc > 0) {
            Spacer(modifier = Modifier.height(24.dp))

            // Card de resultados
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // IMC calculado
                    Text(
                        text = "Tu IMC es:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = String.format("%.2f", viewModel.resultado.imc),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp
                    )

                    // Clasificación con color de fondo
                    Text(
                        text = "Clasificación:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(viewModel.resultado.color),
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = viewModel.resultado.clasificacion,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}