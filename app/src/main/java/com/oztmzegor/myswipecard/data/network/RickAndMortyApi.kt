package com.oztmzegor.myswipecard.data.network

import com.oztmzegor.myswipecard.data.model.CharactersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://rickandmortyapi.com/api/"

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(@Query("page") page :Int) : Response<CharactersDto>

}