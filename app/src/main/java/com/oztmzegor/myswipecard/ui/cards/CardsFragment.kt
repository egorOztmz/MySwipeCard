package com.oztmzegor.myswipecard.ui.cards

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.oztmzegor.myswipecard.R
import com.oztmzegor.myswipecard.databinding.FragmentCardsBinding
import com.oztmzegor.myswipecard.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment : Fragment(R.layout.fragment_cards) {
    private val cardsViewModel : CardsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        val binding = FragmentCardsBinding.bind(view)

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
                }
            }
        }

        cardsViewModel.loadCharacters()
    }

}