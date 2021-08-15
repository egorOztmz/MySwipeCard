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
import com.oztmzegor.myswipecard.util.ResourceProvider
import com.oztmzegor.myswipecard.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "CardsViewModel"

private const val MIN_NUM_ELEMENTS_FOR_NETWORKING = 1

@HiltViewModel
class CardsViewModel @Inject constructor(
        private val swipeCardRepository: SwipeCardRepository,
        private val resourceProvider: ResourceProvider
) : ViewModel() {
    private var currentPage = 0
    private var maxPage = 0
    private var currentCharacters : MutableList<Character> = mutableListOf()

    private val _characters = MutableLiveData<Resource<List<Character>>>()
    val characters : LiveData<Resource<List<Character>>>
        get() = _characters

    fun consumeCharacter() {
        currentCharacters.removeAt(0)

        if(currentCharacters.size <= MIN_NUM_ELEMENTS_FOR_NETWORKING)
            loadCharacters()
    }

    fun loadCharacters() {
        Log.d(TAG, "loadCharacters: ")

        if(currentCharacters.size > MIN_NUM_ELEMENTS_FOR_NETWORKING) {
            _characters.postValue(Resource.success(currentCharacters))
            return
        }

        _characters.value = Resource.loading()
        viewModelScope.launch {
            val response = try {
                if(currentPage >= maxPage)
                    currentPage = 0

                swipeCardRepository.getCharacters(currentPage +1)
            }
            catch (e : Exception) {
                _characters.postValue(Resource.error(resourceProvider.getString(R.string.network_error_connection)))
                e.printStackTrace()
                return@launch
            }

            if(!response.isSuccessful) {
                Log.e(TAG, "loadCharacters: Network request error -> ${response.code()}")
                _characters.postValue(Resource.error(resourceProvider.getString(R.string.network_error_api) + response.code()))
                return@launch
            }

            val charactersDto = response.body()
            if(charactersDto == null || charactersDto.results.isNullOrEmpty()) {
                Log.w(TAG, "loadCharacters: Response was empty...")
                _characters.postValue(Resource.error(resourceProvider.getString(R.string.network_error_no_record)))
                return@launch
            }

            maxPage = charactersDto.info?.pages ?: 0
            currentCharacters.addAll(charactersDto.results)
            _characters.postValue(Resource.success(currentCharacters))
            currentPage += 1
            Log.d(TAG, "loadCharacters: $currentCharacters")
        }
    }
}