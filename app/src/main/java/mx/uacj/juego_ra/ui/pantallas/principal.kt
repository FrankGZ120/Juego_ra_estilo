package mx.uacj.juego_ra.ui.pantallas

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.uacj.juego_ra.modelos.Informacion
import mx.uacj.juego_ra.modelos.InformacionInteractiva
import mx.uacj.juego_ra.modelos.TiposDePistas
import mx.uacj.juego_ra.repositorios.estaticos.RepositorioPruebas
import mx.uacj.juego_ra.ui.organismos.InformacionInteractivaVista
import mx.uacj.juego_ra.ui.organismos.InformacionVista


@Composable
fun Principal(ubicacion: Location?, modificador: Modifier = Modifier) {

    var mostrar_pantalla_generica by remember { mutableStateOf(true) }
    var mostrar_pista_cercana by remember { mutableStateOf(false) }

    Column(
        modifier = modificador
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (pista in RepositorioPruebas.pistas) {
            if (ubicacion == null) {
                break
            }

            val distancia_a_la_pista = ubicacion.distanceTo(pista.ubicacion)

            if (distancia_a_la_pista < pista.distancia_maxima) {
                mostrar_pantalla_generica = false
                val nivel_de_distancia =
                    (distancia_a_la_pista * 100) / (pista.distancia_maxima - pista.distancia_minima)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "La pista es: ${pista.nombre}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1565C0)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "El nivel de la distancia a la pista es ${nivel_de_distancia.toInt()}%",
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (nivel_de_distancia > 75) {
                            Text("❄️ Estás frío todavía", color = Color.Gray)
                        } else if (nivel_de_distancia > 50) {
                            Text("🙂 Te estás acercando", color = Color(0xFF6A1B9A))
                        } else if (nivel_de_distancia > 25) {
                            Text("🔥 Muy cerca, sigue así", color = Color(0xFFD84315))
                        } else if (nivel_de_distancia < 20 && !mostrar_pista_cercana) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { mostrar_pista_cercana = true }
                                    .background(Color(0xFF4CAF50), RoundedCornerShape(8.dp))
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    "Capturar pista cercana",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        if (mostrar_pista_cercana) {
                            Spacer(modifier = Modifier.height(12.dp))
                            when (pista.cuerpo.tipo) {
                                TiposDePistas.texto -> {
                                    InformacionVista(pista.cuerpo as Informacion)
                                }

                                TiposDePistas.interactiva -> {
                                    InformacionInteractivaVista(pista.cuerpo as InformacionInteractiva)
                                }

                                TiposDePistas.camara -> {
                                    TODO()
                                }

                                TiposDePistas.agitar_telefono -> {
                                    TODO()
                                }
                            }
                        }
                    }
                }
            }
        }

        if (mostrar_pantalla_generica) {
            Spacer(modifier = Modifier.height(40.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "🚶 No te encuentras cerca de alguna pista por el momento",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Por favor sigue explorando",
                    color = Color.DarkGray
                )
            }
        }
    }
}
