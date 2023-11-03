package com.example.tpmultiplatformdaminallopar.android.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tpmultiplatformdaminallopar.android.R
import com.example.tpmultiplatformdaminallopar.android.databinding.ItemPokedexBinding
import com.example.tpmultiplatformdaminallopar.aplication.ImageBuilder
import com.example.tpmultiplatformdaminallopar.aplication.StringFormatter
import com.example.tpmultiplatformdaminallopar.data.model.PokedexResults
import com.example.tpmultiplatformdaminallopar.data.model.RandomData
import com.squareup.picasso.Picasso


class PokedexAdapter : RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder>() {

    private val pokemonList = mutableListOf<PokedexResults>()
    private val pokemonErrorList = mutableListOf<RandomData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexViewHolder {
        val pokedexBinding =
            ItemPokedexBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokedexViewHolder(pokedexBinding)
    }

    override fun onBindViewHolder(holder: PokedexViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        holder.binding.titleTextView.text =
            StringFormatter.changeFirstLetterToUppercaseAndDeleteMiddleDash(pokemon.name)

        Picasso.get()
            .load(ImageBuilder.buildPokemonImageByUrl(pokemon.url))
            .placeholder(R.drawable.pokeball)
            .error(R.drawable.pokeball)
            .into(holder.binding.iconImageView)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun updatePokedex(results: List<PokedexResults>?) {
        pokemonList.clear()
        if (results != null) {
            pokemonList.addAll(results)
        }
        notifyDataSetChanged()
    }

    fun errorPokedex(results: List<RandomData>?) {
        pokemonErrorList.clear()
        if (results != null) {
            pokemonErrorList.addAll(results)
        }
        notifyDataSetChanged()
    }

    class PokedexViewHolder(val binding: ItemPokedexBinding) :
        RecyclerView.ViewHolder(binding.root)
}
