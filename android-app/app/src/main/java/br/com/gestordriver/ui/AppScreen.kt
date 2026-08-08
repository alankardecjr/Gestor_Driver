package br.com.gestordriver.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.gestordriver.model.CampoApresentacao
import br.com.gestordriver.model.ModoApresentacao
import br.com.gestordriver.model.PlanoAcesso

@Composable
fun AppScreen(viewModel: AppViewModel) {
    val state = viewModel.state

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF101418),
                            Color(0xFF171D25),
                            Color(0xFF0D1117),
                        ),
                    ),
                )
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            // Título
            Text(
                text = "Gestor Driver",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            // Controle temporário de plano para testes
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState()),
            ) {
                PlanoAcesso.entries.forEach { plano ->
                    FilterChip(
                        selected = state.corrida.plano == plano,
                        onClick = {
                            viewModel.selecionarPlano(plano)
                        },
                        label = {
                            Text(plano.name)
                        },
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 3.dp,
                        color = parseColor(state.corrida.corClassificacao),
                        shape = CardDefaults.shape,
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF050809),
                ),
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    // Cabeçalho idêntico na tela compacta e expandida
                    CabecalhoCorrida(
                        campos = state.corrida.camposCompactos,
                    )

                    // Detalhes aparecem somente quando expandido
                    if (state.corrida.modo == ModoApresentacao.DETALHES) {
                        DetalhesCorrida(
                            campos = state.corrida.camposDetalhes,
                        )
                    }

                    // Controles
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = viewModel::alternarDetalhes,
                        ) {
                            Text(
                                text = if (
                                    state.corrida.modo == ModoApresentacao.COMPACTA
                                ) {
                                    "↓"
                                } else {
                                    "↑"
                                },
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Button(
                            onClick = viewModel::alternarHistorico,
                        ) {
                            Text(
                                text = if (state.historicoVisivel) {
                                    "Histórico ↑"
                                } else {
                                    "Histórico"
                                },
                            )
                        }
                    }
                }
            }

            if (state.historicoVisivel) {
                HistoricoRow(state)
            }

            if (state.seloFlutuante) {
                SeloFlutuante()
            }

            if (state.overlayAtivo) {
                OverlayPreview()
            }
        }
    }
}

@Composable
private fun CabecalhoCorrida(
    campos: List<CampoApresentacao>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        campos.forEach { campo ->

            when (campo.id) {

                "valor_por_km" -> {
                    CampoCabecalho(
                        icone = "🛞",
                        titulo = "R$/KM",
                        valor = campo.valor,
                        destaque = campo.destaque,
                    )
                }

                "valor_total" -> {
                    CampoCabecalho(
                        icone = "💰",
                        titulo = "R$ TOTAL",
                        valor = campo.valor,
                        destaque = false,
                    )
                }

                "km_total" -> {
                    CampoCabecalho(
                        icone = "📍",
                        titulo = "KM TOTAL",
                        valor = campo.valor,
                        destaque = false,
                    )
                }

                "tempo_estimado" -> {
                    CampoCabecalho(
                        icone = "🕐",
                        titulo = "TEMPO",
                        valor = campo.valor,
                        destaque = false,
                    )
                }

                "nota_passageiro" -> {
                    CampoCabecalho(
                        icone = "⭐",
                        titulo = "ESTRELAS",
                        valor = campo.valor,
                        destaque = false,
                    )
                }
            }
        }

        // Informação
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = MaterialTheme.shapes.extraLarge,
                )
                .padding(
                    horizontal = 10.dp,
                    vertical = 5.dp,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "I",
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun CampoCabecalho(
    icone: String,
    titulo: String,
    valor: String,
    destaque: Boolean,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = titulo,
            color = Color(0xFF9BE15D),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {

            Text(
                text = icone,
                color = Color.White,
            )

            Text(
                text = valor,
                color = Color.White,
                style = if (destaque) {
                    MaterialTheme.typography.headlineMedium
                } else {
                    MaterialTheme.typography.titleMedium
                },
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun DetalhesCorrida(
    campos: List<CampoApresentacao>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        campos.forEach { campo ->
            LinhaDetalhe(campo)
        }
    }
}

@Composable
private fun LinhaDetalhe(
    campo: CampoApresentacao,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = campo.titulo,
            color = Color(0xFFDDE6F2),
        )

        Text(
            text = campo.valor,
            color = Color(0xFFDDE6F2),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun HistoricoRow(
    state: AppState,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Text(
            text = "Histórico",
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState()),
        ) {

            state.historico.forEach { item ->

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF111821),
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {

                        Text(
                            text = item.plataforma,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )

                        Text(
                            text = item.data,
                            color = Color(0xFFB7C3D0),
                        )

                        Text(
                            text = item.linhaHorizontal,
                            color = Color(0xFFDDE6F2),
                        )

                        Text(
                            text = item.classificacao.name,
                            color = parseColor(item.corClassificacao),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OverlayPreview() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF213040),
        ),
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Overlay ativo sobre Uber / 99 / inDrive",
            color = Color.White,
        )
    }
}

@Composable
private fun SeloFlutuante() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2B3440),
        ),
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 10.dp,
            ),
            text = "◉ Selo flutuante ativo",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

private fun parseColor(
    valor: String,
): Color {
    return try {
        Color(android.graphics.Color.parseColor(valor))
    } catch (_: IllegalArgumentException) {
        Color.Green
    }
}