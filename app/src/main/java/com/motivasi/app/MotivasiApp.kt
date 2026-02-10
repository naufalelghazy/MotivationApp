package com.motivasi.app

import android.app.Application
import androidx.work.*
import com.motivasi.app.worker.QuoteUpdateWorker
import java.util.concurrent.TimeUnit

class MotivasiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        scheduleQuoteUpdate()
    }

    fun scheduleQuoteUpdate(intervalHours: Long = 24) {
        val workRequest = PeriodicWorkRequestBuilder<QuoteUpdateWorker>(
            intervalHours, TimeUnit.HOURS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "quote_update",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}
