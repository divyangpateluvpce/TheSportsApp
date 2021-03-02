package com.divyang.thesportsapp.model

import com.divyang.thesportsapp.connection.data.TeamsModel

fun List<TeamsModel>.toTeam(): List<TeamMember>? = map { it.toTeam() }

fun TeamsModel.toTeam(): TeamMember {
    return TeamMember(
        id = idTeam,
        name = strTeam ?: "Unknown",
        iconURL = strTeamBadge,
        isFavorite = false,
        sport = strSport ?: "Unknown",
        country = strCountry ?: "Unknown",
        description = strDescriptionEN ?: " No Description"
    )
}
