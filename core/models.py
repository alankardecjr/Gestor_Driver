from dataclasses import dataclass
from typing import Optional


@dataclass
class Corrida:
    valor_total: float
    km_ate_passageiro: float
    km_viagem: float
    tempo_estimado: Optional[int] = None

    @property
    def km_total(self) -> float:
        return self.km_ate_passageiro + self.km_viagem

    @property
    def valor_por_km(self) -> float:
        if self.km_total <= 0:
            return 0.0

        return self.valor_total / self.km_total