package com.siddhika.data

import com.siddhika.core.util.Constants
import com.siddhika.core.util.DateTimeUtil
import com.siddhika.data.local.database.SiddhikaDatabase

private data class QuoteSeed(
    val text: String,
    val author: String,
    val source: String?,
    val category: String
)

private data class MeditationSeed(
    val title: String,
    val description: String,
    val duration: Int,
    val category: String
)

private data class PrayerSeed(
    val title: String,
    val content: String,
    val category: String
)

private data class ScriptureSeed(
    val title: String,
    val description: String,
    val content: String,
    val category: String,
    val chapters: Int
)

object SeedData {

    fun populateDatabase(database: SiddhikaDatabase) {
        val queries = database.siddhikaDatabaseQueries

        // Check if data already exists
        val existingQuotes = queries.getAllQuotes().executeAsList()
        if (existingQuotes.isNotEmpty()) return

        // Seed Quotes
        seedQuotes.forEach { quote ->
            queries.insertQuote(
                text = quote.text,
                author = quote.author,
                source = quote.source,
                category = quote.category,
                isFavorite = 0L,
                createdAt = DateTimeUtil.nowMillis()
            )
        }

        // Seed Meditations
        seedMeditations.forEach { meditation ->
            queries.insertMeditation(
                title = meditation.title,
                description = meditation.description,
                durationMinutes = meditation.duration.toLong(),
                category = meditation.category,
                imageUrl = null,
                audioUrl = null
            )
        }

        // Seed Prayers
        seedPrayers.forEach { prayer ->
            queries.insertPrayer(
                title = prayer.title,
                content = prayer.content,
                category = prayer.category,
                language = "en"
            )
        }

        // Seed Scriptures
        seedScriptures.forEach { scripture ->
            queries.insertScripture(
                title = scripture.title,
                description = scripture.description,
                content = scripture.content,
                category = scripture.category,
                language = "en",
                totalChapters = scripture.chapters.toLong()
            )
        }
    }

    private val seedQuotes = listOf(
        QuoteSeed(
            "Peace comes from within. Do not seek it without.",
            "Buddha",
            "Dhammapada",
            Constants.QuoteCategories.PEACE
        ),
        QuoteSeed(
            "The mind is everything. What you think you become.",
            "Buddha",
            "Dhammapada",
            Constants.QuoteCategories.WISDOM
        ),
        QuoteSeed(
            "In the midst of movement and chaos, keep stillness inside of you.",
            "Deepak Chopra",
            null,
            Constants.QuoteCategories.MINDFULNESS
        ),
        QuoteSeed(
            "The soul that is within me no man can degrade.",
            "Frederick Douglass",
            null,
            Constants.QuoteCategories.STRENGTH
        ),
        QuoteSeed(
            "Love is the bridge between you and everything.",
            "Rumi",
            null,
            Constants.QuoteCategories.LOVE
        ),
        QuoteSeed(
            "Gratitude turns what we have into enough.",
            "Anonymous",
            null,
            Constants.QuoteCategories.GRATITUDE
        ),
        QuoteSeed(
            "You are not a drop in the ocean. You are the entire ocean in a drop.",
            "Rumi",
            null,
            Constants.QuoteCategories.WISDOM
        ),
        QuoteSeed(
            "Be the change you wish to see in the world.",
            "Mahatma Gandhi",
            null,
            Constants.QuoteCategories.WISDOM
        ),
        QuoteSeed(
            "The present moment is filled with joy and happiness. If you are attentive, you will see it.",
            "Thich Nhat Hanh",
            null,
            Constants.QuoteCategories.MINDFULNESS
        ),
        QuoteSeed(
            "When you realize nothing is lacking, the whole world belongs to you.",
            "Lao Tzu",
            "Tao Te Ching",
            Constants.QuoteCategories.PEACE
        )
    )

