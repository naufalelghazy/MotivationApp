package com.motivasi.app.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import com.motivasi.app.widget.MotivationWidgetReceiver
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.motivasi.app.data.PreferencesManager
import com.motivasi.app.data.Quote
import com.motivasi.app.ui.theme.*

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val prefsManager = remember { PreferencesManager(context) }

    var currentQuote by remember { mutableStateOf(prefsManager.getCurrentQuote()) }
    var isFavorite by remember { mutableStateOf(prefsManager.isFavorite(currentQuote.id)) }

    HomeScreenContent(
        quote = currentQuote,
        isFavorite = isFavorite,
        intervalHours = prefsManager.intervalHours,
        onFavoriteClick = {
            if (isFavorite) {
                prefsManager.removeFavorite(currentQuote.id)
            } else {
                prefsManager.addFavorite(currentQuote.id)
            }
            isFavorite = !isFavorite
            Toast.makeText(
                context,
                if (isFavorite) "Ditambahkan ke favorit ❤️" else "Dihapus dari favorit",
                Toast.LENGTH_SHORT
            ).show()
        },
        onRefreshClick = {
            currentQuote = prefsManager.forceRefreshQuote()
            isFavorite = prefsManager.isFavorite(currentQuote.id)
            
            // Send broadcast to update widget immediately
            val widgetIntent = Intent(context, MotivationWidgetReceiver::class.java).apply {
                action = MotivationWidgetReceiver.ACTION_REFRESH_WIDGET
            }
            context.sendBroadcast(widgetIntent)
        },
        onShareClick = { shareQuote(context, currentQuote) },
        onCopyClick = {
            clipboardManager.setText(
                AnnotatedString("\"${currentQuote.text}\"\n— ${currentQuote.author}\n\n✨ Motivasi App")
            )
            Toast.makeText(context, "Kutipan disalin! 📋", Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun HomeScreenContent(
    quote: Quote,
    isFavorite: Boolean,
    intervalHours: Int,
    onFavoriteClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onCopyClick: () -> Unit = {}
) {
    // Floating particles animation
    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val particleOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "p1"
    )
    val particleOffset2 by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "p2"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientMiddle, GradientEnd)
                )
            )
    ) {
        // Animated background orbs
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(
                    x = (particleOffset1 * 100 - 50).dp,
                    y = (particleOffset2 * 150 + 50).dp
                )
                .alpha(0.15f)
                .blur(60.dp)
                .clip(CircleShape)
                .background(PrimaryGold)
        )
        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(
                    x = (particleOffset2 * 200 + 100).dp,
                    y = (particleOffset1 * 200 + 300).dp
                )
                .alpha(0.1f)
                .blur(50.dp)
                .clip(CircleShape)
                .background(TertiaryWarm)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Header
            Text(
                text = "✨ Motivasi Hari Ini",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Category badge
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = PrimaryGold.copy(alpha = 0.15f),
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(
                    text = getCategoryLabel(quote.category),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    color = PrimaryGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )
            }

            // Main Quote Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardDark.copy(alpha = 0.6f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.08f),
                                    Color.White.copy(alpha = 0.02f),
                                    Color.Transparent
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Opening quote mark
                        Text(
                            text = "❝",
                            fontSize = 48.sp,
                            color = PrimaryGold.copy(alpha = 0.5f),
                            lineHeight = 48.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Quote text
                        Text(
                            text = quote.text,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = TextPrimary,
                            textAlign = TextAlign.Center,
                            lineHeight = 32.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Divider
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(2.dp)
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            PrimaryGold.copy(alpha = 0.5f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Author
                        Text(
                            text = "— ${quote.author}",
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Favorite Button
                FilledIconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = if (isFavorite) Color(0xFFE53E3E).copy(alpha = 0.2f)
                        else CardDark.copy(alpha = 0.6f)
                    )
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorit",
                        tint = if (isFavorite) Color(0xFFE53E3E) else TextSecondary
                    )
                }

                // Refresh Button (Main)
                FilledIconButton(
                    onClick = onRefreshClick,
                    modifier = Modifier.size(72.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = PrimaryGold,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Kutipan Baru",
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Share Button
                FilledIconButton(
                    onClick = onShareClick,
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = CardDark.copy(alpha = 0.6f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Bagikan",
                        tint = TextSecondary
                    )
                }

                // Copy Button
                FilledIconButton(
                    onClick = onCopyClick,
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = CardDark.copy(alpha = 0.6f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = "Salin",
                        tint = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer info
            Text(
                text = "Kutipan berubah setiap ${getIntervalLabel(intervalHours)}",
                fontSize = 12.sp,
                color = TextSecondary.copy(alpha = 0.5f),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

private fun shareQuote(context: Context, quote: Quote) {
    val shareText = "\"${quote.text}\"\n— ${quote.author}\n\n✨ Dari Motivasi App"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(Intent.createChooser(intent, "Bagikan Motivasi"))
}

private fun getCategoryLabel(category: String): String {
    return when (category) {
        "percaya_diri" -> "💪 PERCAYA DIRI"
        "semangat" -> "🔥 SEMANGAT"
        "kehidupan" -> "🌱 KEHIDUPAN"
        "pertumbuhan" -> "📈 PERTUMBUHAN"
        "mental" -> "🧠 MENTAL KUAT"
        "kebaikan" -> "💛 KEBAIKAN"
        "impian" -> "⭐ IMPIAN"
        "sabar" -> "🕊️ SABAR & IKHLAS"
        "sukses" -> "🏆 SUKSES"
        "cinta_diri" -> "💖 CINTA DIRI"
        else -> "✨ UMUM"
    }
}

private fun getIntervalLabel(hours: Int): String {
    return when (hours) {
        1 -> "1 jam"
        6 -> "6 jam"
        12 -> "12 jam"
        24 -> "1 hari"
        72 -> "3 hari"
        168 -> "7 hari"
        else -> "$hours jam"
    }
}

// ===== COMPOSE PREVIEW =====
// Preview ini akan muncul di panel "Design" / "Split" di Android Studio
// Tanpa perlu menjalankan app di emulator/HP

@Preview(
    name = "Home Screen",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_5"
)
@Composable
private fun HomeScreenPreview() {
    MotivasiTheme {
        HomeScreenContent(
            quote = Quote(
                id = 1,
                text = "Kamu lebih kuat dari yang kamu pikirkan. Percayalah pada dirimu sendiri.",
                author = "Anonim",
                category = "percaya_diri"
            ),
            isFavorite = false,
            intervalHours = 24
        )
    }
}

@Preview(
    name = "Home Screen - Favorited",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_5"
)
@Composable
private fun HomeScreenFavoritedPreview() {
    MotivasiTheme {
        HomeScreenContent(
            quote = Quote(
                id = 13,
                text = "Hidup ini seperti naik sepeda. Untuk menjaga keseimbangan, kamu harus terus bergerak.",
                author = "Albert Einstein",
                category = "semangat"
            ),
            isFavorite = true,
            intervalHours = 24
        )
    }
}
