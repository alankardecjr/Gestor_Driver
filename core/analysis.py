from dataclasses import dataclass
from core.models import Corrida
from core.classifier import Classificacao

@dataclass(slots=True)
class AnaliseCorrida:
    corrida: Corrida

    km_total: float
    valor_por_km: float

    combustivel_estimado: float
    custo_combustivel: float

    classificacao: Classificacao