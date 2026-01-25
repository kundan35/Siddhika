# Siddhika - Architecture Documentation

## Overview

Siddhika is a cross-platform spiritual wellness application built using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. The architecture follows **Clean Architecture** principles with clear separation of concerns across Presentation, Domain, and Data layers.

---

## System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              PLATFORM LAYER                                  │
├─────────────────────┬─────────────────────┬─────────────────────────────────┤
│      Android        │        iOS          │           Desktop               │
│   ┌───────────┐     │   ┌───────────┐     │      ┌───────────┐              │
│   │ MainActivity│    │   │  iOSApp   │     │      │  Main.kt  │              │
│   │ Application │    │   │  Swift    │     │      │  Window   │              │
│   └───────────┘     │   └───────────┘     │      └───────────┘              │
└─────────────────────┴─────────────────────┴─────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                         COMPOSE MULTIPLATFORM UI                             │
│                              (composeApp)                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │    Home     │  │ Meditation  │  │   Quotes    │  │  Prayers    │        │
│  │   Screen    │  │   Screen    │  │   Screen    │  │   Screen    │        │
│  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘        │
│         │                │                │                │                │
│  ┌──────▼──────┐  ┌──────▼──────┐  ┌──────▼──────┐  ┌──────▼──────┐        │
│  │    Home     │  │ Meditation  │  │   Quotes    │  │   Prayer    │        │
│  │  ViewModel  │  │  ViewModel  │  │  ViewModel  │  │  ViewModel  │        │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘        │
│                                                                             │
│  ┌──────────────────────────────────────────────────────────────────┐      │
│  │                      UI Components                                │      │
│  │  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐            │      │
│  │  │QuoteCard │ │TimerCircle│ │FeatureCard│ │SiddhikaBtn│           │      │
│  │  └──────────┘ └──────────┘ └──────────┘ └──────────┘            │      │
│  └──────────────────────────────────────────────────────────────────┘      │
│                                                                             │
│  ┌──────────────────────────────────────────────────────────────────┐      │
│  │                         Theme                                     │      │
│  │              Colors  •  Typography  •  Shapes                     │      │
│  └──────────────────────────────────────────────────────────────────┘      │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                           SHARED MODULE                                      │
│                             (shared)                                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────┐     │
│  │                        DOMAIN LAYER                                │     │
│  │  ┌─────────────────────────────────────────────────────────────┐  │     │
│  │  │                       Use Cases                              │  │     │
│  │  │  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌──────────┐  │  │     │
│  │  │  │GetDaily    │ │GetMedita-  │ │SetPrayer   │ │Toggle    │  │  │     │
│  │  │  │QuoteUseCase│ │tionsUseCase│ │ReminderUC  │ │BookmarkUC│  │  │     │
│  │  │  └────────────┘ └────────────┘ └────────────┘ └──────────┘  │  │     │
│  │  └─────────────────────────────────────────────────────────────┘  │     │
│  │  ┌─────────────────────────────────────────────────────────────┐  │     │
│  │  │                    Repository Interfaces                     │  │     │
│  │  │  QuoteRepository • MeditationRepository • PrayerRepository   │  │     │
│  │  │                    ScriptureRepository                       │  │     │
│  │  └─────────────────────────────────────────────────────────────┘  │     │
│  │  ┌─────────────────────────────────────────────────────────────┐  │     │
│  │  │                      Domain Models                           │  │     │
│  │  │  Quote • Meditation • Prayer • Scripture • Bookmark          │  │     │
│  │  └─────────────────────────────────────────────────────────────┘  │     │
│  └───────────────────────────────────────────────────────────────────┘     │
│                                    │                                        │
│                                    ▼                                        │
│  ┌───────────────────────────────────────────────────────────────────┐     │
│  │                         DATA LAYER                                 │     │
│  │  ┌─────────────────────────────────────────────────────────────┐  │     │
│  │  │                 Repository Implementations                   │  │     │
│  │  │  QuoteRepositoryImpl • MeditationRepositoryImpl              │  │     │
│  │  │  PrayerRepositoryImpl • ScriptureRepositoryImpl              │  │     │
│  │  └─────────────────────────────────────────────────────────────┘  │     │
│  │  ┌─────────────────────────────────────────────────────────────┐  │     │
│  │  │                      Entity Mappers                          │  │     │
│  │  │            Entity ←→ Domain Model Transformations            │  │     │
│  │  └─────────────────────────────────────────────────────────────┘  │     │
│  │  ┌─────────────────────────────────────────────────────────────┐  │     │
│  │  │                    Local Data Source                         │  │     │
│  │  │  ┌─────────────────────────────────────────────────────┐    │  │     │
│  │  │  │              SQLDelight Database                     │    │  │     │
│  │  │  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐   │    │  │     │
│  │  │  │  │ Quotes  │ │Medita-  │ │ Prayers │ │Scripture│   │    │  │     │
│  │  │  │  │  Table  │ │tions    │ │  Table  │ │  Table  │   │    │  │     │
│  │  │  │  └─────────┘ └─────────┘ └─────────┘ └─────────┘   │    │  │     │
│  │  │  └─────────────────────────────────────────────────────┘    │  │     │
│  │  └─────────────────────────────────────────────────────────────┘  │     │
│  └───────────────────────────────────────────────────────────────────┘     │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────┐     │
│  │                         CORE LAYER                                 │     │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────────┐   │     │
│  │  │   Koin DI   │  │  Utilities  │  │      Constants          │   │     │
│  │  │   Modules   │  │ DateTimeUtil│  │  Categories, Settings   │   │     │
│  │  └─────────────┘  └─────────────┘  └─────────────────────────┘   │     │
│  └───────────────────────────────────────────────────────────────────┘     │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                      PLATFORM-SPECIFIC IMPLEMENTATIONS                       │
├─────────────────────┬─────────────────────┬─────────────────────────────────┤
│      androidMain    │       iosMain       │          desktopMain            │
│  ┌───────────────┐  │  ┌───────────────┐  │     ┌───────────────┐           │
│  │AndroidSqlite  │  │  │ NativeSqlite  │  │     │  JdbcSqlite   │           │
│  │    Driver     │  │  │    Driver     │  │     │    Driver     │           │
│  └───────────────┘  │  └───────────────┘  │     └───────────────┘           │
│  ┌───────────────┐  │  ┌───────────────┐  │     ┌───────────────┐           │
│  │  Platform DI  │  │  │  Platform DI  │  │     │  Platform DI  │           │
│  │    Module     │  │  │    Module     │  │     │    Module     │           │
│  └───────────────┘  │  └───────────────┘  │     └───────────────┘           │
└─────────────────────┴─────────────────────┴─────────────────────────────────┘
```

---

## Technology Stack

### Core Technologies

| Category | Technology | Version | Purpose |
|----------|------------|---------|---------|
| **Language** | Kotlin | 2.0.21 | Primary development language |
| **UI Framework** | Compose Multiplatform | 1.7.0 | Cross-platform UI toolkit |
| **Build System** | Gradle | 8.5 | Build automation |
| **Min Android SDK** | 24 | - | Android 7.0+ support |

### Architecture & Patterns

| Category | Technology | Purpose |
|----------|------------|---------|
| **Architecture** | Clean Architecture | Separation of concerns |
| **UI Pattern** | MVVM | Reactive UI updates |
| **Dependency Injection** | Koin 3.5.6 | Service locator & DI |
| **Navigation** | Voyager 1.0.0 | Type-safe multiplatform navigation |

### Data & Storage

| Category | Technology | Purpose |
|----------|------------|---------|
| **Database** | SQLDelight 2.0.2 | Type-safe SQL with multiplatform support |
| **Preferences** | DataStore 1.1.1 | Key-value storage |
| **Serialization** | kotlinx-serialization 1.7.3 | JSON parsing |

### Async & Reactive

| Category | Technology | Purpose |
|----------|------------|---------|
| **Coroutines** | kotlinx-coroutines 1.8.1 | Asynchronous programming |
| **Flow** | Kotlin Flow | Reactive data streams |
| **DateTime** | kotlinx-datetime 0.6.1 | Cross-platform date/time |

### Networking (Future)

| Category | Technology | Purpose |
|----------|------------|---------|
| **HTTP Client** | Ktor 2.3.12 | Multiplatform networking |

---

## Module Structure

```
Siddhika/
│
├── shared/                          # Kotlin Multiplatform Shared Code
│   ├── commonMain/                  # Shared business logic
│   │   ├── domain/                  # Domain layer
│   │   │   ├── model/              # Business entities
│   │   │   ├── repository/         # Repository interfaces
│   │   │   └── usecase/            # Business logic
│   │   ├── data/                    # Data layer
│   │   │   ├── local/database/     # SQLDelight schemas
│   │   │   ├── repository/         # Repository implementations
│   │   │   └── mapper/             # Entity mappers
│   │   └── core/                    # Core utilities
│   │       ├── di/                 # Koin modules
│   │       └── util/               # Helpers
│   ├── androidMain/                 # Android implementations
│   ├── iosMain/                     # iOS implementations
│   └── desktopMain/                 # Desktop implementations
│
├── composeApp/                      # Compose Multiplatform UI
│   ├── commonMain/                  # Shared UI
│   │   ├── ui/
│   │   │   ├── theme/              # Material 3 theming
│   │   │   ├── components/         # Reusable composables
│   │   │   └── screens/            # Feature screens
│   │   ├── navigation/             # Voyager navigation
│   │   └── di/                     # UI DI module
│   ├── androidMain/                 # Android UI specifics
│   ├── iosMain/                     # iOS UI specifics
│   └── desktopMain/                 # Desktop UI specifics
│
├── androidApp/                      # Android Application
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── kotlin/.../
│           ├── MainActivity.kt
│           ├── SiddhikaApplication.kt
│           └── notification/        # Android notifications
│
└── iosApp/                          # iOS Application (Swift)
    └── iosApp/
        ├── iOSApp.swift
        └── ContentView.swift
