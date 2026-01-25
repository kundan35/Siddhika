package com.siddhika.core.util

object Constants {
    // App
    const val APP_NAME = "Siddhika"

    // Database
    const val DATABASE_NAME = "siddhika.db"

    // Meditation
    const val DEFAULT_MEDITATION_DURATION_MINUTES = 10
    const val MIN_MEDITATION_DURATION_MINUTES = 1
    const val MAX_MEDITATION_DURATION_MINUTES = 120

    // Timer
    const val TIMER_TICK_INTERVAL_MS = 1000L

    // Notification
    const val PRAYER_REMINDER_CHANNEL_ID = "prayer_reminders"
    const val PRAYER_REMINDER_CHANNEL_NAME = "Prayer Reminders"
    const val MEDITATION_CHANNEL_ID = "meditation"
    const val MEDITATION_CHANNEL_NAME = "Meditation"

    // Categories
    object QuoteCategories {
        const val PEACE = "peace"
        const val WISDOM = "wisdom"
        const val LOVE = "love"
        const val GRATITUDE = "gratitude"
        const val MINDFULNESS = "mindfulness"
        const val STRENGTH = "strength"

        val all = listOf(PEACE, WISDOM, LOVE, GRATITUDE, MINDFULNESS, STRENGTH)
    }

    object MeditationCategories {
        const val BREATHING = "breathing"
        const val GUIDED = "guided"
        const val SLEEP = "sleep"
        const val FOCUS = "focus"
        const val RELAXATION = "relaxation"
        const val MORNING = "morning"

        val all = listOf(BREATHING, GUIDED, SLEEP, FOCUS, RELAXATION, MORNING)
    }

    object PrayerCategories {
        const val MORNING = "morning"
        const val EVENING = "evening"
        const val GRATITUDE = "gratitude"
        const val HEALING = "healing"
        const val PROTECTION = "protection"
        const val GENERAL = "general"

        val all = listOf(MORNING, EVENING, GRATITUDE, HEALING, PROTECTION, GENERAL)
    }

    object ScriptureCategories {
        const val VEDAS = "vedas"
        const val UPANISHADS = "upanishads"
        const val BHAGAVAD_GITA = "bhagavad_gita"
        const val YOGA_SUTRAS = "yoga_sutras"
        const val OTHER = "other"

        val all = listOf(VEDAS, UPANISHADS, BHAGAVAD_GITA, YOGA_SUTRAS, OTHER)
    }
}
