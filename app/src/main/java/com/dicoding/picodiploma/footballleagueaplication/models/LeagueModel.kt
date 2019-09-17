package com.dicoding.picodiploma.footballleagueaplication.models


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueModel(val leagueName: String, val leagueDesc: String, val leagueId: String, val leagueImage: Int) : Parcelable {
}