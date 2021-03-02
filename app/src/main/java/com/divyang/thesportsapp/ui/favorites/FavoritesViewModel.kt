package com.divyang.thesports.ui.favorites

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.divyang.thesportsapp.connection.data.ConnectionDataSource
import com.divyang.thesportsapp.model.TeamMember
import com.divyang.thesportsapp.model.toTeam
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception


class FavoritesViewModel(private val prefs: SharedPreferences) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val dataSource = ConnectionDataSource()
    private val teamsResponse: MutableLiveData<List<TeamMember>> = MutableLiveData()

    val teams: LiveData<List<TeamMember>> = teamsResponse

    override fun onCleared() {
        println("on cleared")
        compositeDisposable.clear()
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
        super.onCleared()
    }

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        val badTeam = teamsResponse.value?.firstOrNull { it.id == key.toInt() }
            ?: return@OnSharedPreferenceChangeListener
        teamsResponse.value = teamsResponse.value?.minus(badTeam)
    }

    init {
        Timber.i("Start loading Favorites Team.")
        loadTeams()
        Timber.i("Team Loaded.")
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    private fun loadTeams() {
        compositeDisposable.add(
            Observable.fromIterable(prefs.all.values.filterIsInstance<Int>())
                .filter { it != 0 }
                .flatMapSingle { dataSource.getTeamDetails(it) }
                .map { it.teams?.first()?.toTeam() ?: throw Exception("Team not loaded") }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    teamsResponse.value = (teamsResponse.value ?: emptyList()) + it
                }, {
                    Timber.e("Error: ${it?.message}")
                })
        )
    }
}