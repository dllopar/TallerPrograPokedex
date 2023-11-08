package com.example.tpmultiplatformdaminallopar.android.presentation

import com.example.tpmultiplatformdaminallopar.data.model.Pokedex

sealed class PokedexScreenState {

    object Loading : PokedexScreenState()

    object Error : PokedexScreenState()

    class ShowPokedex(val pokedex: Pokedex) : PokedexScreenState()

}