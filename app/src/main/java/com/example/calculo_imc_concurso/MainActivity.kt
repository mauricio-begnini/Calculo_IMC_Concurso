@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.calculo_imc_concurso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculo_imc_concurso.ui.theme.Calculo_IMC_ConcursoTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculo_IMC_ConcursoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TelaPrincipal()
                }
            }
        }
    }
}

@Composable
fun TelaPrincipal() {

    var alturaInformada by remember{
        mutableStateOf("")
    }
    var pesoInformado by remember{
        mutableStateOf("")
    }
    var imc by remember {
        mutableStateOf(0.0)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Calculadora de IMC", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        EditarCampoNumerico(valor = alturaInformada, identificacao = "Altura (m)"){novaAltura ->
            alturaInformada = novaAltura
        }
        EditarCampoNumerico(valor = pesoInformado.toString(), identificacao = "Peso (kg)"){novoPeso ->
            pesoInformado = novoPeso
        }

        BotaoCalcularIMC(){
            val peso = pesoInformado.toDoubleOrNull() ?: 0.0
            val altura = alturaInformada.toDoubleOrNull() ?: 0.0
            if (peso > 0.0 && altura > 0.0)
                imc = peso / (altura*altura)
            else
                imc = 0.0
        }

        Resultado(imc)
    }
}

@Composable
fun EditarCampoNumerico(
    valor: String,
    identificacao: String,
    quandoValorMudar: (String) -> Unit
) {
    TextField(
        value = valor.toString(),
        onValueChange = quandoValorMudar,
        label = { Text(text = identificacao) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}

@Composable
fun BotaoCalcularIMC(
    quandoPressionarBotao: () -> Unit
) {
    Button(onClick = quandoPressionarBotao) {
        Text(text = "Calcular")
    }
}

@Composable
fun Resultado(imc: Double) {
    Column() {
        if(imc == 0.0)
            Text(text = "Informe valores de peso e altura validos para calcular o IMC", textAlign = TextAlign.Center)
        else
            Text(text = "Indice de Massa Corporal = ${String.format("%.2f",imc)}")
    }
}

@Preview(showBackground = true)
@Composable
fun TelaPrincipalPreview() {
    Calculo_IMC_ConcursoTheme {
        TelaPrincipal()
    }
}