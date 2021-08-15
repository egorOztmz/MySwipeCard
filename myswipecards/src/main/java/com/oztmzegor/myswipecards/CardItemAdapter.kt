package com.oztmzegor.myswipecards

import androidx.recyclerview.widget.RecyclerView

/**
 * Write your own adapter by extending this adapter then set an object to [SwipeCardView.cardItemAdapter]
 * @param T Type of data model
 * @param VH Type of view holder
 */
abstract class CardItemAdapter<T, VH : RecyclerView.ViewHolder>(private var cards : MutableList<T> = mutableListOf()) : RecyclerView.Adapter<VH>() {

    override fun getItemCount(): Int = cards.size

    /**
     * Sets list of cards
     * @param cards List of cards
     */
    fun setCards(cards: List<T>) {
        this.cards.clear()
        this.cards.addAll(cards)
        notifyDataSetChanged()
    }

    /**
     * Removes a card at selected position
     * @param position Card position
     */
    fun removeCard(position: Int) {
        cards.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Gets list of cards
     * @return List of cards
     */
    fun getCards() : List<T> = cards

}