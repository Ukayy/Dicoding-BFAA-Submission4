package com.example.moviecatalogue.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moviecatalogue.db.DatabaseContract
import com.example.moviecatalogue.db.DatabaseContract.MovieColumns.Companion.TABLE_MOVIE
import com.example.moviecatalogue.db.DatabaseContract.TvColumns.Companion.TABLE_TV


internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "momvie"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE $TABLE_MOVIE" +
                " (${DatabaseContract.MovieColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseContract.MovieColumns.MOVIE_ID} TEXT NOT NULL, " +
                "${DatabaseContract.MovieColumns.TITLE} TEXT NOT NULL, " +
                "${DatabaseContract.MovieColumns.DATE} TEXT, " +
                "${DatabaseContract.MovieColumns.OVERVIEW} TEXT, " +
                "${DatabaseContract.MovieColumns.SCORE} TEXT," +
                "${DatabaseContract.MovieColumns.POSTER} TEXT)"

        private const val SQL_CREATE_TABLE_TV = "CREATE TABLE $TABLE_TV" +
                " (${DatabaseContract.TvColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseContract.TvColumns.TV_ID} TEXT NOT NULL, " +
                "${DatabaseContract.TvColumns.TITLE} TEXT NOT NULL, " +
                "${DatabaseContract.TvColumns.DATE} TEXT, " +
                "${DatabaseContract.TvColumns.OVERVIEW} TEXT, " +
                "${DatabaseContract.TvColumns.SCORE} TEXT," +
                "${DatabaseContract.TvColumns.POSTER} TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_MOVIE)
        db?.execSQL(SQL_CREATE_TABLE_TV)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldV: Int, newV: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TV")
        onCreate(db)
    }
}