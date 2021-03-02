package com.divyang.thesportsapp.connection.data

import com.divyang.thesportsapp.connection.HttpInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ConnectionDataSource : DataSource {

    private val api: DataSource
    private val baseUrl = "https://thesportsdb.com/api/v1/json/1/"

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpInterceptor())
            .addInterceptor {
                it.proceed(it.request())
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(DataSource::class.java)
    }


    override fun searchTeams(term: String): Single<TeamsResponseModel?> {
        return api.searchTeams(term)
    }

    override fun getTeamDetails(teamID: Int): Single<TeamsResponseModel> {
        return api.getTeamDetails(teamID)
    }
}