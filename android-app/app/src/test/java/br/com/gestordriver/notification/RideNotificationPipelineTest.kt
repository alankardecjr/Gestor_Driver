package br.com.gestordriver.notification

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class RideNotificationPipelineTest {
    @Test
    fun deve_reconhecer_notificacao_valida_e_parsear_corrida() {
        val detector = RideNotificationDetector()
        val parser = RideParser()

        val detection = detector.detectar(
            RideNotification(
                rawText = "R$ 38,00 • 3,2 km ate o passageiro • 12,8 km viagem • 24 min",
                platform = "br.com.gestordriver.uber",
            ),
        )

        assertTrue(detection.recognized)

        val parsed = parser.parse(detection)
        assertEquals(38.0, parsed?.valorTotal)
        assertEquals(3.2, parsed?.kmAtePassageiro)
        assertEquals(12.8, parsed?.kmViagem)
        assertEquals(24, parsed?.tempoEstimado)
    }

    @Test
    fun nao_deve_parsear_notificacao_nao_reconhecida() {
        val detector = RideNotificationDetector()
        val parser = RideParser()

        val detection = detector.detectar(
            RideNotification(
                rawText = "",
                platform = "",
            ),
        )

        assertFalse(detection.recognized)
        assertNull(parser.parse(detection))
    }

    @Test
    fun deve_rejeitar_plataforma_desconhecida() {
        val detector = RideNotificationDetector()

        val detection = detector.detectar(
            RideNotification(
                rawText = "R$ 20,00 • 2,0 km ate o passageiro • 7,0 km viagem • 19 min",
                platform = "com.exemplo.desconhecido",
            ),
        )

        assertFalse(detection.recognized)
    }
}