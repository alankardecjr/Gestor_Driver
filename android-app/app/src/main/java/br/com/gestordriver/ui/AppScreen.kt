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

    Surface(
        modifier = Modifier.fillMaxSize(),
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
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            // =========================================================
            // TÍTULO
            // =========================================================

            Text(
                text = "Gestor Driver",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )

            // =========================================================
            // CORRIDA ATUAL
            // Borda grossa = corrida em andamento
            // =========================================================

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

                    // Cabeçalho permanece igual na compacta e expandida.
                    CabecalhoCorrida(
                        campos = state.corrida.camposCompactos,
                        onInformacao = viewModel::alternarDetalhes,
                    )

                    // =================================================
                    // DETALHES
                    // =================================================

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

                    // =================================================
                    // CONTROLES
                    // =================================================

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

            // =========================================================
            // HISTÓRICO
            // =========================================================

            if (state.historicoVisivel) {
                HistoricoRow(state)
            }

            // =========================================================
            // SELO FLUTUANTE
            // =========================================================

            if (state.seloFlutuante) {
                SeloFlutuante()
            }

            // =========================================================
            // PREVIEW DO OVERLAY
            // =========================================================

            if (state.overlayAtivo) {
                OverlayPreview()
            }
        }
    }
}


// =====================================================================
// CABEÇALHO
// =====================================================================

@Composable
private fun CabecalhoCorrida(
    campos: List<CampoApresentacao>,
    onInformacao: () -> Unit,
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

        // =============================================================
        // BOTÃO DE INFORMAÇÃO
        // Agora realmente abre/fecha os detalhes.
        // =============================================================

        Button(
            onClick = onInformacao,
            modifier = Modifier.padding(start = 2.dp),
        ) {
            Text(
                text = "ⓘ",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}


// =====================================================================
// CAMPO DO CABEÇALHO
// =====================================================================

@Composable
private fun CampoCabecalho(
    campo: CampoApresentacao,
) {

    when (campo.id) {

        // =============================================================
        // R$/KM
        // =============================================================

        "valor_por_km" -> {
            CabecalhoSimples(
                icone = "🛞",
                titulo = "R$/KM",
                valor = campo.valor,
                destaque = campo.destaque,
            )
        }

        // =============================================================
        // R$ TOTAL
        //
        // O valor vindo do modelo pode ser "R$ 38,00".
        // Aqui retiramos apenas o prefixo para evitar:
        //
        // 💰 R$ 38,00
        //
        // Resultado:
        //
        // 💰 38,00
        // =============================================================

        "valor_total" -> {
            CabecalhoSimples(
                icone = "💰",
                titulo = "R$ TOTAL",
                valor = removerPrefixoReal(campo.valor),
                destaque = campo.destaque,
            )
        }

        // =============================================================
        // KM TOTAL
        // =============================================================

        "km_total" -> {
            CabecalhoSimples(
                icone = "📍",
                titulo = "KM TOTAL",
                valor = campo.valor,
                destaque = campo.destaque,
            )
        }

        // =============================================================
        // TEMPO
        // =============================================================

        "tempo_estimado" -> {
            CabecalhoSimples(
                icone = "🕐",
                titulo = "TEMPO",
                valor = campo.valor,
                destaque = campo.destaque,
            )
        }

        // =============================================================
        // CLASSIFICAÇÃO / ESTRELAS
        //
        // A estrela é construída junto com o valor em um único Text.
        // Isso evita que ela seja tratada como um campo separado.
        // =============================================================

        "nota_passageiro" -> {
            CabecalhoEstrelas(
                titulo = "ESTRELAS",
                valor = campo.valor,
            )
        }

        else -> {
            CabecalhoSimples(
                icone = "",
                titulo = campo.titulo,
                valor = campo.valor,
                destaque = campo.destaque,
            )
        }
    }
}


// =====================================================================
// CABEÇALHO PADRÃO
// =====================================================================

@Composable
private fun CabecalhoSimples(
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
                style = if (destaque) {
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


// =====================================================================
// ESTRELAS
// =====================================================================

@Composable
private fun CabecalhoEstrelas(
    titulo: String,
    valor: String,
) {
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

        // Valor + estrela ficam juntos.
        Text(
            text = "$valor ⭐",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
        )
    }
}


// =====================================================================
// DETALHES
// =====================================================================

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


// =====================================================================
// LINHA DOS DETALHES
// =====================================================================

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


// =====================================================================
// CONTROLES
// =====================================================================

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

        // =============================================================
        // EXPANDIR / RETRAIR
        // =============================================================

        Button(
            onClick = onAlternarDetalhes,
        ) {
            Text(
                text = if (
                    modo == ModoApresentacao.COMPACTA
                ) {
                    "↓"
                } else {
                    "↑"
                },
                style = MaterialTheme.typography.labelMedium,
            )
        }

        // =============================================================
        // CONFIGURAÇÕES
        // Mantido bloqueado conforme definido anteriormente.
        // =============================================================

        Button(
            onClick = {},
            enabled = false,
        ) {
            Text(
                text = "⚙️ Config",
                style = MaterialTheme.typography.labelSmall,
            )
        }

        // =============================================================
        // OCULTAR
        // =============================================================

        Button(
            onClick = onOcultar,
        ) {
            Text(
                text = "❎ Ocultar",
                style = MaterialTheme.typography.labelSmall,
            )
        }

        // =============================================================
        // FECHAR
        // =============================================================

        Button(
            onClick = onFechar,
        ) {
            Text(
                text = "📴 Fechar",
                style = MaterialTheme.typography.labelSmall,
            )
        }

        // =============================================================
        // HISTÓRICO
        // =============================================================

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


// =====================================================================
// HISTÓRICO
// =====================================================================

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


// =====================================================================
// CARD DO HISTÓRICO
// =====================================================================

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
                text = "🛞R$/KM  💰R$ TOTAL  📍KM TOTAL  🕐TEMPO  ESTRELAS",
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


// =====================================================================
// OVERLAY
// =====================================================================

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


// =====================================================================
// SELO FLUTUANTE
// =====================================================================

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


// =====================================================================
// UTILITÁRIO
// =====================================================================

private fun removerPrefixoReal(
    valor: String,
): String {
    return valor
        .removePrefix("R$")
        .trim()
}


// =====================================================================
// COR
// =====================================================================

private fun parseColor(
    valor: String,
): Color {
    return try {
        Color(
            android.graphics.Color.parseColor(valor),
        )
    } catch (_: IllegalArgumentException) {
        Color.Green
    }
}