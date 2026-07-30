class ValidadorCorrida:

    @staticmethod
    def validar_valor(valor: float):

        if valor <= 0:
            raise ValueError(
                "Valor da corrida inválido."
            )

    @staticmethod
    def validar_km(km: float):

        if km < 0:
            raise ValueError(
                "Quilometragem inválida."
            )