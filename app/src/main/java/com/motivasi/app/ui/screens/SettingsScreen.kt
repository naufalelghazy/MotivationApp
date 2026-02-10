package com.motivasi.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.motivasi.app.MotivasiApp
import com.motivasi.app.data.PreferencesManager
import com.motivasi.app.data.QuoteRepository
import com.motivasi.app.ui.theme.*

data class IntervalOption(
    val label: String,
    val description: String,
    val hours: Int
)

val intervalOptions = listOf(
    IntervalOption("1 Jam", "Kutipan berubah setiap jam", 1),
    IntervalOption("6 Jam", "4 kutipan per hari", 6),
    IntervalOption("12 Jam", "2 kutipan per hari", 12),
    IntervalOption("1 Hari", "Kutipan harian (default)", 24),
    IntervalOption("3 Hari", "Kutipan setiap 3 hari", 72),
    IntervalOption("7 Hari", "Kutipan mingguan", 168)
)

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val prefsManager = remember { PreferencesManager(context) }
    var selectedInterval by remember { mutableIntStateOf(prefsManager.intervalHours) }
    var notificationEnabled by remember { mutableStateOf(prefsManager.notificationEnabled) }

    SettingsScreenContent(
        selectedInterval = selectedInterval,
        notificationEnabled = notificationEnabled,
        onIntervalChange = { option ->
            selectedInterval = option.hours
            prefsManager.intervalHours = option.hours
            (context.applicationContext as? MotivasiApp)
                ?.scheduleQuoteUpdate(option.hours.toLong())
            Toast.makeText(context, "Interval diubah: ${option.label}", Toast.LENGTH_SHORT).show()
        },
        onNotificationChange = {
            notificationEnabled = it
            prefsManager.notificationEnabled = it
        }
    )
}

@Composable
fun SettingsScreenContent(
    selectedInterval: Int = 24,
    notificationEnabled: Boolean = true,
    onIntervalChange: (IntervalOption) -> Unit = {},
    onNotificationChange: (Boolean) -> Unit = {}
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Header
            Text(
                text = "⚙️ Pengaturan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = "Atur preferensi motivasi kamu",
                fontSize = 14.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp, bottom = 28.dp)
            )

            // Interval Setting Section
            Text(
                text = "INTERVAL ROTASI",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGold,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Seberapa sering kutipan motivasi berubah?",
                fontSize = 14.sp,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            intervalOptions.forEach { option ->
                val isSelected = selectedInterval == option.hours
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable { onIntervalChange(option) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) PrimaryGold.copy(alpha = 0.15f)
                        else CardDark.copy(alpha = 0.5f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = option.label,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) PrimaryGold else TextPrimary
                            )
                            Text(
                                text = option.description,
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Terpilih",
                                tint = PrimaryGold,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Notification Setting
            Text(
                text = "NOTIFIKASI",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGold,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardDark.copy(alpha = 0.5f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Notifikasi Motivasi",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Terima notifikasi motivasi harian",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                    Switch(
                        checked = notificationEnabled,
                        onCheckedChange = onNotificationChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = PrimaryGold,
                            checkedTrackColor = PrimaryGold.copy(alpha = 0.3f),
                            uncheckedThumbColor = TextSecondary,
                            uncheckedTrackColor = CardDark
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // App Info
            Text(
                text = "TENTANG",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGold,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardDark.copy(alpha = 0.5f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    InfoRow("Versi", "1.0.0")
                    InfoRow("Total Kutipan", "${QuoteRepository.getQuoteCount()} motivasi")
                    InfoRow("Kategori", "${QuoteRepository.getCategories().size} kategori")
                    InfoRow("Bahasa", "Indonesia 🇮🇩")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = TextSecondary
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = TextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

// ===== COMPOSE PREVIEW =====

@Preview(
    name = "Settings Screen",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_5"
)
@Composable
private fun SettingsScreenPreview() {
    MotivasiTheme {
        SettingsScreenContent(
            selectedInterval = 24,
            notificationEnabled = true
        )
    }
}
