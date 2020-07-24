package com.example.test1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.test1.data.model.Joke
import com.example.test1.domain.MyJokeInteractor
import com.example.test1.ui.base.StateAwareResponse
import com.example.test1.ui.base.loadedState
import com.example.test1.ui.myjokes.MyJokesViewModel
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MyJokesViewModelTest {

    private val joke = Joke(id = 0, joke = "test1", isLiked = false)
    private val savedJokes = listOf(
        joke,
        Joke(id = 1, joke = "test1", isLiked = false)
    )

    private lateinit var viewModel: MyJokesViewModel
    private val savedStateHandle = SavedStateHandle()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var interactor: MyJokeInteractor

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        coroutineScope.runBlockingTest {
            whenever(interactor.getSavedJokes()).thenReturn(savedJokes.loadedState)
        }
    }

    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun successLoadDataTest() = testCoroutineDispatcher.runBlockingTest {

        testCoroutineDispatcher.pauseDispatcher()
        viewModel = MyJokesViewModel(savedStateHandle, interactor)

        val listJokes = viewModel.jokes().getOrAwaitValue()

        assertNotNull(listJokes)
        assertEquals(listJokes.size, savedJokes.size)

        listJokes.apply {
            assertNotNull(listJokes[0])
            listJokes[0].apply {
                assertEquals(joke, savedJokes[0].joke)
                assertEquals(id, savedJokes[0].id)
                assertEquals(isLiked, savedJokes[0].isLiked)
            }
        }

        verify(interactor).getSavedJokes()

    }

    @Test
    fun deleteSuccessJokeTest() = testCoroutineDispatcher.runBlockingTest {
        testCoroutineDispatcher.pauseDispatcher()
        viewModel = MyJokesViewModel(savedStateHandle, interactor)

        val listJokes = viewModel.jokes().getOrAwaitValue()

        assertNotNull(listJokes)
        assertEquals(listJokes.size, savedJokes.size)

        listJokes.apply {
            assertNotNull(listJokes[0])
            listJokes[0].apply {
                assertEquals(joke, savedJokes[0].joke)
                assertEquals(id, savedJokes[0].id)
                assertEquals(isLiked, savedJokes[0].isLiked)
            }
        }

        val listWithRemovedJoke = listOf(
            Joke(id = 1, joke = "test1", isLiked = false)
        )
        `when`(interactor.getSavedJokes()).thenReturn(listWithRemovedJoke.loadedState)
        viewModel.deleteJoke(joke)

        val listFromView = viewModel.jokes().getOrAwaitValue()

        assertNotNull(listFromView)
        assertEquals(listFromView.size, listWithRemovedJoke.size)

        listFromView.apply {
            assertNotNull(listFromView[0])
            listFromView[0].apply {
                assertEquals(joke, listWithRemovedJoke[0].joke)
                assertEquals(id, listWithRemovedJoke[0].id)
                assertEquals(isLiked, listWithRemovedJoke[0].isLiked)
            }
        }

        verify(interactor).deleteJoke(joke)
        verify(interactor, times(2)).getSavedJokes()
    }

    @Test
    fun getDataErrorTest() = testCoroutineDispatcher.runBlockingTest {
        testCoroutineDispatcher.pauseDispatcher()
        `when`(interactor.getSavedJokes()).thenReturn(StateAwareResponse.Error(Throwable("Test")))
        viewModel = MyJokesViewModel(savedStateHandle, interactor)

        val error = viewModel.getIsError().getOrAwaitValue()

        assertNotNull(error)

        verify(interactor).getSavedJokes()
    }
}