```

---

## Data Flow

```
┌──────────────────────────────────────────────────────────────────────────┐
│                              USER INTERACTION                             │
└─────────────────────────────────────┬────────────────────────────────────┘
                                      │
                                      ▼
┌──────────────────────────────────────────────────────────────────────────┐
│                               UI LAYER                                    │
│  ┌────────────────┐         ┌────────────────┐         ┌──────────────┐ │
│  │    Screen      │ ──────► │   ViewModel    │ ◄────── │   UI State   │ │
│  │  (Composable)  │         │  (ScreenModel) │         │    (Flow)    │ │
│  └────────────────┘         └───────┬────────┘         └──────────────┘ │
└─────────────────────────────────────┼────────────────────────────────────┘
                                      │
                                      ▼
┌──────────────────────────────────────────────────────────────────────────┐
│                             DOMAIN LAYER                                  │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                           Use Cases                                 │ │
│  │   • Execute single business operations                              │ │
│  │   • Coordinate data from repositories                               │ │
│  │   • Return domain models wrapped in Result                          │ │
│  └───────────────────────────────┬────────────────────────────────────┘ │
│                                  │                                       │
│  ┌───────────────────────────────▼────────────────────────────────────┐ │
│  │                     Repository Interfaces                           │ │
│  │   • Define data access contracts                                    │ │
│  │   • Platform-agnostic                                               │ │
│  └────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────┬────────────────────────────────────┘
                                      │
                                      ▼
