"""Estruturas de saída para análise consolidada de corrida.

Este módulo define o contrato de dados usado após o processamento da
calculadora, reunindo indicadores financeiros e operacionais da corrida.
"""

from dataclasses import dataclass
from core.models import Corrida
from core.classifier import Classificacao

@dataclass(slots=True)
class AnaliseCorrida:
    """Representa o resultado analítico completo de uma corrida.

    Attributes:
        corrida: Entidade original da corrida avaliada.
        km_total: Soma de deslocamento até o passageiro e da viagem.
        valor_por_km: Indicador principal de rentabilidade por distancia.
        combustivel_estimado: Litros previstos para concluir o trajeto.
        custo_combustivel: Custo estimado de combustivel da corrida.
        classificacao: Faixa de desempenho calculada para a corrida.
    """

    corrida: Corrida

    km_total: float
    valor_por_km: float

    combustivel_estimado: float
    custo_combustivel: float

    classificacao: Classificacao