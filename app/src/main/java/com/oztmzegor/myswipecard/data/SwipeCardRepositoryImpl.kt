package com.oztmzegor.myswipecard.data

import com.oztmzegor.myswipecard.data.network.RickAndMortyApi
import javax.inject.Inject

class SwipeCardRepositoryImpl
    @Inject constructor(
            private val rickAndMortyApi: RickAndMortyApi
    ) :SwipeCardRepository {

}