┌──────────────────────────────────────────────────────────────────────────┐
│                              DATA LAYER                                   │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │                   Repository Implementations                        │ │
│  │   • Implement repository interfaces                                 │ │
│  │   • Coordinate data sources                                         │ │
│  │   • Map entities to domain models                                   │ │
│  └───────────────────────────────┬────────────────────────────────────┘ │
│                                  │                                       │
│  ┌───────────────────────────────▼────────────────────────────────────┐ │
│  │                       Local Data Source                             │ │
│  │   ┌──────────────────────────────────────────────────────────┐     │ │
│  │   │                  SQLDelight Database                      │     │ │
│  │   │   • Type-safe SQL queries                                 │     │ │
│  │   │   • Reactive Flow-based queries                           │     │ │
│  │   │   • Platform-specific drivers                             │     │ │
│  │   └──────────────────────────────────────────────────────────┘     │ │
│  └────────────────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────────────────┘
```

---

## Feature Architecture

### Meditation Timer Feature

```
┌─────────────────────────────────────────────────────────────────┐
│                    MEDITATION FEATURE                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  UI Layer                                                        │
│  ┌─────────────────┐    ┌─────────────────────────────────────┐ │
│  │MeditationList   │───►│ MeditationViewModel                 │ │
│  │    Screen       │    │  • meditations: StateFlow<List>     │ │
│  └─────────────────┘    │  • loadMeditations()                │ │
│          │              └─────────────────────────────────────┘ │
│          ▼                                                       │
│  ┌─────────────────┐    ┌─────────────────────────────────────┐ │
│  │MeditationTimer  │───►│ MeditationTimerViewModel            │ │
│  │    Screen       │    │  • remainingSeconds: StateFlow      │ │
│  └─────────────────┘    │  • timerState: StateFlow            │ │
│          │              │  • startTimer() / pauseTimer()      │ │
│          │              └─────────────────────────────────────┘ │
│          ▼                            │                          │
│  ┌─────────────────┐                  │                          │
│  │  TimerCircle    │                  │                          │
│  │   Component     │                  │                          │
│  └─────────────────┘                  │                          │
│                                       ▼                          │
│  Domain Layer                                                    │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │ GetMeditationsUseCase • SaveMeditationSessionUseCase       │ │
│  │ GetMeditationStatsUseCase                                  │ │
│  └────────────────────────────────────────────────────────────┘ │
│                              │                                   │
│                              ▼                                   │
│  Data Layer                                                      │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │ MeditationRepositoryImpl                                   │ │
│  │  └──► SQLDelight: MeditationEntity, MeditationSessionEntity│ │
│  └────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

