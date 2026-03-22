package com.tempestgf.threep.domain

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class Activity(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val date: LocalDate,
    val time: LocalTime,
    val cost: Double = 0.0
)
