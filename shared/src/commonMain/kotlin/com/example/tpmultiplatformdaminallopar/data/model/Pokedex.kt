package com.example.tpmultiplatformdaminallopar.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokedex(
    @SerialName(value = "results")
    val results: List<PokedexResults>
)