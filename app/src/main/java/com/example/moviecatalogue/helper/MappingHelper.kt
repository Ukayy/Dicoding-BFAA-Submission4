package com.example.moviecatalogue.helper

import android.database.Cursor
import com.example.moviecatalogue.db.DatabaseContract
import com.example.moviecatalogue.model.Movie
import com.example.moviecatalogue.model.TVShow


object MappingHelper {
    fun toMovie(movieCursor: Cursor?): Movie {
        val movie = Movie()
        movieCursor?.moveToFirst()

        movie.id =
            movieCursor?.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.MOVIE_ID))
        movie.title =
            movieCursor?.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE))
        movie.date =
            movieCursor?.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DATE))
        movie.overview =
            movieCursor?.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW))
        movie.score =
            movieCursor?.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.SCORE))
        movie.poster =
            movieCursor?.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER))

        return movie
    }

    fun toListMovie(movieCursor: Cursor): List<Movie> {
        val data = ArrayList<Movie>()
        movieCursor.moveToFirst()

        while (movieCursor.moveToNext()) {
            val id =
                movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.MOVIE_ID))
            val title =
                movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE))
            val date =
                movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DATE))
            val overview =
                movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW))
            val score =
                movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.SCORE))
            val poster =
                movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER))

            data.add(Movie(id, title, score, date, overview, poster))
        }
        return data
    }

    fun toTv(tvCursor: Cursor?): TVShow {
        val tvShow = TVShow()
        tvCursor?.moveToFirst()

        tvShow.id =
            tvCursor?.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.TV_ID))
        tvShow.title =
            tvCursor?.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.TITLE))
        tvShow.date =
            tvCursor?.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.DATE))
        tvShow.overview =
            tvCursor?.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.OVERVIEW))
        tvShow.score =
            tvCursor?.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.SCORE))
        tvShow.poster =
            tvCursor?.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.POSTER))

        return tvShow
    }

    fun toListTv(tvCursor: Cursor): List<TVShow> {
        val data = ArrayList<TVShow>()
        tvCursor.moveToFirst()

        while (tvCursor.moveToNext()) {
            val id =
                tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.TV_ID))
            val title =
                tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.TITLE))
            val date =
                tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.DATE))
            val overview =
                tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.OVERVIEW))
            val score =
                tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.SCORE))
            val poster =
                tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvColumns.POSTER))

            data.add(TVShow(id, title, score, date, overview, poster))
        }
        return data
    }
}