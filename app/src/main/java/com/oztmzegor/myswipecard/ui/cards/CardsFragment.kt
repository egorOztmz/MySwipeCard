package com.oztmzegor.myswipecard.ui.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oztmzegor.myswipecard.R
import com.oztmzegor.myswipecard.databinding.FragmentCardsBinding
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

    }
}