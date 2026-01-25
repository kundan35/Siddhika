# Siddhika - Spiritual App

A Kotlin Multiplatform spiritual app with Compose Multiplatform UI for Android, iOS, and Desktop.

## Features

### 1. Meditation & Timer
- Guided meditation sessions with countdown timers
- Categories: Breathing, Guided, Sleep, Focus, Relaxation, Morning
- Session tracking and statistics

### 2. Daily Quotes/Mantras
- Inspirational spiritual quotes
- Quote of the day
- Favorite and share quotes
- Categories: Peace, Wisdom, Love, Gratitude, Mindfulness, Strength

### 3. Prayer Reminders
- Sacred prayers collection
- Scheduled notification reminders
- Customizable repeat days
- Categories: Morning, Evening, Gratitude, Healing, Protection

### 4. Scripture/Text Reader
- Read sacred texts (Bhagavad Gita, Yoga Sutras, Upanishads)
- Bookmark functionality
- Chapter navigation

## Tech Stack

| Layer | Technology |
|-------|------------|
| **UI** | Compose Multiplatform |
| **Navigation** | Voyager |
| **DI** | Koin |
| **Database** | SQLDelight |
| **Async** | Kotlin Coroutines + Flow |
| **DateTime** | kotlinx-datetime |

## Project Structure

```
Siddhika/
├── composeApp/          # Compose Multiplatform UI
│   └── src/
│       ├── commonMain/  # Shared UI code
│       ├── androidMain/ # Android-specific UI
│       ├── iosMain/     # iOS-specific UI
│       └── desktopMain/ # Desktop-specific UI
├── shared/              # Shared business logic
│   └── src/
│       ├── commonMain/  # Domain, Data, Core
│       ├── androidMain/ # Android implementations
│       ├── iosMain/     # iOS implementations
│       └── desktopMain/ # Desktop implementations
├── androidApp/          # Android entry point
└── iosApp/              # iOS entry point (Swift)
```

## Building

### Prerequisites
- JDK 17+
- Android Studio (for Android)
- Xcode (for iOS)

### Run Desktop
```bash
./gradlew :composeApp:run
```

### Build Android APK
```bash
./gradlew :androidApp:assembleDebug
```

### Install on Android Device
```bash
./gradlew :androidApp:installDebug
```

### iOS
Open `iosApp/iosApp.xcodeproj` in Xcode and run on simulator or device.

## Color Palette

The app uses a spiritual theme with these colors:
- **Saffron Primary** - Sacred saffron (#FF9933)
- **Gold Accent** - Divine gold (#FFD700)
- **Cream Background** - Peaceful cream (#FFF8DC)
- **Deep Maroon** - Sacred maroon (#800000)
- **Sage Green** - Nature/peace (#9DC183)
- **Midnight Blue** - Meditation depth (#191970)

## License

MIT License
