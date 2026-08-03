package br.com.gestordriver.ui

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.gestordriver.model.CampoApresentacao
import br.com.gestordriver.model.ClassificacaoVisual
import br.com.gestordriver.model.CorridaPresentation
import br.com.gestordriver.model.HistoricoItemPresentation
import br.com.gestordriver.model.ModoApresentacao
import br.com.gestordriver.model.PlanoAcesso

data class AppState(
    val corrida: CorridaPresentation = corridaPadrao(PlanoAcesso.BETA),
    val historico: List<HistoricoItemPresentation> = historicoPadrao(),
    val historicoVisivel: Boolean = false,
    val overlayAtivo: Boolean = true,
    val notificacaoDisponivel: Boolean = true,
    val seloFlutuante: Boolean = false,
    val monitorando: Boolean = true,
)

class AppViewModel : ViewModel() {
    var state by mutableStateOf(AppState())
        private set

    fun selecionarPlano(plano: PlanoAcesso) {
        state = state.copy(corrida = corridaPadrao(plano))
    }

    fun alternarDetalhes() {
        val corrida = state.corrida
        val modo = if (corrida.modo == ModoApresentacao.COMPACTA) {
            ModoApresentacao.DETALHES
        } else {
            ModoApresentacao.COMPACTA
        }

        state = state.copy(
            corrida = corrida.copy(
                modo = modo,
                acaoDetalhes = if (modo == ModoApresentacao.DETALHES) "Menos detalhes" else "Mais detalhes",
            ),
        )
    }

    fun alternarHistorico() {
        state = state.copy(historicoVisivel = !state.historicoVisivel)
    }

    fun registrarNotificacao() {
        state = state.copy(
            overlayAtivo = true,
            notificacaoDisponivel = true,
            seloFlutuante = false,
            monitorando = true,
        )
    }

    fun semNotificacao() {
        state = state.copy(
            historicoVisivel = false,
            overlayAtivo = false,
            notificacaoDisponivel = false,
            seloFlutuante = true,
            monitorando = true,
        )
    }

    fun sairInterface() {
        state = state.copy(
            historicoVisivel = false,
            overlayAtivo = false,
            seloFlutuante = false,
        )
    }

    fun fecharApp() {
        state = state.copy(overlayAtivo = false, monitorando = false, seloFlutuante = false)
    }
}

private fun corridaPadrao(plano: PlanoAcesso): CorridaPresentation {
    val exibeFinanceiro = plano != PlanoAcesso.FREE
    val exibeAvancado = plano == PlanoAcesso.PRO
    return CorridaPresentation(
        plano = plano,
        modo = ModoApresentacao.COMPACTA,
        classificacao = ClassificacaoVisual.BOA,
        corClassificacao = "#7CB342",
        acaoDetalhes = "Mais detalhes",
        camposCompactos = listOf(
            CampoApresentacao("valor_por_km", "R$/KM", if (exibeFinanceiro) "2,38" else "🔒", exibeFinanceiro, true),
            CampoApresentacao("valor_total", "Valor total", "R$ 38,00"),
            CampoApresentacao("km_total", "KM total", "16 km"),
            CampoApresentacao("tempo_estimado", "Tempo", "24 min"),
            CampoApresentacao("nota_passageiro", "Nota", "4,98"),
        ),
        camposDetalhes = listOf(
            CampoApresentacao("km_ate_passageiro", "Passageiro", "3,2 km"),
            CampoApresentacao("km_viagem", "Destino", "12,8 km"),
            CampoApresentacao("combustivel_estimado", "Combustível", if (exibeFinanceiro) "1,28 L" else "🔒", exibeFinanceiro),
            CampoApresentacao("custo_combustivel", "Gasto estimado", if (exibeFinanceiro) "R$ 7,92" else "🔒", exibeFinanceiro),
            CampoApresentacao("recursos_avancados", "Recursos avançados", if (exibeAvancado) "Ativo" else "Bloqueado", exibeAvancado),
        ),
    )
}

private fun historicoPadrao(): List<HistoricoItemPresentation> = listOf(
    HistoricoItemPresentation(
        data = "03/08 12:00",
        plataforma = "Uber",
        linhaHorizontal = "2,38 │ R$ 38,00 │ 16 km │ 24 min │ 4,98",
        classificacao = ClassificacaoVisual.BOA,
        corClassificacao = "#7CB342",
    ),
    HistoricoItemPresentation(
        data = "03/08 11:00",
        plataforma = "99",
        linhaHorizontal = "2,65 │ R$ 22,50 │ 8,5 km │ 18 min │ —",
        classificacao = ClassificacaoVisual.REGULAR,
        corClassificacao = "#F9A825",
    ),
)