package br.com.gestordriver.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import br.com.gestordriver.model.ModoApresentacao
import br.com.gestordriver.model.PlanoAcesso

class AppViewModelTest {
    @Test
    fun deve_alternar_detalhes_e_historico() {
        val viewModel = AppViewModel()

        assertEquals(ModoApresentacao.COMPACTA, viewModel.state.corrida.modo)
        assertFalse(viewModel.state.historicoVisivel)

        viewModel.alternarDetalhes()

        assertEquals(ModoApresentacao.DETALHES, viewModel.state.corrida.modo)
        assertEquals("Menos detalhes", viewModel.state.corrida.acaoDetalhes)

        viewModel.alternarHistorico()

        assertTrue(viewModel.state.historicoVisivel)
    }

    @Test
    fun deve_selecionar_plano_e_controlar_overlay() {
        val viewModel = AppViewModel()

        viewModel.selecionarPlano(PlanoAcesso.PRO)
        assertEquals(PlanoAcesso.PRO, viewModel.state.corrida.plano)
        assertEquals("Ativo", viewModel.state.corrida.camposDetalhes.last().valor)

        viewModel.semNotificacao()
        assertFalse(viewModel.state.notificacaoDisponivel)
        assertTrue(viewModel.state.seloFlutuante)

        viewModel.sairInterface()
        assertFalse(viewModel.state.historicoVisivel)
        assertFalse(viewModel.state.overlayAtivo)
        assertFalse(viewModel.state.seloFlutuante)

        viewModel.fecharApp()
        assertFalse(viewModel.state.overlayAtivo)
        assertFalse(viewModel.state.monitorando)
    }
}