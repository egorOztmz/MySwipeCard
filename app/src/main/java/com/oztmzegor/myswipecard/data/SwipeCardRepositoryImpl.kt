package com.oztmzegor.myswipecard.data

import com.oztmzegor.myswipecard.data.model.CharactersDto
import com.oztmzegor.myswipecard.data.network.RickAndMortyApi
import retrofit2.Response
import javax.inject.Inject

class SwipeCardRepositoryImpl
    @Inject constructor(
            private val rickAndMortyApi: RickAndMortyApi
    ) :SwipeCardRepository {

    override suspend fun getCharacters(page: Int): Response<CharactersDto> =
            rickAndMortyApi.getCharacters(page)

}