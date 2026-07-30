"""Motor principal de calculo e classificacao de corridas.

Contem a logica de negocio para transformar uma entidade Corrida em um
resumo pronto para exibicao, incluindo o indicador valor por km.
"""

from core.models import Corrida


class ClassificadorCorrida:
    """Classifica a corrida com base no valor arrecadado por km."""

    def classificar(self, valor_por_km: float) -> str:
        """Retorna a faixa de desempenho da corrida.

        Args:
            valor_por_km: Valor monetario obtido por quilometro total.

        Returns:
            Uma classificacao textual: EXCELENTE, BOA, REGULAR ou RUIM.
        """

        if valor_por_km >= 2.50:
            return "EXCELENTE"

        if valor_por_km >= 2.00:
            return "BOA"

        if valor_por_km >= 1.60:
            return "REGULAR"

        return "RUIM"


class CalculadoraCorrida:
    """Orquestra o calculo dos indicadores exibidos ao motorista."""

    def __init__(self):
        """Inicializa dependencias de classificacao."""
        self.classificador = ClassificadorCorrida()

    def calcular(self, corrida: Corrida) -> dict:
        """Consolida os dados da corrida em um dicionario de resultado.

        Args:
            corrida: Objeto com valor, distancia e tempo estimado.

        Returns:
            Dicionario com metricas operacionais e classificacao final.
        """

        valor_por_km = corrida.valor_por_km

        classificacao = self.classificador.classificar(
            valor_por_km
        )

        return {
            "valor_total": corrida.valor_total,
            "km_ate_passageiro": corrida.km_ate_passageiro,
            "km_viagem": corrida.km_viagem,
            "km_total": corrida.km_total,
            "tempo_estimado": corrida.tempo_estimado,
            "valor_por_km": valor_por_km,
            "classificacao": classificacao,
        }