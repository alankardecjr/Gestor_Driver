package br.com.gestordriver.model

data class HistoricoItemPresentation(
    val data: String,
    val plataforma: String,
    val linhaHorizontal: String,
    val classificacao: ClassificacaoVisual,
    val corClassificacao: String,
)