package com.example.moviecatalogue.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.moviecatalogue.db.DatabaseContract.MovieColumns.Companion.MOVIE_ID
import com.example.moviecatalogue.db.DatabaseContract.MovieColumns.Companion.TABLE_MOVIE
import com.example.moviecatalogue.db.DatabaseContract.MovieColumns.Companion._ID


class MovieHelper(context: Context) {
    companion object {
        private const val DATABASE_TABLE = TABLE_MOVIE
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: MovieHelper? = null

        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): MovieHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            MovieHelper(context)
                    }
                }
            }
            return INSTANCE as MovieHelper
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
            null
        )
    }

    fun getById(id: String): Cursor {
        val query = "SELECT * FROM $TABLE_MOVIE WHERE $MOVIE_ID = $id"
        return database.rawQuery(query, null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(
            DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String):Int{
        return database.delete(
            DATABASE_TABLE, "$MOVIE_ID = '$id'", null)
    }
}