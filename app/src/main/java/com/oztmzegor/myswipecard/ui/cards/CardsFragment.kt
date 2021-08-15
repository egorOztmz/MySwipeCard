package com.oztmzegor.myswipecard.ui.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.oztmzegor.myswipecard.R
import com.oztmzegor.myswipecard.databinding.FragmentCardsBinding
import com.oztmzegor.myswipecard.util.Status
import com.oztmzegor.myswipecards.SwipeCardView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardsFragment : Fragment(R.layout.fragment_cards) {
    private val cardsViewModel : CardsViewModel by viewModels()
    private lateinit var binding : FragmentCardsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        binding = FragmentCardsBinding.bind(view)

        val adapter = CharacterAdapter()
        binding.swipeCardView.cardItemAdapter = adapter

        cardsViewModel.characters.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.LOADING -> {
                    binding.progressBarContainer.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBarContainer.visibility = View.GONE

                    Snackbar.make(view, it.message ?: "Error", Snackbar.LENGTH_LONG).apply {
                        setAction(R.string.snackbar_action_retry) {
                            cardsViewModel.loadCharacters()
                        }
                    }.show()
                }
                Status.SUCCESS -> {
                    binding.progressBarContainer.visibility = View.GONE
                    if(!it.data.isNullOrEmpty())
                        adapter.setCards(it.data)
                }
            }
        }

        cardsViewModel.loadCharacters()
    }

    private val onCardConsumedListener = SwipeCardView.OnCardConsumedListener {
        cardsViewModel.consumeCharacter()
    }

    override fun onStart() {
        super.onStart()
        binding.swipeCardView.registerOnCardConsumedListener(onCardConsumedListener)
    }

    override fun onStop() {
        binding.swipeCardView.unregisterOnCardConsumedListener(onCardConsumedListener)
        super.onStop()
    }

}