package com.oztmzegor.myswipecard.data

import com.oztmzegor.myswipecard.data.model.CharactersDto
import retrofit2.Response

interface SwipeCardRepository {
    suspend fun getCharacters(page :Int) : Response<CharactersDto>
}