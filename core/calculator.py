from core.models import Corrida


class ClassificadorCorrida:

    def classificar(self, valor_por_km: float) -> str:

        if valor_por_km >= 2.50:
            return "EXCELENTE"

        if valor_por_km >= 2.00:
            return "BOA"

        if valor_por_km >= 1.60:
            return "REGULAR"

        return "RUIM"


class CalculadoraCorrida:

    def __init__(self):
        self.classificador = ClassificadorCorrida()

    def calcular(self, corrida: Corrida) -> dict:

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