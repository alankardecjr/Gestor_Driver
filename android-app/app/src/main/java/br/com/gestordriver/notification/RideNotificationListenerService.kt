package br.com.gestordriver.notification

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class RideNotificationListenerService : NotificationListenerService() {
    private val detector = RideNotificationDetector()
    private val parser = RideParser()

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null) {
            return
        }

        val notification = RideNotification(
            rawText = sbn.notification.extras.getCharSequence("android.text")?.toString().orEmpty(),
            platform = sbn.packageName,
        )
        val detection = detector.detectar(notification)
        parser.parse(detection)
    }
}
