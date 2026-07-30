from dataclasses import dataclass
from enum import Enum


class Combustivel(Enum):

    GASOLINA = "gasolina"

    ETANOL = "etanol"


@dataclass(slots=True)
class ConfiguracaoUsuario:

    marca: str

    modelo: str

    versao: str

    ano: int

    consumo_gasolina: float

    consumo_etanol: float

    preco_gasolina: float

    preco_etanol: float

    combustivel: Combustivel