    private val seedMeditations = listOf(
        MeditationSeed(
            "Breath Awareness",
            "A simple meditation focusing on the natural rhythm of your breath. Perfect for beginners.",
            5,
            Constants.MeditationCategories.BREATHING
        ),
        MeditationSeed(
            "Body Scan Relaxation",
            "Progressively relax each part of your body, releasing tension and finding peace.",
            15,
            Constants.MeditationCategories.RELAXATION
        ),
        MeditationSeed(
            "Morning Intention Setting",
            "Start your day with clarity and purpose. Set positive intentions for the day ahead.",
            10,
            Constants.MeditationCategories.MORNING
        ),
        MeditationSeed(
            "Deep Focus",
            "Enhance your concentration and mental clarity with this focused meditation.",
            20,
            Constants.MeditationCategories.FOCUS
        ),
        MeditationSeed(
            "Peaceful Sleep",
            "Prepare your mind and body for restful sleep with calming visualization.",
            15,
            Constants.MeditationCategories.SLEEP
        ),
        MeditationSeed(
            "Loving Kindness",
            "Cultivate compassion for yourself and others through this heart-opening practice.",
            10,
            Constants.MeditationCategories.GUIDED
        ),
        MeditationSeed(
            "Box Breathing",
            "A powerful breathing technique used by yogis and athletes for calm and focus.",
            5,
            Constants.MeditationCategories.BREATHING
        ),
        MeditationSeed(
            "Gratitude Meditation",
            "Connect with feelings of appreciation and thankfulness for life's blessings.",
            10,
            Constants.MeditationCategories.GUIDED
        )
    )

    private val seedPrayers = listOf(
        PrayerSeed(
            "Gayatri Mantra",
            "Om Bhur Bhuvaḥ Swaḥ\nTat Savitur Vareṇyaṃ\nBhargo Devasya Dhīmahi\nDhiyo Yo Naḥ Prachodayāt\n\nMay we meditate on the glory of the Creator who has created the Universe, who is fit to be worshipped, who is the embodiment of Knowledge and Light, who is the remover of all sins and ignorance. May He enlighten our intellect.",
            Constants.PrayerCategories.MORNING
        ),
        PrayerSeed(
            "Shanti Mantra",
            "Om Sahana Vavatu\nSahana Bhunaktu\nSaha Viryam Karavavahai\nTejasvi Navadhitamastu\nMa Vidvishavahai\nOm Shanti Shanti Shantihi\n\nMay we be protected together. May we be nourished together. May we work together with great energy. May our study be enlightening. May we not hate each other. Om Peace, Peace, Peace.",
            Constants.PrayerCategories.GENERAL
        ),
        PrayerSeed(
            "Morning Gratitude",
            "I am grateful for this new day.\nI am grateful for the breath of life.\nI am grateful for the opportunity to grow.\nI am grateful for the love that surrounds me.\nMay I use this day wisely and spread kindness wherever I go.",
            Constants.PrayerCategories.GRATITUDE
        ),
        PrayerSeed(
            "Evening Reflection",
            "As this day comes to an end,\nI release all worries and stress.\nI am grateful for the lessons learned today.\nMay I find peaceful rest,\nAnd awaken refreshed and renewed.",
            Constants.PrayerCategories.EVENING
        ),
        PrayerSeed(
            "Prayer for Healing",
            "Divine energy flows through me,\nHealing every cell of my being.\nI release all that no longer serves me.\nI embrace health, vitality, and wholeness.\nMay all beings be healed and free from suffering.",
            Constants.PrayerCategories.HEALING
        ),
        PrayerSeed(
            "Prayer for Protection",
            "I am surrounded by divine light.\nNo harm can come to me.\nI am protected, guided, and blessed.\nMay this protection extend to all those I love.\nOm Namah Shivaya.",
            Constants.PrayerCategories.PROTECTION
        )
    )

