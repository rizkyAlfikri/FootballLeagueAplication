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

    // make database and table favorite
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
            FavoritesModel.HOME_BADGE to TEXT,
            FavoritesModel.AWAY_BADGE to TEXT,
            FavoritesModel.ID_LEAGUE to TEXT


        )

        db.createTable(
            FavoriteTeamModel.TABLE_TEAM_FAVORITE, true,
            FavoriteTeamModel.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeamModel.ID_TEAM to TEXT + UNIQUE,
            FavoriteTeamModel.ID_LEAGUE to TEXT,
            FavoriteTeamModel.TEAM_NAME to TEXT,
            FavoriteTeamModel.LEAGUE_NAME to TEXT,
            FavoriteTeamModel.TEAM_BADGE to TEXT,
            FavoriteTeamModel.WIN_TEAM to INTEGER,
            FavoriteTeamModel.DRAW_TEAM to INTEGER,
            FavoriteTeamModel.LOST_TEAM to INTEGER
        )
    }

    // upgrade database version if needed
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoritesModel.TABLE_FAVORITE, true)
        db.dropTable(FavoriteTeamModel.TABLE_TEAM_FAVORITE, true)
    }
}

// this statement make us able to call database in activity or fragment
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)