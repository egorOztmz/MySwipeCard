package com.oztmzegor.myswipecard.ui.cards

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.edata.pumppayment.MainCoroutineRule
import com.edata.pumppayment.getOrAwaitValue
import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import com.oztmzegor.myswipecard.data.SwipeCardRepository
import com.oztmzegor.myswipecard.data.model.Character
import com.oztmzegor.myswipecard.data.model.CharactersDto
import com.oztmzegor.myswipecard.data.model.Info
import com.oztmzegor.myswipecard.data.model.Origin
import com.oztmzegor.myswipecard.util.ResourceProvider
import com.oztmzegor.myswipecard.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import retrofit2.Response
import java.lang.Exception
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class CardsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var sut : CardsViewModel
    private lateinit var mockRepository: SwipeCardRepository
    private lateinit var mockResourceProvider: ResourceProvider

    @Before
    fun setUp() {
        mockRepository = mock(SwipeCardRepository::class.java)
        mockResourceProvider = mock(ResourceProvider::class.java)
        sut = CardsViewModel(mockRepository, mockResourceProvider)
    }

    @Test
    fun `Given getCharacters exits with an exception, then expect error`() = runBlockingTest {
        //given
        `when`(mockRepository.getCharacters(anyInt())).thenThrow(RuntimeException())
        `when`(mockResourceProvider.getString(anyInt())).thenReturn(" ")

        //when
        sut.loadCharacters()

        //then
        val characters = sut.characters.getOrAwaitValue()
        assertThat(characters.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `Given getCharacters is not successful, then expect error`() = runBlockingTest {
        //given
        val response : Response<CharactersDto> = Response.error(404, ResponseBody.create(null, ""))
        `when`(mockRepository.getCharacters(anyInt())).thenReturn(response)
        `when`(mockResourceProvider.getString(anyInt())).thenReturn(" ")

        //when
        sut.loadCharacters()

        //then
        val characters = sut.characters.getOrAwaitValue()
        assertThat(characters.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `Given getCharacters is successful but has empty body, then expect error`() = runBlockingTest {
        //given
        val body = CharactersDto(Info(1, 1, " ", " "), listOf())
        val response : Response<CharactersDto> = Response.success(body)
        `when`(mockRepository.getCharacters(anyInt())).thenReturn(response)
        `when`(mockResourceProvider.getString(anyInt())).thenReturn(" ")

        //when
        sut.loadCharacters()

        //then
        val characters = sut.characters.getOrAwaitValue()
        assertThat(characters.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `Given getCharacters is successful and has body, then expect success`() = runBlockingTest {
        //given
        val character1 = Character(name = "character1")
        val character2 = Character(name = "character2")
        val body = CharactersDto(Info(), listOf(character1, character2))
        val response : Response<CharactersDto> = Response.success(body)
        `when`(mockRepository.getCharacters(anyInt())).thenReturn(response)
        `when`(mockResourceProvider.getString(anyInt())).thenReturn(" ")

        //when
        sut.loadCharacters()

        //then
        val characters = sut.characters.getOrAwaitValue()
        assertThat(characters.status).isEqualTo(Status.SUCCESS)
        assertThat(characters.data).containsExactly(character1, character2)
    }
}