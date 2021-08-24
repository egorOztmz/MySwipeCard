package com.oztmzegor.myswipecard.di

import android.content.Context
import com.oztmzegor.myswipecard.data.SwipeCardRepository
import com.oztmzegor.myswipecard.data.SwipeCardRepositoryImpl
import com.oztmzegor.myswipecard.data.network.BASE_URL
import com.oztmzegor.myswipecard.data.network.RickAndMortyApi
import com.oztmzegor.myswipecard.util.ResourceProvider
import com.oztmzegor.myswipecard.util.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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

    @Singleton
    @Provides
    fun providesResourceProvider(@ApplicationContext context: Context) :ResourceProvider =
        ResourceProviderImpl(context)

    @Provides
    fun providesBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.IO

}