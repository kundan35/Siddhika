package com.siddhika.backend.controller

import com.siddhika.backend.data.SeedData
import com.siddhika.backend.model.Prayer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/prayers")
class PrayerController {

    @GetMapping
    fun getAll(): List<Prayer> = SeedData.prayers

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Prayer =
        SeedData.prayers.first { it.id == id }

    @GetMapping("/category/{category}")
    fun getByCategory(@PathVariable category: String): List<Prayer> =
        SeedData.prayers.filter { it.category == category }
}
