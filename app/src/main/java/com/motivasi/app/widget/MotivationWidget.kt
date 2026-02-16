package com.motivasi.app.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.cornerRadius
import androidx.glance.layout.*
import androidx.glance.text.*
import androidx.glance.unit.ColorProvider
import com.motivasi.app.MainActivity
import com.motivasi.app.data.PreferencesManager

class MotivationWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val prefsManager = PreferencesManager(context)
        val quote = prefsManager.getCurrentQuote()

        provideContent {
            WidgetContent(
                quoteText = quote.text,
                authorText = quote.author
            )
        }
    }

    @Composable
    private fun WidgetContent(quoteText: String, authorText: String) {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(20.dp)
                .background(Color(0xFF1A1520))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = GlanceModifier.fillMaxWidth()
            ) {
                // Header row: App label + Refresh button
                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // App label (clickable → open app)
                    Text(
                        text = "✨ Motivasi",
                        style = TextStyle(
                            color = ColorProvider(Color(0xFFE8A838)),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = GlanceModifier
                            .defaultWeight()
                            .clickable(actionStartActivity<MainActivity>())
                    )

                    // Refresh button
                    Text(
                        text = "🔄",
                        style = TextStyle(
                            fontSize = 20.sp
                        ),
                        modifier = GlanceModifier
                            .padding(4.dp)
                            .cornerRadius(8.dp)
                            .clickable(actionRunCallback<RefreshWidgetAction>())
                    )
                }

                Spacer(modifier = GlanceModifier.height(12.dp))

                // Quote (clickable → open app)
                Text(
                    text = "\"$quoteText\"",
                    style = TextStyle(
                        color = ColorProvider(Color(0xFFF5F0EA)),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 5,
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .clickable(actionStartActivity<MainActivity>())
                )

                Spacer(modifier = GlanceModifier.height(12.dp))

                // Author
                Text(
                    text = "— $authorText",
                    style = TextStyle(
                        color = ColorProvider(Color(0xFFB8A8C8)),
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                )
            }
        }
    }
}

/**
 * ActionCallback for the widget refresh button.
 * When tapped, it force-refreshes the quote and re-renders the widget.
 */
class RefreshWidgetAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        // Force refresh the quote
        val prefsManager = PreferencesManager(context)
        prefsManager.forceRefreshQuote()

        // Re-render widget with new quote
        MotivationWidget().update(context, glanceId)
    }
}
