package com.example.tpmultiplatformdaminallopar.repositoryDB

import com.example.tpmultiplatformdaminallopar.DatabaseDriverFactory
import com.example.tpmultiplatformdaminallopar.data.model.RandomData
import com.pokedex.db.AppDatabase

class PokedexDBRepository(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.pokedexQueries

    fun insert(name:String, url:String){
        dbQuery.transaction {
            dbQuery.insertPokemon(name, url)
        }
    }
    fun get(): List <RandomData> {
        val results: List <RandomData> = dbQuery.selectAllPokemon(){
                name, url -> RandomData(NameData(name), PictureData(url))
        }.executeAsList()

        return results
    }
}