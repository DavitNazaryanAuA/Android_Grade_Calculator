package com.aua.gradecalculator.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.aua.gradecalculator.domain.Gradable

const val DB_NAME = "Gradable.db"
const val DB_VERSION = 1
const val TABLE_NAME = "gradable"
const val COLUMN_NAME = "name"
const val COLUMN_WEIGHT = "weight"
const val COLUMN_GRADE = "grade"

class GradableRepository(
    context: Context
): SQLiteOpenHelper(
    context, DB_NAME, null, DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_NAME + " VARCHAR(20) PRIMARY KEY, " +
                COLUMN_WEIGHT + " WEIGHT, " +
                COLUMN_GRADE + " GRADE);"

        println("HELLO")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addGradable(gradable: Gradable): Long {
        val values = ContentValues().apply {
            put(COLUMN_NAME, gradable.name)
            put(COLUMN_WEIGHT, gradable.weight)
            put(COLUMN_GRADE, gradable.grade)
        }
       return writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun removeGradable(name: String): Int {
        return writableDatabase.delete(TABLE_NAME, "name=?", arrayOf(name))
    }

    fun getAllGradeAbles(): MutableList<Gradable> {
        val cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val result = mutableListOf<Gradable>()
        while (cursor.moveToNext()){
            result.add(Gradable(cursor.getString(0), cursor.getString(1).toDouble(), cursor.getString(2).toDouble()))
        }
        return result
    }
}