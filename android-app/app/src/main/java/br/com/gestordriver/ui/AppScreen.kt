package br.com.gestordriver.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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

            // =====================================================
            // TÍTULO
            // =====================================================

            Text(
                text = "Gestor Driver",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )

            // =====================================================
            // CORRIDA ATUAL
            //
            // 3 dp = corrida em andamento
            // =====================================================

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 3.dp,
                        color = parseColor(
                            state.corrida.corClassificacao,
                        ),
                        shape = CardDefaults.shape,
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF050809),
                ),
            ) {

                Column(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 7.dp,
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    // =================================================
                    // CABEÇALHO
                    // =================================================

                    CabecalhoCorrida(
                        campos = state.corrida.camposCompactos,
                        onInformacao = viewModel::alternarDetalhes,
                    )

                    // =================================================
                    // DETALHES
                    // =================================================

                    if (
                        state.corrida.modo ==
                        ModoApresentacao.DETALHES
                    ) {
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
                        onAlternarDetalhes =
                            viewModel::alternarDetalhes,
                        onAlternarHistorico =
                            viewModel::alternarHistorico,
                        onOcultar =
                            viewModel::sairInterface,
                        onFechar =
                            viewModel::fecharApp,
                    )
                }
            }

            // =====================================================
            // HISTÓRICO
            // =====================================================

            if (state.historicoVisivel) {
                HistoricoRow(state)
            }
        }
    }
}


// =====================================================================
// CABEÇALHO DA CORRIDA
// =====================================================================

@Composable
private fun CabecalhoCorrida(
    campos: List<CampoApresentacao>,
    onInformacao: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
        // INFORMAÇÃO
        //
        // Não é Button Material 3.
        // É uma área pequena e clicável.
        // =============================================================

        Box(
            modifier = Modifier
                .padding(start = 3.dp)
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = MaterialTheme.shapes.extraLarge,
                )
                .clickable {
                    onInformacao()
                }
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                ),
            contentAlignment = Alignment.Center,
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

        "valor_por_km" -> {
            CabecalhoSimples(
                icone = "🛞",
                titulo = "R$/KM",
                valor = campo.valor,
                destaque = campo.destaque,
            )
        }

        "valor_total" -> {
            CabecalhoSimples(
                icone = "💰",
                titulo = "R$ TOTAL",
                valor = removerPrefixoReal(campo.valor),
                destaque = campo.destaque,
            )
        }

        "km_total" -> {
            CabecalhoSimples(
                icone = "📍",
                titulo = "KM TOTAL",
                valor = campo.valor,
                destaque = campo.destaque,
            )
        }

        "tempo_estimado" -> {
            CabecalhoSimples(
                icone = "🕐",
                titulo = "TEMPO",
                valor = campo.valor,
                destaque = campo.destaque,
            )
        }

        "nota_passageiro" -> {
            CabecalhoEstrelas(
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
// CAMPO PADRÃO
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
    valor: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "ESTRELAS",
            color = Color(0xFFB8C5D1),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
        )

        // A estrela pertence ao valor.
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
            .padding(top = 1.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        campos.forEach { campo ->
            LinhaDetalhe(campo)
        }
    }
}


// =====================================================================
// LINHA DE DETALHE
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
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        ControleCompacto(
            texto = if (
                modo == ModoApresentacao.COMPACTA
            ) {
                "↓"
            } else {
                "↑"
            },
            onClick = onAlternarDetalhes,
        )

        ControleCompacto(
            texto = "⚙️ Config",
            onClick = {},
            habilitado = false,
        )

        ControleCompacto(
            texto = "❎ Ocultar",
            onClick = onOcultar,
        )

        ControleCompacto(
            texto = "📴 Fechar",
            onClick = onFechar,
        )

        ControleCompacto(
            texto = if (historicoVisivel) {
                "📜 Histórico ↑"
            } else {
                "📜 Histórico"
            },
            onClick = onAlternarHistorico,
        )
    }
}


// =====================================================================
// CONTROLE COMPACTO
// =====================================================================

@Composable
private fun ControleCompacto(
    texto: String,
    onClick: () -> Unit,
    habilitado: Boolean = true,
) {
    Text(
        text = texto,
        modifier = Modifier
            .clickable(
                enabled = habilitado,
                onClick = onClick,
            )
            .padding(
                horizontal = 3.dp,
                vertical = 5.dp,
            ),
        color = if (habilitado) {
            Color.White
        } else {
            Color(0xFF555D63)
        },
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
    )
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

        // =============================================================
        // CABEÇALHO DO HISTÓRICO
        // =============================================================

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

        // =============================================================
        // ABAS DOS APLICATIVOS
        //
        // IMPORTANTE:
        // Não usar weight().
        //
        // Cada aplicativo recebe uma largura própria.
        // O usuário desliza horizontalmente.
        // =============================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState(),
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            state.historico.forEachIndexed { index, item ->

                HistoricoCard(
                    item = item,
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
    seta: String,
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .border(
                width = 1.dp,
                color = parseColor(
                    item.corClassificacao,
                ),
                shape = CardDefaults.shape,
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111821),
        ),
    ) {

        Column(
            modifier = Modifier.padding(9.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {

            // =========================================================
            // APP
            // =========================================================

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

            // =========================================================
            // DATA / HORA
            // =========================================================

            Text(
                text = "📅 Data ${item.data}",
                color = Color(0xFFB7C3D0),
                style = MaterialTheme.typography.labelSmall,
            )

            // =========================================================
            // CABEÇALHO DOS DADOS
            // =========================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                Text(
                    text = "🛞 R$/KM",
                    color = Color(0xFFDDE6F2),
                    style = MaterialTheme.typography.labelSmall,
                )

                Text(
                    text = "💰 TOTAL",
                    color = Color(0xFFDDE6F2),
                    style = MaterialTheme.typography.labelSmall,
                )

                Text(
                    text = "📍 KM",
                    color = Color(0xFFDDE6F2),
                    style = MaterialTheme.typography.labelSmall,
                )
            }

            // =========================================================
            // SEGUNDA LINHA
            // =========================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                Text(
                    text = "🕐 TEMPO",
                    color = Color(0xFFDDE6F2),
                    style = MaterialTheme.typography.labelSmall,
                )

                Text(
                    text = "ESTRELAS",
                    color = Color(0xFFDDE6F2),
                    style = MaterialTheme.typography.labelSmall,
                )
            }

            // =========================================================
            // VALORES DA CORRIDA
            // =========================================================

            Text(
                text = formatarHistorico(
                    item.linhaHorizontal,
                ),
                color = Color.White,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
            )
        }
    }
}


// =====================================================================
// FORMATAÇÃO DO HISTÓRICO
// =====================================================================

private fun formatarHistorico(
    linha: String,
): String {

    val partes = linha
        .split("│")
        .map { it.trim() }

    if (partes.size < 5) {
        return linha
    }

    val valorPorKm = partes[0]
    val valorTotal = partes[1]
        .removePrefix("R$")
        .trim()

    val km = partes[2]
    val tempo = partes[3]
    val estrelas = partes[4]

    return "$valorPorKm   $valorTotal   $km   $tempo   $estrelas ⭐"
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