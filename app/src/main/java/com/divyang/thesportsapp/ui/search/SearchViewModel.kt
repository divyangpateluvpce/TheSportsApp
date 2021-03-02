package com.divyang.thesportsapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.divyang.thesportsapp.connection.data.ConnectionDataSource
import com.divyang.thesportsapp.model.TeamMember
import com.divyang.thesportsapp.model.toTeam
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SearchViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val dataSource = ConnectionDataSource()
    private val teamsResponse: MutableLiveData<List<TeamMember>> = MutableLiveData()

    val teams: LiveData<List<TeamMember>> = teamsResponse

    override fun onCleared() {
        println("on cleared")
        compositeDisposable.clear()
        super.onCleared()
    }

    fun searchTeams(term: String) {
        loadTeams(term)
    }

    private fun loadTeams(term: String) {
        compositeDisposable.add(
            dataSource.searchTeams(term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.teams?.toTeam() ?: emptyList() }
                .subscribe({
                    teamsResponse.value = it
                }, {
                    Log.e("MainViewModel", "Error: ${it.message}")
                })
        )
    }

}