package br.com.gestordriver.model

data class AnaliseCorrida(
    val valorTotal: Double,
    val kmAtePassageiro: Double,
    val kmViagem: Double,
    val kmTotal: Double,
    val tempoEstimado: Int,
    val notaPassageiro: Double,
    val plataforma: String,
    val valorPorKm: Double,
    val combustivelEstimado: Double,
    val custoCombustivel: Double,
    val classificacao: ClassificacaoVisual,
    val corClassificacao: String,
)