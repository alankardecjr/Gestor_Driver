package br.com.gestordriver.flow

import br.com.gestordriver.model.AnaliseCorrida
import br.com.gestordriver.model.CampoApresentacao
import br.com.gestordriver.model.ClassificacaoVisual
import br.com.gestordriver.model.ControlePlano
import br.com.gestordriver.model.CorridaPresentation
import br.com.gestordriver.model.CorridaTeste
import br.com.gestordriver.model.HistoricoItemPresentation
import br.com.gestordriver.model.ModoApresentacao
import br.com.gestordriver.model.PlanoAcesso
import br.com.gestordriver.model.PresentationModel
import br.com.gestordriver.model.RecursosPlano
import br.com.gestordriver.ui.AppState

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

    fun criarEstado(
        plano: PlanoAcesso = PlanoAcesso.BETA,
    ): AppState {

        val analise = corridaTeste.paraAnalise()

        val recursos = ControlePlano().aplicar(
            analise,
            plano,
        )

        val presentationModel = analise.paraPresentation(
            plano,
            recursos,
            historicoTeste,
        )

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

    val valorPorKm =
        if (kmTotal <= 0) {
            0.0
        } else {
            valorTotal / kmTotal
        }

    val combustivelEstimado = 1.28
    val custoCombustivel = 7.92
    val classificacao = ClassificacaoVisual.BOA

    return AnaliseCorrida(
        corrida = this,
        kmTotal = kmTotal,
        valorPorKm = valorPorKm,
        combustivelEstimado = combustivelEstimado,
        custoCombustivel = custoCombustivel,
        classificacao = classificacao,
        corClassificacao = "#7CB342",
    )
}

private fun AnaliseCorrida.paraPresentation(
    plano: PlanoAcesso,
    recursos: RecursosPlano,
    historico: List<HistoricoItemPresentation>,
): PresentationModel {

    val camposCompactos = listOf(

        CampoApresentacao(
            id = "valor_por_km",
            titulo = "🛞 R$/KM",
            valor = if (recursos.exibeValorPorKm) {
                formatDecimal(valorPorKm, 2)
            } else {
                "🔒"
            },
            disponivel = recursos.exibeValorPorKm,
            destaque = true,
        ),

        CampoApresentacao(
            id = "valor_total",
            titulo = "💰 R$ TOTAL",
            valor = formatMoney(corrida.valorTotal),
        ),

        CampoApresentacao(
            id = "km_total",
            titulo = "📍 KM TOTAL",
            valor = formatKm(kmTotal),
        ),

        CampoApresentacao(
            id = "tempo_estimado",
            titulo = "🕐 TEMPO",
            valor = "${corrida.tempoEstimado} min",
        ),

        CampoApresentacao(
            id = "nota_passageiro",
            titulo = "CLASSIFICAÇÃO",
            valor = "${formatDecimal(corrida.notaPassageiro, 2)} ⭐",
        ),
    )

    val camposDetalhes = listOf(

        CampoApresentacao(
            id = "km_ate_passageiro",
            titulo = "Passageiro",
            valor = formatKm(corrida.kmAtePassageiro),
        ),

        CampoApresentacao(
            id = "km_viagem",
            titulo = "Destino",
            valor = formatKm(corrida.kmViagem),
        ),

        CampoApresentacao(
            id = "combustivel_estimado",
            titulo = "Combustível",
            valor = if (recursos.exibeCombustivelEstimado) {
                formatLiters(combustivelEstimado)
            } else {
                "🔒"
            },
            disponivel = recursos.exibeCombustivelEstimado,
        ),

        CampoApresentacao(
            id = "custo_combustivel",
            titulo = "Gasto estimado",
            valor = if (recursos.exibeCustoCombustivel) {
                formatMoney(custoCombustivel)
            } else {
                "🔒"
            },
            disponivel = recursos.exibeCustoCombustivel,
        ),

        CampoApresentacao(
            id = "recursos_avancados",
            titulo = "Recursos avançados",
            valor = if (recursos.recursosAvancados) {
                "Ativo"
            } else {
                "Bloqueado"
            },
            disponivel = recursos.recursosAvancados,
        ),
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

private fun formatDecimal(
    valor: Double,
    casas: Int,
): String =
    "%.${casas}f"
        .format(valor)
        .replace(".", ",")

private fun formatMoney(
    valor: Double,
): String =
    "R$ %.2f"
        .format(valor)
        .replace(".", ",")

private fun formatKm(
    valor: Double,
): String {

    val texto =
        if (valor % 1.0 == 0.0) {
            "%.0f".format(valor)
        } else {
            "%.1f".format(valor)
        }

    return "$texto km"
}

private fun formatLiters(
    valor: Double,
): String =
    "%.2f L"
        .format(valor)
        .replace(".", ",")