package com.tempestgf.threep.domain

import java.util.UUID

data class Trip(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val startDate: String,
    val endDate: String,
    val description: String,
    val budget: Double = 0.0,
    val activities: List<Activity> = emptyList()
) {
    /**
     * Calculates remaining budget after planned activities.
     */
    fun getRemainingBudget(): Double {
        val totalActivityCost = activities.sumOf { it.cost }
        return budget - totalActivityCost
    }

    /**
     * Future feature: calculate optimization of daily spending.
     */
    fun optimizeBudgetDistribution() {
        // @TODO Implement smart budget distribution algorithm
    }
}