    private val seedScriptures = listOf(
        ScriptureSeed(
            "Bhagavad Gita - Chapter 2",
            "The Yoga of Knowledge - Sankhya Yoga",
            """Verse 2.47:
कर्मण्येवाधिकारस्ते मा फलेषु कदाचन।
मा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि॥

You have a right to perform your prescribed duties, but you are not entitled to the fruits of your actions. Never consider yourself to be the cause of the results of your activities, nor be attached to inaction.

Verse 2.48:
योगस्थः कुरु कर्माणि सङ्गं त्यक्त्वा धनञ्जय।
सिद्ध्यसिद्ध्योः समो भूत्वा समत्वं योग उच्यते॥

Be steadfast in the performance of your duty, O Arjuna, abandoning attachment to success and failure. Such equanimity is called Yoga.

Verse 2.62-63:
ध्यायतो विषयान्पुंसः सङ्गस्तेषूपजायते।
सङ्गात्सञ्जायते कामः कामात्क्रोधोऽभिजायते॥

While contemplating the objects of the senses, a person develops attachment for them, and from such attachment lust develops, and from lust anger arises.""",
            Constants.ScriptureCategories.BHAGAVAD_GITA,
            18
        ),
        ScriptureSeed(
            "Patanjali Yoga Sutras - Chapter 1",
            "Samadhi Pada - On Contemplation",
            """Sutra 1.1:
अथ योगानुशासनम्
Atha yoga-anushasanam
Now, the teachings of Yoga begin.

Sutra 1.2:
योगश्चित्तवृत्तिनिरोधः
Yogash chitta-vritti-nirodhah
Yoga is the cessation of the fluctuations of the mind.

Sutra 1.3:
तदा द्रष्टुः स्वरूपेऽवस्थानम्
Tada drashtuh svarupe avasthanam
Then the seer abides in their own true nature.

Sutra 1.12:
अभ्यासवैराग्याभ्यां तन्निरोधः
Abhyasa-vairagyabhyam tan-nirodhah
These mental modifications are restrained by practice and non-attachment.

Sutra 1.14:
स तु दीर्घकालनैरन्तर्यसत्कारासेवितो दृढभूमिः
Sa tu dirgha-kala-nairantarya-satkara-asevito dridha-bhumih
Practice becomes firmly grounded when it is performed for a long time, without break, and with sincere devotion.""",
            Constants.ScriptureCategories.YOGA_SUTRAS,
            4
        ),
        ScriptureSeed(
            "Isha Upanishad",
            "The Lord dwells in all",
            """Verse 1:
ईशा वास्यमिदं सर्वं यत्किञ्च जगत्यां जगत्।
तेन त्यक्तेन भुञ्जीथा मा गृधः कस्यस्विद्धनम्॥

All this—whatever exists in this changing universe—is pervaded by the Lord. Protect yourself through detachment. Do not covet anybody's wealth.

Verse 6:
यस्तु सर्वाणि भूतान्यात्मन्येवानुपश्यति।
सर्वभूतेषु चात्मानं ततो न विजुगुप्सते॥

One who sees all beings in the Self, and the Self in all beings, will never turn away from it.

Verse 17:
वायुरनिलममृतमथेदं भस्मान्तं शरीरम्।
ॐ क्रतो स्मर कृतं स्मर क्रतो स्मर कृतं स्मर॥

Let this temporary body be reduced to ashes. Om! O my mind, remember my deeds! Remember! O mind, remember my deeds! Remember!""",
            Constants.ScriptureCategories.UPANISHADS,
            1
        ),
        ScriptureSeed(
            "Mandukya Upanishad",
            "The essence of the Upanishads in 12 verses",
            """Verse 1:
ॐ इत्येतदक्षरमिदं सर्वं तस्योपव्याख्यानं भूतं भवद्भविष्यदिति सर्वमोङ्कार एव।

Om! This syllable is all this. A clear exposition of it is: all that is past, present, and future is indeed Om. And whatever is beyond the three periods of time is also Om.

Verse 2:
सर्वं ह्येतद् ब्रह्मायमात्मा ब्रह्म सोऽयमात्मा चतुष्पात्।

All this is indeed Brahman. This Self is Brahman. This same Self has four quarters.

Verse 7:
नान्तःप्रज्ञं न बहिष्प्रज्ञं नोभयतःप्रज्ञं न प्रज्ञानघनं न प्रज्ञं नाप्रज्ञम्। अदृष्टमव्यवहार्यमग्राह्यमलक्षणमचिन्त्यमव्यपदेश्यमेकात्मप्रत्ययसारं प्रपञ्चोपशमं शान्तं शिवमद्वैतं चतुर्थं मन्यन्ते स आत्मा स विज्ञेयः।

The Fourth (Turiya) is not conscious of the internal world, nor conscious of the external world, nor conscious of both worlds, nor a mass of consciousness, nor simple consciousness, nor unconsciousness. It is unseen, beyond empirical dealings, beyond the grasp of ordinary mind, uninferable, unthinkable, indescribable, the essence of the awareness of the oneness of the Self, the cessation of the world of phenomena, peaceful, blissful, and non-dual. This is the Self, and it is to be known.""",
            Constants.ScriptureCategories.UPANISHADS,
            1
        )
    )
}
