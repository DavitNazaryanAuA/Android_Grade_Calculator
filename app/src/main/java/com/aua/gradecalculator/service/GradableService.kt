package com.aua.gradecalculator.service

import android.content.Context
import com.aua.gradecalculator.domain.Gradable
import com.aua.gradecalculator.repository.GradableRepository

class GradableService(
    context: Context
) {
    private val gradableRepository = GradableRepository(context)

    fun getAllGradables(): MutableList<Gradable> = gradableRepository.getAllGradeAbles()

    fun removeGradable(name: String) = gradableRepository.removeGradable(name)

    fun addGradable(gradable: Gradable) = gradableRepository.addGradable(gradable)
}