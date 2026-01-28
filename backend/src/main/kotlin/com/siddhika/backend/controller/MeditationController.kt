package com.siddhika.backend.controller

import com.siddhika.backend.data.SeedData
import com.siddhika.backend.model.Meditation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/meditations")
class MeditationController {

    @GetMapping
    fun getAll(): List<Meditation> = SeedData.meditations

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Meditation =
        SeedData.meditations.first { it.id == id }

    @GetMapping("/category/{category}")
    fun getByCategory(@PathVariable category: String): List<Meditation> =
        SeedData.meditations.filter { it.category == category }
}
