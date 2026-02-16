# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2026-02-16

### Added

- 🔄 **Widget refresh button** - Tap the 🔄 icon on the widget to instantly refresh the quote without opening the app
- 📡 **Broadcast-based widget sync** - Widget now updates immediately when you refresh in the app (fixed HyperOS/MIUI delay issue)
- 📦 **APK size optimization** - Enabled R8 minification and resource shrinking (APK size reduced to ~1.6 MB)
- 🏗️ **ABI splits configuration** - Support for generating architecture-specific APKs

### Fixed

- 🐛 Fixed widget not updating when refresh button is tapped in the app (especially on Xiaomi devices)
- 🐛 Fixed race condition between app and widget data synchronization
- 🐛 Fixed `getQuoteForDate()` parameter mismatch in `PreferencesManager`
- 🐛 Fixed unresolved reference to `getQuoteByIndex()` - replaced with `getQuoteById()`
- 🐛 Fixed widget hanging on old quote after manual refresh

### Changed

- ⚡ Widget refresh now uses Glance `ActionCallback` for instant updates (no IPC delay)
- ⚡ App-to-widget communication now uses broadcast instead of WorkManager for faster sync
- 🎨 Widget UI updated with header row containing app label and refresh button

### Technical Improvements

- Added `ACTION_REFRESH_WIDGET` broadcast action for widget updates
- Added `RefreshWidgetAction` ActionCallback for widget-initiated refresh
- Optimized widget update mechanism to prevent SharedPreferences race conditions
- Build configuration improvements: `isMinifyEnabled = true`, `isShrinkResources = true`

---

## [1.0.0] - 2026-02-10

### Added

- ✨ Initial release of Motivasi App
- 📝 366 Indonesian motivational quotes covering 12+ categories
- 🏠 Home screen widget using Glance API
- 🔄 Automatic quote rotation with configurable intervals (1h - 7 days)
- ❤️ Favorite quotes feature with local storage
- 📤 Share quotes to social media and messaging apps
- 📋 Copy quotes to clipboard
- 🔔 Daily motivation notifications
- ⚙️ Settings screen with interval and notification controls
- 🎨 Premium dark theme with gradient backgrounds and glassmorphism
- 🌊 Animated floating orbs background effect
- 📱 Bottom navigation with 3 tabs (Home, Favorites, Settings)
- 👁️ Compose Preview support for all screens
- 📚 Comprehensive documentation (README.md)

### Categories

- Percaya Diri (Self-confidence)
- Semangat (Spirit/Motivation)
- Kehidupan (Life)
- Pertumbuhan (Growth)
- Mental Kuat (Mental Strength)
- Kebaikan (Kindness)
- Impian (Dreams)
- Sabar (Patience)
- Sukses (Success)
- Cinta Diri (Self-love)
- Kesehatan (Health)
- Iman (Faith)

### Technical Details

- Built with Kotlin and Jetpack Compose
- Material Design 3 components
- WorkManager for background tasks
- SharedPreferences for data persistence
- Min SDK: 26 (Android 8.0+)
- Target SDK: 34

---

## Future Plans

### [1.1.0] - Planned

- [ ] Add more quote categories
- [ ] Widget customization options
- [ ] Dark/Light theme toggle
- [ ] Quote search functionality
- [ ] Export favorites to text file
- [ ] Multiple widget sizes

### [1.2.0] - Planned

- [ ] Cloud sync for favorites
- [ ] User-submitted quotes
- [ ] Quote of the day history
- [ ] Sharing templates with custom backgrounds
- [ ] Widget themes

---

[1.1.0]: https://github.com/naufalelghazy/MotivationApp/releases/tag/v1.1.0
[1.0.0]: https://github.com/naufalelghazy/MotivationApp/releases/tag/v1.0.0
