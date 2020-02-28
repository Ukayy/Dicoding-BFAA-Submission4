package com.example.moviecatalogue.db

import android.provider.BaseColumns


internal class DatabaseContract {

    internal class MovieColumns : BaseColumns {
        companion object {
            const val TABLE_MOVIE = "movie"
            const val _ID = "_id"
            const val MOVIE_ID = "id"
            const val TITLE = "title"
            const val SCORE = "score"
            const val DATE = "date"
            const val OVERVIEW = "overview"
            const val POSTER = "poster"
        }
    }

    internal class TvColumns: BaseColumns {
        companion object {
            const val TABLE_TV = "tvshow"
            const val _ID = "_id"
            const val TV_ID = "id"
            const val TITLE = "title"
            const val SCORE = "score"
            const val DATE = "date"
            const val OVERVIEW = "overview"
            const val POSTER = "poster"
        }
    }
}