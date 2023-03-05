package com.aua.gradecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import com.aua.gradecalculator.domain.Gradable
import com.aua.gradecalculator.service.GradableService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alert("Enter assignments with weights and grades and calculate your final grade!", "")

        val gradeableService = GradableService(applicationContext)


        val assignmentView = findViewById<ListView>(R.id.assignment)
        val assignments = gradeableService.getAllGradables()

        val assignmentAdapter = GradableListAdapter(
            LayoutInflater.from(applicationContext),
            assignments,
            gradeableService
        )
        assignmentView.adapter = assignmentAdapter

        //add assignment
        val addAssignmentLayout = findViewById<RelativeLayout>(R.id.addListView)
        val assignmentAddButton = addAssignmentLayout
            .findViewById<Button>(R.id.assignmentAdd)
        assignmentAddButton.setOnClickListener {
            val newAssignmentName = addAssignmentLayout
                .findViewById<EditText>(R.id.newActivityName).text.toString()
            val newAssignmentWeight = addAssignmentLayout
                .findViewById<EditText>(R.id.newActivityWeight).text.toString()
            val newAssignmentGrade = addAssignmentLayout
                .findViewById<EditText>(R.id.newActivityGrade).text.toString()

            if (!isValidInput(
                    newAssignmentName,
                    newAssignmentWeight,
                    newAssignmentGrade,
                    assignments
                )
            ) {
                alert("Invalid Input!", "Try again")
                return@setOnClickListener
            }

            val newAssignment = Gradable(
                newAssignmentName,
                newAssignmentWeight.toDouble(),
                newAssignmentGrade.toDouble()
            )
            assignments.add(newAssignment)
            assignmentAdapter.notifyDataSetChanged()
            gradeableService.addGradable(newAssignment)

            addAssignmentLayout.findViewById<EditText>(R.id.newActivityName).setText("")
            addAssignmentLayout.findViewById<EditText>(R.id.newActivityWeight).setText("")
            addAssignmentLayout.findViewById<EditText>(R.id.newActivityGrade).setText("")
        }

        // calculate grade
        val calculateButton =
            findViewById<RelativeLayout>(R.id.calculate).findViewById<Button>(R.id.calculateButton)
        calculateButton.setOnClickListener {
            val finalGrade = assignments.sumOf { (it.grade / 100.0) * it.weight }
            alert("Your Final Grade is", finalGrade.toString())
        }
    }

    private fun isValidInput(
        name: String,
        weight: String,
        grade: String,
        assignments: List<Gradable>
    ): Boolean {
        if (name.isBlank() || weight.isBlank() || grade.isBlank()) return false
        if (assignments.any { it.name == name }) return false

        try {
            val weightAsDouble = weight.toDouble()
            val gradeAsDouble = grade.toDouble()
            val currentWeightSum = assignments.sumOf { it.weight }

            if (weightAsDouble > 100.0 - currentWeightSum ||
                weightAsDouble <= 0 ||
                gradeAsDouble < 0 ||
                gradeAsDouble > 100
            ) {
                return false
            }

        } catch (exception: RuntimeException) {
            return false
        }

        return true
    }

    private fun alert(title: String, message: String) = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .create()
        .show()
}