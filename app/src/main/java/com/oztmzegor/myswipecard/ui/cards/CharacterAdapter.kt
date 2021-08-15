package com.oztmzegor.myswipecard.ui.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oztmzegor.myswipecard.data.model.Character
import com.oztmzegor.myswipecard.databinding.ItemCardBinding
import com.oztmzegor.myswipecards.CardItemAdapter

class CharacterAdapter() : CardItemAdapter<Character, CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.binding.character = getCards()[position]
    }

}