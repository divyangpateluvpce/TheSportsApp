package com.divyang.thesportsapp.connection.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DataSource {

    @GET("searchteams.php")
    fun searchTeams(@Query("t") term: String): Single<TeamsResponseModel?>

    @GET("lookupteam.php")
    fun getTeamDetails(@Query("id") teamID: Int): Single<TeamsResponseModel>

}

