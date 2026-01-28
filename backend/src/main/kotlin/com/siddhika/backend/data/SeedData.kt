package com.siddhika.backend.data

import com.siddhika.backend.model.Meditation
import com.siddhika.backend.model.Prayer
import com.siddhika.backend.model.Quote
import com.siddhika.backend.model.Scripture

object SeedData {

    val quotes = listOf(
        Quote(1, "Peace comes from within. Do not seek it without.", "Buddha", "Dhammapada", "peace"),
        Quote(2, "The mind is everything. What you think you become.", "Buddha", "Dhammapada", "wisdom"),
        Quote(3, "Love is the bridge between you and everything.", "Rumi", "Masnavi", "love"),
        Quote(4, "Gratitude turns what we have into enough.", "Aesop", null, "gratitude"),
        Quote(5, "The present moment is filled with joy and happiness. If you are attentive, you will see it.", "Thich Nhat Hanh", "Peace Is Every Step", "mindfulness"),
        Quote(6, "Strength does not come from physical capacity. It comes from an indomitable will.", "Mahatma Gandhi", null, "strength"),
        Quote(7, "In the midst of movement and chaos, keep stillness inside of you.", "Deepak Chopra", null, "peace"),
        Quote(8, "The only true wisdom is in knowing you know nothing.", "Socrates", null, "wisdom"),
        Quote(9, "Where there is love there is life.", "Mahatma Gandhi", null, "love"),
        Quote(10, "Enjoy the little things, for one day you may look back and realize they were the big things.", "Robert Brault", null, "gratitude"),
        Quote(11, "Be where you are, not where you think you should be.", "Anonymous", null, "mindfulness"),
        Quote(12, "You were given this life because you are strong enough to live it.", "Robin Sharma", null, "strength"),
        Quote(13, "Nothing can bring you peace but yourself.", "Ralph Waldo Emerson", "Self-Reliance", "peace"),
        Quote(14, "Knowing yourself is the beginning of all wisdom.", "Aristotle", null, "wisdom"),
        Quote(15, "Let us always meet each other with smile, for the smile is the beginning of love.", "Mother Teresa", null, "love")
    )

    val meditations = listOf(
        Meditation(1, "Morning Calm", "Start your day with a peaceful morning meditation to center your mind and set positive intentions.", 10, "peace"),
        Meditation(2, "Deep Breathing", "Focus on your breath to release tension and find inner calm through rhythmic breathing exercises.", 15, "peace"),
        Meditation(3, "Body Scan", "A guided journey through your body to release physical tension and promote deep relaxation.", 20, "mindfulness"),
        Meditation(4, "Loving Kindness", "Cultivate compassion for yourself and others through this heart-opening meditation practice.", 15, "love"),
        Meditation(5, "Gratitude Reflection", "Reflect on the blessings in your life and cultivate a deep sense of thankfulness.", 10, "gratitude"),
        Meditation(6, "Evening Wind Down", "Release the stress of the day and prepare for restful sleep with this calming practice.", 20, "peace"),
        Meditation(7, "Focus & Clarity", "Sharpen your mental focus and gain clarity through concentrated meditation techniques.", 15, "wisdom"),
        Meditation(8, "Inner Strength", "Build resilience and inner power through visualization and affirmation meditation.", 10, "strength")
    )

    val prayers = listOf(
        Prayer(1, "Morning Prayer", "Lord, as I begin this new day, fill my heart with Your peace and guide my steps. Grant me wisdom to make good choices, strength to face challenges, and love to share with those I meet. Let me be a light in the darkness and a source of comfort to those in need. Amen.", "peace"),
        Prayer(2, "Prayer for Wisdom", "Almighty God, I humbly seek Your wisdom. Open my mind to understanding, my heart to compassion, and my spirit to truth. Help me see beyond the surface of things and discern what is right and just. Guide my decisions today and always. Amen.", "wisdom"),
        Prayer(3, "Prayer of Gratitude", "Gracious Creator, I thank You for the gift of this day. For the breath in my lungs, the love in my heart, and the beauty that surrounds me. Help me never take these blessings for granted, and teach me to share my abundance with others. Amen.", "gratitude"),
        Prayer(4, "Prayer for Strength", "Heavenly Father, in moments of weakness I turn to You. Strengthen my resolve, renew my courage, and help me persevere through trials. Remind me that I am never alone and that Your power is made perfect in weakness. Amen.", "strength"),
        Prayer(5, "Prayer for Love", "God of love, fill my heart with compassion. Help me love others as You love me — unconditionally, patiently, and without judgment. Teach me to forgive freely and to see the divine spark in every person I encounter. Amen.", "love"),
        Prayer(6, "Evening Prayer", "Lord, as this day comes to a close, I reflect on Your goodness. Forgive me for the times I have fallen short. Thank You for the lessons learned and the blessings received. Watch over me and my loved ones through the night. Grant us peaceful rest. Amen.", "peace"),
        Prayer(7, "Prayer for Mindfulness", "Dear God, help me to be present in each moment. Quiet the noise of worry and distraction. Let me notice the sacred in the ordinary and find Your presence in the stillness. Teach me to listen more than I speak. Amen.", "mindfulness")
    )

