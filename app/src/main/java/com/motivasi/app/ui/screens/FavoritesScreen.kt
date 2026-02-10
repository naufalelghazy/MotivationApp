package com.motivasi.app.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.motivasi.app.data.PreferencesManager
import com.motivasi.app.data.Quote
import com.motivasi.app.data.QuoteRepository
import com.motivasi.app.ui.theme.*

@Composable
fun FavoritesScreen() {
    val context = LocalContext.current
    val prefsManager = remember { PreferencesManager(context) }
    var favoriteIds by remember { mutableStateOf(prefsManager.getFavorites()) }
    val favoriteQuotes = remember(favoriteIds) {
        QuoteRepository.getAllQuotes().filter { it.id in favoriteIds }
    }

    FavoritesScreenContent(
        favoriteQuotes = favoriteQuotes,
        onDelete = { quote ->
            prefsManager.removeFavorite(quote.id)
            favoriteIds = prefsManager.getFavorites()
            Toast.makeText(context, "Dihapus dari favorit", Toast.LENGTH_SHORT).show()
        },
        onShare = { quote -> shareQuote(context, quote) }
    )
}

@Composable
fun FavoritesScreenContent(
    favoriteQuotes: List<Quote>,
    onDelete: (Quote) -> Unit = {},
    onShare: (Quote) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientMiddle, GradientEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Header
            Text(
                text = "❤️ Kutipan Favorit",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = "${favoriteQuotes.size} kutipan tersimpan",
                fontSize = 14.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            if (favoriteQuotes.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "💫",
                            fontSize = 64.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Belum ada favorit",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tekan ❤️ pada kutipan yang kamu suka\nuntuk menyimpannya di sini",
                            fontSize = 14.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier.animateContentSize()
                ) {
                    items(favoriteQuotes, key = { it.id }) { quote ->
                        FavoriteQuoteCard(
                            quote = quote,
                            onDelete = { onDelete(quote) },
                            onShare = { onShare(quote) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteQuoteCard(
    quote: Quote,
    onDelete: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Quote text
            Text(
                text = "\"${quote.text}\"",
                fontSize = 15.sp,
                color = TextPrimary,
                lineHeight = 24.sp,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Author
                Text(
                    text = "— ${quote.author}",
                    fontSize = 13.sp,
                    color = PrimaryGold,
                    fontWeight = FontWeight.Medium
                )

                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = onShare,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Bagikan",
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Hapus",
                            tint = Color(0xFFE53E3E).copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
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

// ===== COMPOSE PREVIEW =====

@Preview(
    name = "Favorites - Empty",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_5"
)
@Composable
private fun FavoritesEmptyPreview() {
    MotivasiTheme {
        FavoritesScreenContent(favoriteQuotes = emptyList())
    }
}

@Preview(
    name = "Favorites - With Items",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_5"
)
@Composable
private fun FavoritesWithItemsPreview() {
    MotivasiTheme {
        FavoritesScreenContent(
            favoriteQuotes = listOf(
                Quote(1, "Kamu lebih kuat dari yang kamu pikirkan.", "Anonim", "percaya_diri"),
                Quote(13, "Hidup ini seperti naik sepeda. Untuk menjaga keseimbangan, kamu harus terus bergerak.", "Albert Einstein", "semangat"),
                Quote(21, "Hidup bukan tentang menunggu badai berlalu, tapi belajar menari di tengah hujan.", "Vivian Greene", "kehidupan")
            )
        )
    }
}
