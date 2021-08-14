package com.oztmzegor.myswipecard.ui.cards

import androidx.lifecycle.ViewModel
import com.oztmzegor.myswipecard.data.SwipeCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
        private val swipeCardRepository: SwipeCardRepository
) : ViewModel() {
}