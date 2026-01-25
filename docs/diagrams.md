# Siddhika - Visual Diagrams

## High-Level Architecture

```mermaid
graph TB
    subgraph Platform["Platform Layer"]
        Android[Android App]
        iOS[iOS App]
        Desktop[Desktop App]
    end

    subgraph UI["Compose Multiplatform UI"]
        subgraph Screens
            Home[Home Screen]
            Meditation[Meditation Screen]
            Quotes[Quotes Screen]
            Prayers[Prayers Screen]
            Scripture[Scripture Screen]
        end

        subgraph ViewModels
            HomeVM[HomeViewModel]
            MedVM[MeditationViewModel]
            QuoteVM[QuotesViewModel]
            PrayerVM[PrayerViewModel]
            ScriptVM[ScriptureViewModel]
        end

        Components[UI Components]
        Theme[Theme & Styling]
    end

    subgraph Shared["Shared Module"]
        subgraph Domain["Domain Layer"]
            Models[Domain Models]
            RepoInterfaces[Repository Interfaces]
            UseCases[Use Cases]
        end

        subgraph Data["Data Layer"]
            RepoImpl[Repository Implementations]
            Mappers[Entity Mappers]
            LocalDB[(SQLDelight Database)]
        end

        subgraph Core["Core"]
            DI[Koin DI]
            Utils[Utilities]
        end
    end

    Android --> UI
    iOS --> UI
    Desktop --> UI

    Screens --> ViewModels
    ViewModels --> UseCases
    UseCases --> RepoInterfaces
    RepoInterfaces -.-> RepoImpl
    RepoImpl --> Mappers
    Mappers --> LocalDB

    Components --> Theme
    DI --> RepoImpl
    DI --> UseCases
    DI --> ViewModels

    style Platform fill:#e1f5fe
    style UI fill:#fff3e0
    style Domain fill:#e8f5e9
    style Data fill:#fce4ec
    style Core fill:#f3e5f5
```

## Data Flow

```mermaid
sequenceDiagram
    participant U as User
    participant S as Screen
    participant VM as ViewModel
    participant UC as UseCase
    participant R as Repository
    participant DB as SQLDelight

    U->>S: User Action
    S->>VM: Trigger Event
    VM->>UC: Execute Use Case
    UC->>R: Request Data
    R->>DB: Query Database
    DB-->>R: Return Entities
    R-->>UC: Return Domain Models
    UC-->>VM: Return Result
    VM-->>S: Update UI State
    S-->>U: Display Changes
```

## Module Dependencies

```mermaid
graph LR
    subgraph Apps
        A[androidApp]
        I[iosApp]
    end

    C[composeApp]
    S[shared]

    A --> C
    A --> S
    I --> C
    I --> S
    C --> S

    style A fill:#a5d6a7
    style I fill:#90caf9
    style C fill:#ffcc80
    style S fill:#ce93d8
```

## Feature Module Structure

```mermaid
graph TB
    subgraph Meditation["Meditation Feature"]
        MS[MeditationScreen]
        MTS[MeditationTimerScreen]
        MVM[MeditationViewModel]
        MTVM[MeditationTimerViewModel]

        GMU[GetMeditationsUseCase]
        SMU[SaveMeditationSessionUseCase]
        GSU[GetMeditationStatsUseCase]

        MR[MeditationRepository]
        MRI[MeditationRepositoryImpl]

        ME[(MeditationEntity)]
        MSE[(MeditationSessionEntity)]
    end

    MS --> MVM
    MTS --> MTVM
    MVM --> GMU
    MTVM --> SMU
    MTVM --> GSU

    GMU --> MR
    SMU --> MR
    GSU --> MR

    MR -.-> MRI
    MRI --> ME
    MRI --> MSE

    style MS fill:#bbdefb
    style MTS fill:#bbdefb
    style MVM fill:#c8e6c9
    style MTVM fill:#c8e6c9
    style GMU fill:#fff9c4
    style SMU fill:#fff9c4
    style GSU fill:#fff9c4
    style MRI fill:#ffccbc
```

