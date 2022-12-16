package com.example.unitconverter.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "HistoryDatabase"
        private const val TABLE_NAME = "ProcessHistory"
        private const val KEY_ID = "id"
        private const val KEY_VALUE = "value"
        private const val KEY_FROM = "from_unit"
        private const val KEY_TO = "to_unit"
        private const val KEY_RESULT = "result"
        private const val KEY_DATE = "date"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable =
            ("CREATE TABLE $TABLE_NAME($KEY_ID INTEGER PRIMARY KEY,$KEY_VALUE REAL,'$KEY_FROM' TEXT,'$KEY_TO' TEXT,$KEY_RESULT TEXT,$KEY_DATE TEXT)")
        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }

    fun addData(model: DataModel): Long {

        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_VALUE, model.value)
        contentValues.put(KEY_FROM, model.from)
        contentValues.put(KEY_TO, model.to)
        contentValues.put(KEY_RESULT, model.result)
        contentValues.put(KEY_DATE, model.date)

        // Satır ekleme
        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()

        return success
    }

    @SuppressLint("Range")
    fun readTable(): ArrayList<DataModel> {
        val modelList: ArrayList<DataModel> = ArrayList()

        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var value: Double
        var from: String
        var to: String
        var result: Double
        var date: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                value = cursor.getDouble(cursor.getColumnIndex(KEY_VALUE))
                from = cursor.getString(cursor.getColumnIndex(KEY_FROM))
                to = cursor.getString(cursor.getColumnIndex(KEY_TO))
                result = cursor.getDouble(cursor.getColumnIndex(KEY_RESULT))
                date = cursor.getString(cursor.getColumnIndex(KEY_DATE))

                val info = DataModel(
                    id = id,
                    value = value,
                    from = from,
                    to = to,
                    result = result,
                    date = date,
                )
                modelList.add(info)

            } while (cursor.moveToNext())
        }
        return modelList
    }

    fun deleteItem(id: Int): Boolean {
        val db = this.writableDatabase
        val deleteQuery = "DELETE FROM $TABLE_NAME WHERE $KEY_ID = $id"
        try {
            db.execSQL(deleteQuery)
        } catch (e: SQLiteException) {
            return false
        }
        return true
    }

    fun deleteTable() {
        val db = this.writableDatabase
        db.execSQL(
            "DELETE FROM $TABLE_NAME"
        )
        db.close()
    }

}