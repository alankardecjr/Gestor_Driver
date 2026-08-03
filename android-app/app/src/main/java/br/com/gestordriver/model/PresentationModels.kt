package br.com.gestordriver.model

enum class PlanoAcesso {
    FREE,
    BETA,
    PRO,
}

enum class ModoApresentacao {
    COMPACTA,
    DETALHES,
}

enum class ClassificacaoVisual {
    EXCELENTE,
    BOA,
    REGULAR,
    BAIXA,
    RUIM,
}

data class CampoApresentacao(
    val chave: String,
    val rotulo: String,
    val valor: String,
    val permitido: Boolean = true,
    val destaque: Boolean = false,
)

data class CorridaPresentation(
    val plano: PlanoAcesso,
    val modo: ModoApresentacao,
    val classificacao: ClassificacaoVisual,
    val corClassificacao: String,
    val acaoDetalhes: String,
    val camposCompactos: List<CampoApresentacao>,
    val camposDetalhes: List<CampoApresentacao>,
)

data class HistoricoItemPresentation(
    val data: String,
    val plataforma: String,
    val linhaHorizontal: String,
    val classificacao: ClassificacaoVisual,
    val corClassificacao: String,
)