package com.siddhika.backend.controller

import com.siddhika.backend.data.SeedData
import com.siddhika.backend.model.Scripture
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/scriptures")
class ScriptureController {

    @GetMapping
    fun getAll(): List<Scripture> = SeedData.scriptures

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Scripture =
        SeedData.scriptures.first { it.id == id }

    @GetMapping("/category/{category}")
    fun getByCategory(@PathVariable category: String): List<Scripture> =
        SeedData.scriptures.filter { it.category == category }
}