## Database Entity Relationships

```mermaid
erDiagram
    QuoteEntity {
        int id PK
        string text
        string author
        string source
        string category
        boolean isFavorite
        long createdAt
    }

    MeditationEntity {
        int id PK
        string title
        string description
        int durationMinutes
        string category
        string imageUrl
        string audioUrl
    }

    MeditationSessionEntity {
        int id PK
        int meditationId FK
        long completedAt
        int durationSeconds
    }

    PrayerEntity {
        int id PK
        string title
        string content
        string category
        string language
    }

    PrayerReminderEntity {
        int id PK
        int prayerId FK
        string title
        string time
        string daysOfWeek
        boolean isEnabled
    }

    ScriptureEntity {
        int id PK
        string title
        string description
        string content
        string category
        string language
        int totalChapters
    }

    BookmarkEntity {
        int id PK
        int scriptureId FK
        int chapterNumber
        int verseNumber
        string note
        long createdAt
    }

    MeditationEntity ||--o{ MeditationSessionEntity : has
    PrayerEntity ||--o{ PrayerReminderEntity : has
    ScriptureEntity ||--o{ BookmarkEntity : has
```

## Dependency Injection Graph

```mermaid
graph TB
    subgraph PlatformModule["Platform Module"]
        DDF[DatabaseDriverFactory]
        SDB[(SiddhikaDatabase)]
    end

    subgraph SharedModule["Shared Module"]
        QRI[QuoteRepositoryImpl]
        MRI[MeditationRepositoryImpl]
        PRI[PrayerRepositoryImpl]
        SRI[ScriptureRepositoryImpl]

        GDQU[GetDailyQuoteUseCase]
        GAQU[GetAllQuotesUseCase]
        GMU[GetMeditationsUseCase]
        GPU[GetPrayersUseCase]
        GSU[GetScripturesUseCase]
    end

    subgraph AppModule["App Module"]
        HVM[HomeViewModel]
        MeVM[MeditationViewModel]
        QVM[QuotesViewModel]
        PVM[PrayerViewModel]
        ScVM[ScriptureViewModel]
    end

    DDF --> SDB
    SDB --> QRI
    SDB --> MRI
    SDB --> PRI
    SDB --> SRI

    QRI --> GDQU
    QRI --> GAQU
    MRI --> GMU
    PRI --> GPU
    SRI --> GSU

    GDQU --> HVM
    GAQU --> QVM
    GMU --> MeVM
    GPU --> PVM
    GSU --> ScVM

    style PlatformModule fill:#e3f2fd
    style SharedModule fill:#e8f5e9
    style AppModule fill:#fff3e0
```

## Navigation Flow

```mermaid
stateDiagram-v2
    [*] --> Home

    Home --> MeditationList: Meditation Card
    Home --> QuotesList: Quotes Card
    Home --> PrayerList: Prayers Card
    Home --> ScriptureList: Scripture Card

    MeditationList --> MeditationTimer: Select Meditation
    MeditationTimer --> MeditationList: Complete/Back

    QuotesList --> Home: Back

    PrayerList --> PrayerDetail: Select Prayer
    PrayerList --> AddReminder: Add Reminder
    PrayerDetail --> PrayerList: Back
    AddReminder --> PrayerList: Save/Cancel

    ScriptureList --> ScriptureReader: Select Scripture
    ScriptureList --> Bookmarks: View Bookmarks
    ScriptureReader --> ScriptureList: Back
    Bookmarks --> ScriptureReader: Select Bookmark
```

## Technology Stack

```mermaid
mindmap
    root((Siddhika))
        UI Layer
            Compose Multiplatform
            Material 3
            Voyager Navigation
        Domain Layer
            Use Cases
            Repository Interfaces
            Domain Models
        Data Layer
            SQLDelight
            DataStore
            Ktor Client
        Core
            Kotlin Coroutines
            Kotlin Flow
            kotlinx-datetime
            kotlinx-serialization
        DI
            Koin
        Platforms
            Android
            iOS
            Desktop
```
