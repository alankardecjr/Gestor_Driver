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
import androidx.compose.foundation.layout.widthIn
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
                    androidx.compose.ui.graphics.Brush.linearGradient(
                        listOf(Color(0xFF101418), Color(0xFF171D25), Color(0xFF0D1117)),
                    ),
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Gestor Driver",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PlanoAcesso.entries.forEach { plano ->
                    FilterChip(
                        selected = state.corrida.plano == plano,
                        onClick = { viewModel.selecionarPlano(plano) },
                        label = { Text(text = plano.name) },
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2330)),
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    LinhaCompacta(campos = state.corrida.camposCompactos, cor = state.corrida.corClassificacao)

                    if (state.corrida.modo == ModoApresentacao.DETALHES) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            state.corrida.camposDetalhes.forEach { campo ->
                                LinhaDetalhe(campo)
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = viewModel::alternarDetalhes) {
                            Text(text = state.corrida.acaoDetalhes)
                        }
                        Button(onClick = viewModel::alternarHistorico) {
                            Text(text = if (state.historicoVisivel) "Ocultar histórico" else "Histórico")
                        }
                        Button(onClick = viewModel::sairInterface) { Text(text = "Sair interface") }
                        Button(onClick = viewModel::fecharApp) { Text(text = "Fechar app") }
                        Button(onClick = viewModel::registrarNotificacao) { Text(text = "NotificationListenerService") }
                        Button(onClick = viewModel::semNotificacao) { Text(text = "Sem notificação") }
                    }
                }
            }

            if (state.historicoVisivel) {
                HistoricoRow(state = state)
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
private fun LinhaCompacta(campos: List<CampoApresentacao>, cor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            modifier = Modifier
                .widthIn(min = 12.dp)
                .background(Color(android.graphics.Color.parseColor(cor)))
                .padding(6.dp),
        )
        campos.forEach { campo -> CampoChip(campo) }
    }
}

@Composable
private fun CampoChip(campo: CampoApresentacao) {
    Box(
        modifier = Modifier.border(1.dp, Color(0xFF46576B)).padding(horizontal = 10.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "${campo.titulo} ${campo.valor}", color = Color.White, fontWeight = if (campo.destaque) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
private fun LinhaDetalhe(campo: CampoApresentacao) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = campo.titulo, color = Color(0xFFDDE6F2))
        Text(text = campo.valor, color = Color(0xFFDDE6F2))
    }
}

@Composable
private fun HistoricoRow(state: AppState) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Histórico", color = Color.White, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
            state.historico.forEach { item ->
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF111821))) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = item.plataforma, color = Color.White, fontWeight = FontWeight.Bold)
                        Text(text = item.data, color = Color(0xFFB7C3D0))
                        Text(text = item.linhaHorizontal, color = Color(0xFFDDE6F2))
                        Text(text = item.classificacao.name, color = Color(android.graphics.Color.parseColor(item.corClassificacao)))
                    }
                }
            }
        }
    }
}

@Composable
private fun OverlayPreview() {
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF213040))) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Overlay ativo sobre Uber / 99 / inDrive",
            color = Color.White,
        )
    }
}

@Composable
private fun SeloFlutuante() {
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF2B3440))) {
        Text(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            text = "◉ Selo flutuante ativo",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
        )
    }
}