package com.aua.gradecalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.aua.gradecalculator.domain.Gradable
import com.aua.gradecalculator.service.GradableService

class  GradableListAdapter(
    private val layoutInflater: LayoutInflater,
    private val gradables: MutableList<Gradable>,
    private val gradableService: GradableService
) : BaseAdapter() {

    override fun getCount(): Int = gradables.size

    override fun getItem(p0: Int): Any = gradables[p0]

    override fun getItemId(p0: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = layoutInflater.inflate(R.layout.assignment, null).findViewById(R.id.assignmentView)
        val assignmentNameText = view.findViewById<TextView>(R.id.assignmentName)
        val assignmentGradeText = view.findViewById<TextView>(R.id.assignmentGrade)
        val assignmentRemoveButton = view.findViewById<Button>(R.id.assignmentRemove)

        assignmentNameText.text = gradables[position].name
        assignmentGradeText.text = gradables[position].grade.toString()

        assignmentRemoveButton.setOnClickListener {
            gradables.removeIf {it.name == assignmentNameText.text }
            notifyDataSetChanged()
            gradableService.removeGradable(assignmentNameText.text.toString())
        }
        return view
    }
}