package br.com.gestordriver.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

    Surface(modifier = Modifier.fillMaxSize()) {
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
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Gestor Driver",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )

            // A classificação da corrida em andamento é representada
            // pela borda mais grossa.
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
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    CabecalhoCorrida(
                        campos = state.corrida.camposCompactos,
                    )

                    if (state.corrida.modo == ModoApresentacao.DETALHES) {
                        Text(
                            text = "Detalhes",
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                        )

                        DetalhesCorrida(
                            campos = state.corrida.camposDetalhes,
                        )
                    }

                    ControlesInterface(
                        modo = state.corrida.modo,
                        historicoVisivel = state.historicoVisivel,
                        onAlternarDetalhes = viewModel::alternarDetalhes,
                        onAlternarHistorico = viewModel::alternarHistorico,
                        onOcultar = viewModel::sairInterface,
                        onFechar = viewModel::fecharApp,
                    )
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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        campos.forEach { campo ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 1.dp),
                contentAlignment = Alignment.Center,
            ) {
                CampoCabecalho(campo)
            }
        }

        Box(
            modifier = Modifier
                .padding(start = 2.dp)
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = MaterialTheme.shapes.extraLarge,
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "I",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun CampoCabecalho(
    campo: CampoApresentacao,
) {
    val (icone, titulo, valor) = when (campo.id) {
        "valor_por_km" -> Triple("🛞", "R$/KM", campo.valor)
        "valor_total" -> Triple("💰", "R$ TOTAL", campo.valor)
        "km_total" -> Triple("📍", "KM TOTAL", campo.valor)
        "tempo_estimado" -> Triple("🕐", "TEMPO", campo.valor)
        "nota_passageiro" -> Triple("", "ESTRELAS", "${campo.valor} ⭐")
        else -> Triple("", campo.titulo, campo.valor)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = titulo,
            color = Color(0xFFB8C5D1),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            if (icone.isNotEmpty()) {
                Text(
                    text = icone,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Text(
                text = valor,
                color = Color.White,
                style = if (campo.destaque) {
                    MaterialTheme.typography.titleMedium
                } else {
                    MaterialTheme.typography.bodyMedium
                },
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
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
            .padding(top = 2.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
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
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = campo.titulo,
            color = Color(0xFFD0D9E2),
            style = MaterialTheme.typography.bodySmall,
        )

        Text(
            text = campo.valor,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun ControlesInterface(
    modo: ModoApresentacao,
    historicoVisivel: Boolean,
    onAlternarDetalhes: () -> Unit,
    onAlternarHistorico: () -> Unit,
    onOcultar: () -> Unit,
    onFechar: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = onAlternarDetalhes,
        ) {
            Text(
                text = if (modo == ModoApresentacao.COMPACTA) "↓" else "↑",
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Button(
            onClick = {},
            enabled = false,
        ) {
            Text(
                text = "⚙️ Config",
                style = MaterialTheme.typography.labelSmall,
            )
        }

        Button(
            onClick = onOcultar,
        ) {
            Text(
                text = "❎ Ocultar",
                style = MaterialTheme.typography.labelSmall,
            )
        }

        Button(
            onClick = onFechar,
        ) {
            Text(
                text = "📴 Fechar",
                style = MaterialTheme.typography.labelSmall,
            )
        }

        Button(
            onClick = onAlternarHistorico,
        ) {
            Text(
                text = if (historicoVisivel) {
                    "📜 Histórico ↑"
                } else {
                    "📜 Histórico"
                },
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
private fun HistoricoRow(
    state: AppState,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Histórico",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = "ⓘ ↑",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            state.historico.forEachIndexed { index, item ->
                HistoricoCard(
                    item = item,
                    modifier = Modifier.weight(1f),
                    seta = when (index) {
                        0 -> "ⓘ ←"
                        1 -> "ⓘ →"
                        else -> "ⓘ"
                    },
                )
            }
        }
    }
}

@Composable
private fun HistoricoCard(
    item: br.com.gestordriver.model.HistoricoItemPresentation,
    modifier: Modifier = Modifier,
    seta: String,
) {
    Card(
        modifier = modifier.border(
            width = 1.dp,
            color = parseColor(item.corClassificacao),
            shape = CardDefaults.shape,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111821),
        ),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = item.plataforma,
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    text = seta,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                )
            }

            Text(
                text = "📅Data ${item.data}",
                color = Color(0xFFB7C3D0),
                style = MaterialTheme.typography.labelSmall,
            )

            Text(
                text = "🛞R$/KM  💰TOTAL  📍KM  🕐TEMPO  ESTRELAS",
                color = Color(0xFFDDE6F2),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
            )

            Text(
                text = item.linhaHorizontal,
                color = Color.White,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
            )
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
            modifier = Modifier.padding(8.dp),
            text = "Overlay ativo sobre Uber / 99 / inDrive",
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
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
                horizontal = 10.dp,
                vertical = 8.dp,
            ),
            text = "◉ Selo flutuante ativo",
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
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
