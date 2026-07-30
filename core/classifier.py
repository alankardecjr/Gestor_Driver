"""Enumeracoes de classificacao de rentabilidade de corrida."""

from enum import Enum

class Classificacao(Enum):
    """Faixas de desempenho financeiro por corrida."""

    EXCELENTE = "excelente"

    BOA = "boa"

    REGULAR = "regular"

    BAIXA = "baixa"

    RUIM = "ruim"