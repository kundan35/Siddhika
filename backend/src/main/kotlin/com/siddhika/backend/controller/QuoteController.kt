package com.siddhika.backend.controller

import com.siddhika.backend.data.SeedData
import com.siddhika.backend.model.Quote
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quotes")
class QuoteController {

    @GetMapping
    fun getAll(): List<Quote> = SeedData.quotes

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Quote =
        SeedData.quotes.first { it.id == id }

    @GetMapping("/category/{category}")
    fun getByCategory(@PathVariable category: String): List<Quote> =
        SeedData.quotes.filter { it.category == category }
}