---

## Dependency Injection Graph

```
┌─────────────────────────────────────────────────────────────────────────┐
│                          KOIN DI CONTAINER                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ┌─────────────────────────────────────────────────────────────────┐   │
│  │                     Platform Module                              │   │
│  │  ┌─────────────────────────────────────────────────────────┐    │   │
│  │  │ DatabaseDriverFactory (platform-specific)                │    │   │
│  │  │ SiddhikaDatabase (singleton)                            │    │   │
│  │  └─────────────────────────────────────────────────────────┘    │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                              │                                          │
│                              ▼                                          │
│  ┌─────────────────────────────────────────────────────────────────┐   │
│  │                      Shared Module                               │   │
│  │  ┌─────────────────────────────────────────────────────────┐    │   │
│  │  │ Repositories (singleton)                                 │    │   │
│  │  │  • QuoteRepositoryImpl                                   │    │   │
│  │  │  • MeditationRepositoryImpl                              │    │   │
│  │  │  • PrayerRepositoryImpl                                  │    │   │
│  │  │  • ScriptureRepositoryImpl                               │    │   │
│  │  └─────────────────────────────────────────────────────────┘    │   │
│  │  ┌─────────────────────────────────────────────────────────┐    │   │
│  │  │ Use Cases (factory)                                      │    │   │
│  │  │  • GetDailyQuoteUseCase, GetAllQuotesUseCase            │    │   │
│  │  │  • GetMeditationsUseCase, SaveMeditationSessionUseCase  │    │   │
│  │  │  • GetPrayersUseCase, SetPrayerReminderUseCase          │    │   │
│  │  │  • GetScripturesUseCase, ToggleBookmarkUseCase          │    │   │
│  │  └─────────────────────────────────────────────────────────┘    │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                              │                                          │
│                              ▼                                          │
│  ┌─────────────────────────────────────────────────────────────────┐   │
│  │                       App Module                                 │   │
│  │  ┌─────────────────────────────────────────────────────────┐    │   │
│  │  │ ViewModels / ScreenModels (factory)                      │    │   │
│  │  │  • HomeViewModel                                         │    │   │
│  │  │  • MeditationViewModel, MeditationTimerViewModel        │    │   │
│  │  │  • QuotesViewModel                                       │    │   │
│  │  │  • PrayerViewModel, AddReminderViewModel                │    │   │
│  │  │  • ScriptureViewModel, ScriptureReaderViewModel         │    │   │
│  │  └─────────────────────────────────────────────────────────┘    │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Database Schema

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        SQLDELIGHT DATABASE                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ┌─────────────────┐       ┌─────────────────┐                          │
│  │   QuoteEntity   │       │ MeditationEntity│                          │
│  ├─────────────────┤       ├─────────────────┤                          │
│  │ id (PK)         │       │ id (PK)         │                          │
│  │ text            │       │ title           │                          │
│  │ author          │       │ description     │                          │
│  │ source          │       │ durationMinutes │                          │
│  │ category        │       │ category        │                          │
│  │ isFavorite      │       │ imageUrl        │                          │
│  │ createdAt       │       │ audioUrl        │                          │
│  └─────────────────┘       └────────┬────────┘                          │
│                                     │                                    │
│                                     │ 1:N                                │
│                                     ▼                                    │
│                            ┌─────────────────────┐                       │
│                            │MeditationSession    │                       │
│                            │      Entity         │                       │
│                            ├─────────────────────┤                       │
│                            │ id (PK)             │                       │
│                            │ meditationId (FK)   │                       │
│                            │ completedAt         │                       │
│                            │ durationSeconds     │                       │
│                            └─────────────────────┘                       │
│                                                                          │
│  ┌─────────────────┐       ┌─────────────────────┐                      │
│  │  PrayerEntity   │       │PrayerReminderEntity │                      │
│  ├─────────────────┤       ├─────────────────────┤                      │
│  │ id (PK)         │◄──────│ id (PK)             │                      │
│  │ title           │  1:N  │ prayerId (FK)       │                      │
│  │ content         │       │ title               │                      │
│  │ category        │       │ time                │                      │
│  │ language        │       │ daysOfWeek          │                      │
│  └─────────────────┘       │ isEnabled           │                      │
│                            └─────────────────────┘                       │
│                                                                          │
│  ┌─────────────────┐       ┌─────────────────┐                          │
│  │ ScriptureEntity │       │ BookmarkEntity  │                          │
│  ├─────────────────┤       ├─────────────────┤                          │
│  │ id (PK)         │◄──────│ id (PK)         │                          │
│  │ title           │  1:N  │ scriptureId (FK)│                          │
│  │ description     │       │ chapterNumber   │                          │
│  │ content         │       │ verseNumber     │                          │
│  │ category        │       │ note            │                          │
│  │ language        │       │ createdAt       │                          │
│  │ totalChapters   │       └─────────────────┘                          │
│  └─────────────────┘                                                     │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Design Patterns Used

| Pattern | Implementation | Purpose |
|---------|---------------|---------|
| **Repository** | `*RepositoryImpl` classes | Abstract data sources |
| **Use Case** | `*UseCase` classes | Single responsibility business logic |
| **MVVM** | ViewModel + StateFlow | Reactive UI state management |
| **Factory** | Koin factory | Create new instances per request |
| **Singleton** | Koin single | Share single instance (DB, Repos) |
| **Mapper** | `EntityMapper.kt` | Transform between layers |
| **Observer** | Kotlin Flow | Reactive data streams |

---

## Color System

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         SPIRITUAL COLOR PALETTE                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  Primary Colors                                                          │
│  ┌─────────────────────────────────────────────────────────────────┐   │
│  │  ████████  Saffron Primary    #FF9933   Sacred saffron          │   │
│  │  ████████  Saffron Light      #FFB366   Lighter variant         │   │
│  │  ████████  Saffron Dark       #CC7A29   Darker variant          │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                          │
│  Secondary Colors                                                        │
│  ┌─────────────────────────────────────────────────────────────────┐   │
│  │  ████████  Sage Green         #9DC183   Nature/peace            │   │
│  │  ████████  Gold Accent        #FFD700   Divine gold             │   │
│  │  ████████  Deep Maroon        #800000   Sacred maroon           │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                          │
│  Background Colors                                                       │
│  ┌─────────────────────────────────────────────────────────────────┐   │
│  │  ████████  Cream Background   #FFF8DC   Peaceful cream          │   │
│  │  ████████  Midnight Blue      #191970   Meditation depth        │   │
│  │  ████████  Surface Dark       #1E1E2E   Dark mode surface       │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Build & Deployment

```
┌─────────────────────────────────────────────────────────────────────────┐
│                          BUILD PIPELINE                                  │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  Source Code                                                             │
│       │                                                                  │
│       ▼                                                                  │
│  ┌─────────────────────────────────────────────────────────────────┐   │
│  │                      Gradle Build                                │   │
│  │  ./gradlew build                                                 │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│       │                                                                  │
│       ├──────────────────┬──────────────────┬──────────────────┐        │
│       ▼                  ▼                  ▼                  ▼        │
│  ┌─────────┐       ┌─────────┐       ┌─────────┐       ┌─────────┐    │
│  │ Android │       │   iOS   │       │ Desktop │       │  Tests  │    │
│  │   APK   │       │Framework│       │   JAR   │       │         │    │
│  └────┬────┘       └────┬────┘       └────┬────┘       └─────────┘    │
│       │                 │                 │                             │
│       ▼                 ▼                 ▼                             │
│  ┌─────────┐       ┌─────────┐       ┌─────────┐                       │
│  │ Google  │       │  Apple  │       │  DMG/   │                       │
│  │  Play   │       │  Store  │       │  EXE    │                       │
│  └─────────┘       └─────────┘       └─────────┘                       │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Version Information

| Component | Version |
|-----------|---------|
| Kotlin | 2.0.21 |
| Compose Multiplatform | 1.7.0 |
| Gradle | 8.5 |
| Android Gradle Plugin | 8.2.2 |
| Min SDK (Android) | 24 |
| Target SDK (Android) | 34 |
| iOS Deployment Target | 14.0 |

---

*Document Version: 1.0.0*
*Last Updated: January 2026*
