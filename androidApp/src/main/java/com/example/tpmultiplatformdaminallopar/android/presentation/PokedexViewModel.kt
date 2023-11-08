package com.example.tpmultiplatformdaminallopar.android.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpmultiplatformdaminallopar.data.PokedexRepository
import com.example.tpmultiplatformdaminallopar.data.model.Pokedex
import com.example.tpmultiplatformdaminallopar.data.model.PokedexResults
import com.example.tpmultiplatformdaminallopar.repositoryDB.PokedexDBRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PokedexViewModel(private val pokedexRepository: PokedexRepository, private val pokedexDBRepository: PokedexDBRepository) : ViewModel() {

    val pokedex = MutableLiveData<Pokedex>()

    private val _screenState: MutableStateFlow<PokedexScreenState> = MutableStateFlow(
        PokedexScreenState.Loading
    )
    val screenState: Flow<PokedexScreenState> = _screenState

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.d("PokedexViewModel", "Error retrieving pokedex: ${throwable.message}")
        }

    /*init {
        viewModelScope.launch(coroutineExceptionHandler) {
            kotlin.runCatching {
                pokedexRepository.get()
            }.onSuccess {
                pokedex.postValue(it)
                _screenState.value = PokedexScreenState.ShowPokedex(it!!)
            }.onFailure {
                Log.d("PokedexViewModel", "Error retrieving pokedex: ${it.message}")
                _screenState.value = PokedexScreenState.Error
            }

        }
    }*/

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            val dataFromDB = pokedexDBRepository.get()

            val resultsFromDB = dataFromDB.map {
                PokedexResults(name = it.name.name, url = it.picture.url)
            }

            val pokedexFromDB = Pokedex(results = resultsFromDB)

            if (pokedexFromDB.results.isNotEmpty()) {
                _screenState.value = PokedexScreenState.ShowPokedex(pokedexFromDB)
            }

            kotlin.runCatching {
                pokedexRepository.get()
            }.onSuccess { pokedexFromApi ->
                pokedex.postValue(pokedexFromApi)
                _screenState.value = PokedexScreenState.ShowPokedex(pokedexFromApi)

                pokedexFromApi.results.forEach { pokemon ->
                    pokedexDBRepository.insert(pokemon.name, pokemon.url)
                }
            }.onFailure {
                Log.d("PokedexViewModel", "Error retrieving pokedex: ${it.message}")
                _screenState.value = PokedexScreenState.Error
            }
        }
    }



}