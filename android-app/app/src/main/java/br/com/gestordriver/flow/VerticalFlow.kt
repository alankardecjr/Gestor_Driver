package br.com.gestordriver.flow

import br.com.gestordriver.model.CampoApresentacao
import br.com.gestordriver.model.ClassificacaoVisual
import br.com.gestordriver.model.CorridaPresentation
import br.com.gestordriver.model.HistoricoItemPresentation
import br.com.gestordriver.model.ModoApresentacao
import br.com.gestordriver.model.PlanoAcesso
import br.com.gestordriver.ui.AppState

data class CorridaTeste(
    val valorTotal: Double,
    val kmAtePassageiro: Double,
    val kmViagem: Double,
    val tempoEstimado: Int,
    val plataforma: String,
    val notaPassageiro: Double,
)

data class AnaliseCorrida(
    val corrida: CorridaTeste,
    val kmTotal: Double,
    val valorPorKm: Double,
    val combustivelEstimado: Double,
    val custoCombustivel: Double,
    val classificacao: ClassificacaoVisual,
    val corClassificacao: String,
)

data class RecursosPlano(
    val exibeValorPorKm: Boolean,
    val exibeCombustivelEstimado: Boolean,
    val exibeCustoCombustivel: Boolean,
    val recursosAvancados: Boolean,
)

class ControlePlano {
    fun aplicar(analise: AnaliseCorrida, plano: PlanoAcesso): RecursosPlano {
        return when (plano) {
            PlanoAcesso.FREE -> RecursosPlano(false, false, false, false)
            PlanoAcesso.BETA -> RecursosPlano(true, true, true, false)
            PlanoAcesso.PRO -> RecursosPlano(true, true, true, true)
        }
    }
}

data class PresentationModel(
    val analise: AnaliseCorrida,
    val plano: PlanoAcesso,
    val corrida: CorridaPresentation,
    val historico: List<HistoricoItemPresentation>,
    val modo: ModoApresentacao,
    val historicoVisivel: Boolean,
    val overlayAtivo: Boolean,
    val notificacaoDisponivel: Boolean,
    val seloFlutuante: Boolean,
    val monitorando: Boolean,
)

object VerticalFlow {
    private val corridaTeste = CorridaTeste(
        valorTotal = 38.0,
        kmAtePassageiro = 3.2,
        kmViagem = 12.8,
        tempoEstimado = 24,
        plataforma = "Uber",
        notaPassageiro = 4.98,
    )

    private val historicoTeste = listOf(
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

    fun criarEstado(plano: PlanoAcesso = PlanoAcesso.BETA): AppState {
        val analise = corridaTeste.paraAnalise()
        val recursos = ControlePlano().aplicar(analise, plano)
        val presentationModel = analise.paraPresentation(plano, recursos, historicoTeste)

        return AppState(
            corrida = presentationModel.corrida,
            historico = presentationModel.historico,
            historicoVisivel = presentationModel.historicoVisivel,
            overlayAtivo = presentationModel.overlayAtivo,
            notificacaoDisponivel = presentationModel.notificacaoDisponivel,
            seloFlutuante = presentationModel.seloFlutuante,
            monitorando = presentationModel.monitorando,
        )
    }
}

private fun CorridaTeste.paraAnalise(): AnaliseCorrida {
    val kmTotal = kmAtePassageiro + kmViagem
    val valorPorKm = if (kmTotal <= 0) 0.0 else valorTotal / kmTotal
    val combustivelEstimado = 1.28
    val custoCombustivel = 7.92
    val classificacao = ClassificacaoVisual.BOA
    val cor = "#7CB342"

    return AnaliseCorrida(
        corrida = this,
        kmTotal = kmTotal,
        valorPorKm = valorPorKm,
        combustivelEstimado = combustivelEstimado,
        custoCombustivel = custoCombustivel,
        classificacao = classificacao,
        corClassificacao = cor,
    )
}

private fun AnaliseCorrida.paraPresentation(
    plano: PlanoAcesso,
    recursos: RecursosPlano,
    historico: List<HistoricoItemPresentation>,
): PresentationModel {
    val camposCompactos = listOf(
        CampoApresentacao("valor_por_km", "R$/KM", if (recursos.exibeValorPorKm) formatDecimal(valorPorKm, 2) else "🔒", recursos.exibeValorPorKm, true),
        CampoApresentacao("valor_total", "Valor total", formatMoney(corrida.valorTotal)),
        CampoApresentacao("km_total", "KM total", formatKm(kmTotal)),
        CampoApresentacao("tempo_estimado", "Tempo", "${corrida.tempoEstimado} min"),
        CampoApresentacao("nota_passageiro", "Nota", formatDecimal(corrida.notaPassageiro, 2)),
    )

    val camposDetalhes = listOf(
        CampoApresentacao("km_ate_passageiro", "Passageiro", formatKm(corrida.kmAtePassageiro)),
        CampoApresentacao("km_viagem", "Destino", formatKm(corrida.kmViagem)),
        CampoApresentacao("combustivel_estimado", "Combustível", if (recursos.exibeCombustivelEstimado) formatLiters(combustivelEstimado) else "🔒", recursos.exibeCombustivelEstimado),
        CampoApresentacao("custo_combustivel", "Gasto estimado", if (recursos.exibeCustoCombustivel) formatMoney(custoCombustivel) else "🔒", recursos.exibeCustoCombustivel),
        CampoApresentacao("recursos_avancados", "Recursos avançados", if (recursos.recursosAvancados) "Ativo" else "Bloqueado", recursos.recursosAvancados),
    )

    return PresentationModel(
        analise = this,
        plano = plano,
        corrida = CorridaPresentation(
            plano = plano,
            modo = ModoApresentacao.COMPACTA,
            classificacao = classificacao,
            corClassificacao = corClassificacao,
            acaoDetalhes = "ⓘ",
            camposCompactos = camposCompactos,
            camposDetalhes = camposDetalhes,
        ),
        historico = historico,
        modo = ModoApresentacao.COMPACTA,
        historicoVisivel = false,
        overlayAtivo = true,
        notificacaoDisponivel = true,
        seloFlutuante = false,
        monitorando = true,
    )
}

private fun formatDecimal(valor: Double, casas: Int): String = "%.$casas f".trim().format(valor).replace(".", ",")

private fun formatMoney(valor: Double): String = "R$ ${String.format("%.2f", valor)}".replace(".", ",")

private fun formatKm(valor: Double): String {
    val texto = if (valor % 1.0 == 0.0) String.format("%.0f", valor) else String.format("%.1f", valor)
    return "$texto km"
}

private fun formatLiters(valor: Double): String = "${String.format("%.2f", valor).replace(".", ",")} L"