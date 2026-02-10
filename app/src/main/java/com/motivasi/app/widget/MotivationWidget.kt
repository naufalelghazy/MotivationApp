package com.motivasi.app.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
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
                .clickable(actionStartActivity<MainActivity>())
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = GlanceModifier.fillMaxWidth()
            ) {
                // App label
                Text(
                    text = "✨ Motivasi",
                    style = TextStyle(
                        color = ColorProvider(Color(0xFFE8A838)),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = GlanceModifier.height(8.dp))

                // Quote
                Text(
                    text = "\"$quoteText\"",
                    style = TextStyle(
                        color = ColorProvider(Color(0xFFF5F0EA)),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 5
                )

                Spacer(modifier = GlanceModifier.height(8.dp))

                // Author
                Text(
                    text = "— $authorText",
                    style = TextStyle(
                        color = ColorProvider(Color(0xFFB8A8C8)),
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic
                    )
                )
            }
        }
    }
}
