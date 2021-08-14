package com.oztmzegor.myswipecard.di

import com.oztmzegor.myswipecard.data.SwipeCardRepository
import com.oztmzegor.myswipecard.data.SwipeCardRepositoryImpl
import com.oztmzegor.myswipecard.data.network.BASE_URL
import com.oztmzegor.myswipecard.data.network.RickAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRickAndMortyApi() : RickAndMortyApi {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RickAndMortyApi::class.java)
    }

    @Singleton
    @Provides
    fun providesSwipeCardRepository(rickAndMortyApi: RickAndMortyApi) : SwipeCardRepository =
            SwipeCardRepositoryImpl(rickAndMortyApi)

}