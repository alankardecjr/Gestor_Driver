from pathlib import Path
import sys

PROJECT_ROOT = Path(__file__).resolve().parents[1]
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

from core.models import Corrida
from core.calculator import CalculadoraCorrida


def main():

    corrida = Corrida(
        valor_total=38.00,
        km_ate_passageiro=3.2,
        km_viagem=12.8,
        tempo_estimado=24
    )

    calculadora = CalculadoraCorrida()

    resultado = calculadora.calcular(corrida)

    print("GESTOR DRIVER")
    print("-" * 30)

    print(f"💰 R$ {resultado['valor_total']:.2f}")
    print(f"📍 {resultado['km_total']:.1f} km")
    print(f"⏱ {resultado['tempo_estimado']} min")
    print(f"⭐ R$/KM {resultado['valor_por_km']:.2f}")
    print(f"🟢 {resultado['classificacao']}")


if __name__ == "__main__":
    main()