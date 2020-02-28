package com.example.moviecatalogue.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.moviecatalogue.db.DatabaseContract.MovieColumns.Companion._ID
import com.example.moviecatalogue.db.DatabaseContract.TvColumns.Companion.TABLE_TV
import com.example.moviecatalogue.db.DatabaseContract.TvColumns.Companion.TV_ID


class TvHelper (context: Context){
    companion object {
        private const val DATABASE_TABLE = TABLE_TV
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: TvHelper? = null

        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): TvHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            TvHelper(context)
                    }
                }
            }
            return INSTANCE as TvHelper
        }
    }

    init {
        databaseHelper = DatabaseHelper(context)
        open()
    }

    @Throws(SQLiteException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun getAll(): Cursor {
        database = databaseHelper.writableDatabase
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun getById(id: String): Cursor {
        val query = "SELECT * FROM $TABLE_TV WHERE $TV_ID = $id"
        return database.rawQuery(query, null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(
            DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String):Int{
        return database.delete(
            DATABASE_TABLE, "$TV_ID = '$id'", null)
    }
}