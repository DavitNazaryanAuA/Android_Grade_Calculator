package com.aua.gradecalculator.domain

data class Gradable(
    val name: String,
    val weight: Double,
    val grade: Double = 100.0
)