from dataclasses import dataclass

@dataclass(slots=True)
class ResultadoCombustivel:

    litros: float

    custo: float


class CalculadoraCombustivel:

    @staticmethod
    def calcular(
        km_total: float,
        consumo_km_l: float,
        preco_litro: float
    ) -> ResultadoCombustivel:

        litros = km_total / consumo_km_l
        custo = litros * preco_litro

        return ResultadoCombustivel(
            litros=litros,
            custo=custo
        )