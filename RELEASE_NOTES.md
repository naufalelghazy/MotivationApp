# 🎉 Motivasi App v1.1.0

> **Aplikasi kutipan motivasi harian dalam Bahasa Indonesia dengan widget support**

![Version](https://img.shields.io/badge/version-1.1.0-blue)
![Platform](https://img.shields.io/badge/platform-Android-green)
![Min SDK](https://img.shields.io/badge/minSDK-26-orange)
![License](https://img.shields.io/badge/license-MIT-purple)

---

## ✨ Fitur Utama

- 📚 **366 Kutipan Motivasi** dalam Bahasa Indonesia (satu untuk setiap hari dalam setahun, termasuk tahun kabisat)
- 🎨 **12 Kategori Beragam**: Motivasi, Kesuksesan, Kerja Keras, Impian, Kegagalan, Waktu, Perubahan, Kebahagiaan, Kepercayaan Diri, Belajar, Kesabaran, Kehidupan
- 🏠 **Home Widget dengan Refresh Button** - Lihat dan refresh kutipan langsung di layar utama
- � **Widget Refresh Button** - Tap 🔄 di widget untuk ganti kutipan tanpa buka app
- �🔔 **Notifikasi Harian** - Pengingat otomatis dengan kutipan baru
- ⭐ **Favorit** - Simpan kutipan favoritmu
- � **Auto-Refresh** - Kutipan berubah otomatis setiap hari
- 🎯 **Material Design 3** - UI modern dan indah
- 🌙 **Dark Mode Support** - Nyaman di mata
- � **Optimized for Tablets** - Font widget lebih besar untuk layar besar

---

## 🆕 What's New in v1.1.0

### **Added**

- 🔄 **Widget Refresh Button** - Tap icon 🔄 di widget untuk instantly refresh quote tanpa buka app
- 📡 **Instant Widget Sync** - Widget langsung update ketika refresh di app (fixed delay di Xiaomi/HyperOS)
- 📦 **APK Size Optimization** - R8 minification & resource shrinking enabled (~1.6 MB)

### **Fixed**

- 🐛 Fixed widget tidak update di Xiaomi devices (Poco F6, Redmi Note series)
- 🐛 Fixed race condition antara app dan widget data sync
- 🐛 Fixed padding issues untuk kutipan pendek
- 🐛 Fixed widget font terlalu kecil di tablet

### **Improved**

- ⚡ Widget refresh sekarang pakai Glance ActionCallback (instant, no delay)
- ⚡ App-to-widget communication pakai broadcast (lebih cepat dari WorkManager)
- 🎨 Quote card padding lebih balanced (24dp vs 28dp)
- 📱 Widget font lebih besar untuk tablet (+29% average)

---

## 📥 Download APK

**Recommended:**

| APK                 | Ukuran | Rekomendasi                         |
| ------------------- | ------ | ----------------------------------- |
| **app-release.apk** | 1.6 MB | ⭐ **Recommended** - Untuk semua HP |

> 💡 Universal APK - berfungsi di semua device Android!

---

## 📱 Cara Install

1. **Download APK** dari Assets di bawah
2. **Buka file APK** yang sudah didownload
3. **Izinkan instalasi** dari sumber tidak dikenal (jika diminta)
4. **Install** dan buka aplikasi
5. **Nikmati** kutipan motivasi harian! 🎉

### 📌 Khusus Pengguna Xiaomi (HyperOS/MIUI)

Untuk performa widget optimal, lakukan whitelist app:

```
Settings → Apps → Motivasi App → Battery saver → No restrictions
Settings → Apps → Motivasi App → Autostart → Enable
```

Panduan lengkap ada di [Xiaomi Widget Guide](https://github.com/naufalelghazy/MotivationApp/blob/master/docs/xiaomi_widget_guide.md)

---

## 🛠️ Tech Stack

- **Kotlin** - Programming language
- **Jetpack Compose** - Modern UI toolkit
- **Material Design 3** - Design system
- **Glance API** - App Widget framework with ActionCallback
- **WorkManager** - Background tasks
- **SharedPreferences** - Data persistence
- **Navigation Compose** - In-app navigation

---

## 📸 Screenshots

> _Screenshots akan ditambahkan di update berikutnya_

---

## 🔧 Technical Details

- **Min SDK**: Android 8.0 (API 26)
- **Target SDK**: Android 14 (API 34)
- **Build Tools**: AGP 8.2.2, Kotlin 1.9.22
- **Optimizations**: R8 minification, resource shrinking enabled
- **APK Size**: ~1.6 MB (optimized)
- **Version Code**: 2
- **Version Name**: 1.1.0

---

## 🚀 Fitur Mendatang (v1.2.0)

- [ ] Share kutipan ke social media
- [ ] Customizable widget themes
- [ ] Export favorit ke file
- [ ] Tambah kutipan custom
- [ ] Multiple widget sizes
- [ ] Quote of the week

---

## 📝 Changelog

### v1.1.0 (2026-02-16)

**Features:**

- ✅ Widget refresh button (🔄 icon)
- ✅ Broadcast-based instant widget sync
- ✅ APK optimization (R8 minification)
- ✅ Larger widget fonts for tablets

**Bug Fixes:**

- ✅ Fixed widget delay on Xiaomi devices
- ✅ Fixed SharedPreferences race condition
- ✅ Fixed padding for short quotes
- ✅ Fixed small widget fonts on tablets

**Technical:**

- ✅ Glance ActionCallback for widget refresh
- ✅ Broadcast receiver for widget updates
- ✅ Optimized quote card layout (24dp padding)
- ✅ Widget fonts increased (+29% average)

### v1.0.0 (2026-02-10)

**Initial Release**

- ✅ 366 kutipan motivasi Indonesia
- ✅ 12 kategori kutipan
- ✅ Home screen widget
- ✅ Daily notifications
- ✅ Favorites system
- ✅ Material Design 3 UI
- ✅ Dark mode support
- ✅ Auto-refresh quotes

---

## 👨‍💻 Developer

**Naufal Elghazy**

- GitHub: [@naufalelghazy](https://github.com/naufalelghazy)
- Repository: [MotivationApp](https://github.com/naufalelghazy/MotivationApp)

---

## 📄 License

MIT License - Feel free to use and modify!

---

## 🙏 Terima Kasih

Terima kasih sudah menggunakan Motivasi App! Jika ada bug atau saran, silakan buat [Issue](https://github.com/naufalelghazy/MotivationApp/issues) di GitHub.

**Jangan lupa kasih ⭐ jika kamu suka aplikasi ini!**

---

_"Mulailah dari mana kamu berada, gunakan apa yang kamu punya, lakukan apa yang kamu bisa."_
