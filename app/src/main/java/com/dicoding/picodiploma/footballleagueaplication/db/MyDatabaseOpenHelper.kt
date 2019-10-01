package com.dicoding.picodiploma.footballleagueaplication.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "FootBallLeague.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            FavoritesModel.TABLE_FAVORITE, true,
            FavoritesModel.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoritesModel.ID_EVENT to TEXT + UNIQUE,
            FavoritesModel.DATE to TEXT,
            FavoritesModel.TIME to TEXT,
            FavoritesModel.HOME_TEAM to TEXT,
            FavoritesModel.AWAY_TEAM to TEXT,
            FavoritesModel.HOME_SCORE to TEXT,
            FavoritesModel.AWAY_SCORE to TEXT,
            FavoritesModel.HOME_BAGDE to TEXT,
            FavoritesModel.AWAY_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoritesModel.TABLE_FAVORITE, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)