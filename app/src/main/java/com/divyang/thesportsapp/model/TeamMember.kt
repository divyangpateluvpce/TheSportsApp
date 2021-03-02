package com.divyang.thesportsapp.model

data class TeamMember(
    val id: Int,
    val name: String,
    val sport: String,
    val country: String,
    val iconURL: String?,
    val isFavorite: Boolean,
    val description: String
) {

}
