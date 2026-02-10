package com.motivasi.app.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.motivasi.app.MainActivity
import com.motivasi.app.R
import com.motivasi.app.data.PreferencesManager
import com.motivasi.app.widget.MotivationWidget

class QuoteUpdateWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val CHANNEL_ID = "motivasi_channel"
        const val NOTIFICATION_ID = 1001
    }

    override suspend fun doWork(): Result {
        val prefsManager = PreferencesManager(context)
        val quote = prefsManager.getCurrentQuote()

        // Update widget
        try {
            val manager = GlanceAppWidgetManager(context)
            val widget = MotivationWidget()
            val glanceIds = manager.getGlanceIds(widget.javaClass)
            glanceIds.forEach { glanceId ->
                widget.update(context, glanceId)
            }
        } catch (e: Exception) {
            // Widget might not be placed yet
        }

        // Send notification if enabled
        if (prefsManager.notificationEnabled) {
            sendNotification(quote.text, quote.author)
        }

        return Result.success()
    }

    private fun sendNotification(quoteText: String, author: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create channel for Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Motivasi Harian",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifikasi motivasi harian untuk semangat kamu"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("✨ Motivasi Hari Ini")
            .setContentText(quoteText)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("\"$quoteText\"\n\n— $author")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
