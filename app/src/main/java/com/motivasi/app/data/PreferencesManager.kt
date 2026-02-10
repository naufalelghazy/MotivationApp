package com.motivasi.app.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("motivasi_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_INTERVAL_HOURS = "interval_hours"
        private const val KEY_NOTIFICATION_ENABLED = "notification_enabled"
        private const val KEY_FAVORITES = "favorites"
        private const val KEY_LAST_QUOTE_INDEX = "last_quote_index"
        private const val KEY_LAST_UPDATE_TIME = "last_update_time"
    }

    var intervalHours: Int
        get() = prefs.getInt(KEY_INTERVAL_HOURS, 24)
        set(value) = prefs.edit().putInt(KEY_INTERVAL_HOURS, value).apply()

    var notificationEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATION_ENABLED, true)
        set(value) = prefs.edit().putBoolean(KEY_NOTIFICATION_ENABLED, value).apply()

    var lastQuoteIndex: Int
        get() = prefs.getInt(KEY_LAST_QUOTE_INDEX, 0)
        set(value) = prefs.edit().putInt(KEY_LAST_QUOTE_INDEX, value).apply()

    var lastUpdateTime: Long
        get() = prefs.getLong(KEY_LAST_UPDATE_TIME, 0)
        set(value) = prefs.edit().putLong(KEY_LAST_UPDATE_TIME, value).apply()

    fun getFavorites(): Set<Int> {
        return prefs.getStringSet(KEY_FAVORITES, emptySet())
            ?.map { it.toInt() }
            ?.toSet() ?: emptySet()
    }

    fun addFavorite(quoteId: Int) {
        val current = getFavorites().toMutableSet()
        current.add(quoteId)
        prefs.edit().putStringSet(KEY_FAVORITES, current.map { it.toString() }.toSet()).apply()
    }

    fun removeFavorite(quoteId: Int) {
        val current = getFavorites().toMutableSet()
        current.remove(quoteId)
        prefs.edit().putStringSet(KEY_FAVORITES, current.map { it.toString() }.toSet()).apply()
    }

    fun isFavorite(quoteId: Int): Boolean {
        return getFavorites().contains(quoteId)
    }

    fun getCurrentQuote(): Quote {
        val now = System.currentTimeMillis()
        val intervalMillis = intervalHours.toLong() * 60 * 60 * 1000

        return if (lastUpdateTime == 0L || (now - lastUpdateTime) >= intervalMillis) {
            // Calculate day of year (1-366)
            val calendar = java.util.Calendar.getInstance()
            calendar.timeInMillis = now
            val dayOfYear = calendar.get(java.util.Calendar.DAY_OF_YEAR)
            
            val quote = QuoteRepository.getQuoteForDate(dayOfYear)
            lastQuoteIndex = quote.id
            lastUpdateTime = now
            quote
        } else {
            // Get quote by ID, fallback to first quote if not found
            QuoteRepository.getQuoteById(lastQuoteIndex) ?: QuoteRepository.getAllQuotes().first()
        }
    }

    fun forceRefreshQuote(): Quote {
        val now = System.currentTimeMillis()
        val allQuotes = QuoteRepository.getAllQuotes()
        val currentIndex = lastQuoteIndex - 1
        var newIndex = (currentIndex + 1) % allQuotes.size
        if (newIndex == currentIndex) newIndex = (newIndex + 1) % allQuotes.size
        val quote = allQuotes[newIndex]
        lastQuoteIndex = quote.id
        lastUpdateTime = now
        return quote
    }
}
