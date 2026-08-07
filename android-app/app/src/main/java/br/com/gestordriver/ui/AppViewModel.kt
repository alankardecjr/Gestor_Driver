package br.com.gestordriver.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.gestordriver.flow.VerticalFlow
import br.com.gestordriver.model.ModoApresentacao
import br.com.gestordriver.model.PlanoAcesso

data class AppState(
    val corrida: br.com.gestordriver.model.CorridaPresentation,
    val historico: List<br.com.gestordriver.model.HistoricoItemPresentation>,
    val historicoVisivel: Boolean = false,
    val overlayAtivo: Boolean = true,
    val notificacaoDisponivel: Boolean = true,
    val seloFlutuante: Boolean = false,
    val monitorando: Boolean = true,
)

class AppViewModel : ViewModel() {

    var state by mutableStateOf(
        VerticalFlow.criarEstado(PlanoAcesso.BETA)
    )
        private set


    fun selecionarPlano(plano: PlanoAcesso) {
        state = VerticalFlow.criarEstado(plano)
    }


    fun alternarDetalhes() {

        val corrida = state.corrida

        val modo =
            if (corrida.modo == ModoApresentacao.COMPACTA) {
                ModoApresentacao.DETALHES
            } else {
                ModoApresentacao.COMPACTA
            }

        state = state.copy(
            corrida = corrida.copy(
                modo = modo,
                acaoDetalhes =
                    if (modo == ModoApresentacao.DETALHES) {
                        "Menos detalhes"
                    } else {
                        "Mais detalhes"
                    },
            ),
        )
    }


    fun alternarHistorico() {

        state = state.copy(
            historicoVisivel = !state.historicoVisivel
        )
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

        state = state.copy(
            overlayAtivo = false,
            monitorando = false,
            seloFlutuante = false,
        )
    }
}