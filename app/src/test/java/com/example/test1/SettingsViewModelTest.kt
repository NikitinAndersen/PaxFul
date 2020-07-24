package com.example.test1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.test1.domain.SettingsInteractor
import com.example.test1.ui.settings.SettingsViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    private val savedFirstName = "Chuck"
    private val savedLastName = "Norris"
    private val savedIsOfflineMode = true

    private lateinit var viewModel: SettingsViewModel
    private val savedStateHandle = SavedStateHandle()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var interactor: SettingsInteractor

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        coroutineScope.runBlockingTest {
            whenever(interactor.getFirstName()).thenReturn(savedFirstName)
            whenever(interactor.getLastName()).thenReturn(savedLastName)
            whenever(interactor.getIsOfflineMode()).thenReturn(savedIsOfflineMode)
        }
    }


    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun loadDataTest() = testCoroutineDispatcher.runBlockingTest {

        testCoroutineDispatcher.pauseDispatcher()
        viewModel = SettingsViewModel(savedStateHandle, interactor)

        val firstName = viewModel.getFirstName().getOrAwaitValue()
        val lastName = viewModel.getLastName().getOrAwaitValue()
        val isOfflineEnabled = viewModel.getIsOfflineModeEnabled().getOrAwaitValue()

        assertNotNull(firstName)
        assertNotNull(lastName)
        assertNotNull(isOfflineEnabled)

        assertEquals(firstName, savedFirstName)
        assertEquals(lastName, savedLastName)
        assertEquals(isOfflineEnabled, savedIsOfflineMode)

        verify(interactor).getFirstName()
        verify(interactor).getLastName()
        verify(interactor).getIsOfflineMode()
    }
}

