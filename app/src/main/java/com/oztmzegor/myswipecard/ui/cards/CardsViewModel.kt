package com.oztmzegor.myswipecard.ui.cards

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oztmzegor.myswipecard.R
import com.oztmzegor.myswipecard.data.SwipeCardRepository
import com.oztmzegor.myswipecard.data.model.Character
import com.oztmzegor.myswipecard.util.Resource
import com.oztmzegor.myswipecard.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "CardsViewModel"

@HiltViewModel
class CardsViewModel @Inject constructor(
        @ApplicationContext private val context : Context,
        private val swipeCardRepository: SwipeCardRepository
) : ViewModel() {

    private val _characters = MutableLiveData<Resource<List<Character>>>()
    val characters : LiveData<Resource<List<Character>>>
        get() = _characters

    fun loadCharacters(page : Int = 0) {
        _characters.value = Resource.loading()

        viewModelScope.launch {
            val response = try {
                swipeCardRepository.getCharacters(page)
            }
            catch (e : Exception) {
                _characters.postValue(Resource.error(context.getString(R.string.network_error_connection)))
                e.printStackTrace()
                return@launch
            }

            if(!response.isSuccessful) {
                Log.e(TAG, "loadCharacters: Network request error -> ${response.code()}")
                _characters.postValue(Resource.error(context.getString(R.string.network_error_api) + response.code()))
                return@launch
            }

            val charactersDto = response.body()
            if(charactersDto == null || charactersDto.results.isNullOrEmpty()) {
                Log.w(TAG, "loadCharacters: Response was empty...")
                _characters.postValue(Resource.error(context.getString(R.string.network_error_no_record)))
                return@launch
            }

            with(charactersDto.results) {
                _characters.postValue(Resource.success(this))
                Log.d(TAG, "loadCharacters: $this")
            }
        }
    }
}