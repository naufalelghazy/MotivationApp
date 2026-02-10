# ✨ Motivasi App

Aplikasi Android native yang menampilkan **kutipan motivasi Bahasa Indonesia** untuk membangun rasa percaya diri. Dilengkapi dengan **Home Screen Widget** yang menampilkan kutipan langsung di layar utama HP.

---

## 📱 Fitur

| Fitur                       | Deskripsi                                                                                                                               |
| --------------------------- | --------------------------------------------------------------------------------------------------------------------------------------- |
| 📝 **366 Kutipan Motivasi** | 12+ kategori: Percaya Diri, Semangat, Kehidupan, Pertumbuhan, Mental Kuat, Kebaikan, Impian, Sabar, Sukses, Cinta Diri, Kesehatan, Iman |
| 🔄 **Rotasi Otomatis**      | Kutipan berubah otomatis sesuai interval yang di-setting                                                                                |
| ⏰ **Setting Interval**     | 6 pilihan: 1 jam, 6 jam, 12 jam, 1 hari, 3 hari, 7 hari                                                                                 |
| 🏠 **Home Screen Widget**   | Widget motivasi langsung di home screen Android (Glance)                                                                                |
| ❤️ **Favorit**              | Simpan kutipan yang disukai                                                                                                             |
| 📤 **Share**                | Bagikan kutipan ke WhatsApp, Instagram, dll                                                                                             |
| 📋 **Copy**                 | Salin kutipan ke clipboard                                                                                                              |
| 🔔 **Notifikasi Harian**    | Terima notifikasi motivasi setiap hari                                                                                                  |
| 🌙 **Dark Premium UI**      | Gradient, glassmorphism, animasi floating orbs                                                                                          |

---

## 🛠️ Tech Stack

- **Bahasa**: Kotlin
- **UI**: Jetpack Compose + Material Design 3
- **Widget**: Glance (Jetpack Compose for App Widgets)
- **Background Task**: WorkManager
- **Storage**: SharedPreferences
- **Architecture**: MVVM
- **Min SDK**: 26 (Android 8.0+)
- **Target SDK**: 34

---

## � Quote Repository

Semua kutipan disimpan di file lokal:
`app/src/main/java/com/motivasi/app/data/QuoteRepository.kt`

Project ini sekarang berisi **366 kutipan motivasi** (cukup untuk 1 tahun termasuk tahun kabisat), dengan kategori:

### Kategori Tersedia

- `percaya_diri`
- `semangat`
- `kehidupan`
- `pertumbuhan`
- `mental`
- `kebaikan`
- `impian`
- `sabar`
- `sukses`
- `cinta_diri`
- `kesehatan`
- `iman`

### Menambahkan Kutipan Baru

Untuk menambah kutipan, edit list `quotes` di dalam `QuoteRepository.kt`:

---

## �📂 Struktur Project

```
app/src/main/
├── java/com/motivasi/app/
│   ├── MainActivity.kt              # Entry point
│   ├── MotivasiApp.kt               # Application (WorkManager setup)
│   ├── data/
│   │   ├── QuoteRepository.kt       # 366 kutipan motivasi
│   │   └── PreferencesManager.kt    # Settings & favorit storage
│   ├── ui/
│   │   ├── Navigation.kt            # Bottom navigation (3 tab)
│   │   ├── theme/Theme.kt           # Dark premium color scheme
│   │   └── screens/
│   │       ├── HomeScreen.kt        # Halaman utama + kutipan
│   │       ├── FavoritesScreen.kt   # Daftar kutipan favorit
│   │       └── SettingsScreen.kt    # Pengaturan interval & notifikasi
│   ├── widget/
│   │   ├── MotivationWidget.kt      # Glance home screen widget
│   │   └── MotivationWidgetReceiver.kt
│   └── worker/
│       └── QuoteUpdateWorker.kt     # Background update + notifikasi
└── res/
    ├── drawable/                     # Icons, widget background
    ├── layout/widget_layout.xml      # Widget layout
    ├── values/                       # strings, colors, themes
    └── xml/motivation_widget_info.xml
```

---

## 🚀 Cara Build & Install

### Prasyarat

- **Android Studio** (versi terbaru, disarankan Hedgehog+)
- **JDK 17+**

### Langkah

1. **Buka project** di Android Studio:

   ```
   File → Open → pilih folder MotivationApp
   ```

2. **Tunggu Gradle sync** selesai (otomatis download dependencies)

3. **Build APK**:

   ```
   Build → Build Bundle(s) / APK(s) → Build APK(s)
   ```

   Atau via terminal:

   ```bash
   ./gradlew assembleDebug
   ```

4. **Lokasi APK**:

   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

5. **Install ke HP**:
   - Copy APK ke HP → Buka → Install
   - Atau via ADB:
     ```bash
     adb install app/build/outputs/apk/debug/app-debug.apk
     ```

### Menambahkan Widget

1. Long press di home screen
2. Pilih **Widgets**
3. Cari **"Motivasi"**
4. Drag ke home screen

---

## 🎨 Design

Aplikasi menggunakan dark premium theme dengan:

- **Gradient background**: ungu gelap → hitam
- **Glassmorphism card**: efek kaca transparan
- **Floating orbs**: animasi bola cahaya bergerak
- **Warna utama**: Gold (#E8A838) + Amber (#F5C16C)
- **Material Design 3** components

---

## 📄 Lisensi

Project ini dibuat untuk penggunaan personal. Silakan modifikasi sesuai kebutuhan.
