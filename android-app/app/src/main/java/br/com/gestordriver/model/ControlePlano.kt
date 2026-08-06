package br.com.gestordriver.model

class ControlePlano {

    fun aplicar(
        analise: AnaliseCorrida,
        plano: PlanoAcesso,
    ): RecursosPlano {

        return when (plano) {

            PlanoAcesso.FREE ->
                RecursosPlano(false, false, false, false)

            PlanoAcesso.BETA ->
                RecursosPlano(true, true, true, false)

            PlanoAcesso.PRO ->
                RecursosPlano(true, true, true, true)
        }
    }
}