    val scriptures = listOf(
        Scripture(1, "Bhagavad Gita - Chapter 2", "The Yoga of Knowledge", "When a man dwells on the pleasures of sense, attraction for them arises in him. From attraction grows desire, from desire anger is born. From anger comes delusion, from delusion loss of memory, from loss of memory the destruction of intelligence, and from the destruction of intelligence he perishes.\n\nBut the disciplined soul, moving among sense objects with senses under control and free from attachment and aversion, attains tranquility. In tranquility, all sorrows are destroyed, for the intellect of one who is tranquil soon becomes steady.\n\nThe soul is never born nor dies at any time. It has not come into being, does not come into being, and will not come into being. It is unborn, eternal, ever-existing, and primeval. It is not slain when the body is slain.", "hindu_scripture", "en", 18),
        Scripture(2, "Psalm 23", "The Lord is My Shepherd", "The Lord is my shepherd; I shall not want. He makes me lie down in green pastures. He leads me beside still waters. He restores my soul. He leads me in paths of righteousness for his name's sake.\n\nEven though I walk through the valley of the shadow of death, I will fear no evil, for you are with me; your rod and your staff, they comfort me.\n\nYou prepare a table before me in the presence of my enemies; you anoint my head with oil; my cup overflows. Surely goodness and mercy shall follow me all the days of my life, and I shall dwell in the house of the Lord forever.", "psalm", "en", 1),
        Scripture(3, "Heart Sutra", "The Essence of Wisdom", "Form is emptiness, emptiness is form. Emptiness is not separate from form, form is not separate from emptiness. Whatever is form is emptiness, whatever is emptiness is form.\n\nThe same is true of feelings, perceptions, mental formations, and consciousness. All phenomena bear the mark of emptiness. Their true nature is not born, not destroyed, not stained, not pure, without loss, without gain.\n\nTherefore, in emptiness there is no form, no feeling, no perception, no mental formation, no consciousness. There is no ignorance, no end of ignorance. There is no old age and death, no end of old age and death. There is no suffering, no cause of suffering, no end of suffering, no path. There is no wisdom and no attainment.", "buddhist_scripture", "en", 1),
        Scripture(4, "Surah Al-Fatiha", "The Opening", "In the name of God, the Most Gracious, the Most Merciful.\n\nPraise be to God, the Lord of all the worlds. The Most Gracious, the Most Merciful. Master of the Day of Judgment.\n\nYou alone we worship, and You alone we ask for help. Guide us on the Straight Path — the path of those who have received Your grace; not the path of those who have brought down wrath upon themselves, nor of those who have gone astray.", "islamic_scripture", "en", 1),
        Scripture(5, "Guru Granth Sahib - Japji Sahib", "Morning Prayer", "There is one God, whose name is Truth, the Creator, without fear, without hate, timeless in form, beyond birth and death, self-existent — by the grace of the Guru.\n\nChant and meditate: True in the beginning, true throughout the ages, true here and now. O Nanak, forever and ever true.\n\nBy thinking, He cannot be reduced to thought, even by thinking hundreds of thousands of times. By remaining silent, inner silence is not obtained, even by remaining lovingly absorbed deep within. The hunger of the hungry is not appeased, even by piling up loads of worldly goods. Hundreds of thousands of clever tricks, but not even one of them will go along with you in the end. So how can you become truthful? And how can the veil of illusion be torn away? O Nanak, it is written that you shall obey the Will of God.", "sikh_scripture", "en", 38)
    )
}
