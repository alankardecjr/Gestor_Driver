package br.com.gestordriver.model

data class AnaliseCorrida(
    val corrida: br.com.gestordriver.flow.CorridaTeste,
    val kmTotal: Double,
    val valorPorKm: Double,
    val combustivelEstimado: Double,
    val custoCombustivel: Double,
    val classificacao: ClassificacaoVisual,
    val corClassificacao: String,
)