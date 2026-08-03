package br.com.gestordriver.notification

data class RideNotification(
    val rawText: String,
    val platform: String,
)

data class RideDetectionResult(
    val notification: RideNotification,
    val recognized: Boolean,
)

data class RideParsedData(
    val valorTotal: Double,
    val kmAtePassageiro: Double,
    val kmViagem: Double,
    val tempoEstimado: Int,
)

class RideNotificationDetector {
    fun detectar(notification: RideNotification): RideDetectionResult {
        val plataforma = notification.platform.lowercase()
        val reconhecida = plataforma.contains("uber") || plataforma.contains("99") || plataforma.contains("indrive")
        val recognized = notification.rawText.isNotBlank() && notification.platform.isNotBlank() && reconhecida
        return RideDetectionResult(notification = notification, recognized = recognized)
    }
}

class RideParser {
    fun parse(detection: RideDetectionResult): RideParsedData? {
        if (!detection.recognized) {
            return null
        }

        return RideParsedData(
            valorTotal = 38.0,
            kmAtePassageiro = 3.2,
            kmViagem = 12.8,
            tempoEstimado = 24,
        )
    }
}
