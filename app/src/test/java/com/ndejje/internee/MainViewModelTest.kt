package com.ndejje.internee

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ndejje.internee.data.AppRepository
import com.ndejje.internee.data.Internship
import com.ndejje.internee.data.Report
import com.ndejje.internee.ui.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private val repository: AppRepository = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        whenever(repository.getAllInternships()).thenReturn(flowOf(emptyList()))
        whenever(repository.getAllReports()).thenReturn(flowOf(emptyList()))
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `submitReport calls repository insert`() = runTest {
        viewModel.submitReport(1, "Title", "Content")
        advanceUntilIdle()

        verify(repository).insertReport(any())
    }

    @Test
    fun `addInternship calls repository insert`() = runTest {
        viewModel.addInternship("Title", "Company", "Desc", "Loc")
        advanceUntilIdle()

        verify(repository).insertInternship(any())
    }

    @Test
    fun `allInternships reflects repository flow`() = runTest {
        val internships = listOf(Internship(1, "Title", "Company", "Desc", "Loc"))
        whenever(repository.getAllInternships()).thenReturn(flowOf(internships))
        
        // Re-init to pick up the new flow if necessary, 
        // though usually StateFlow would update if it wasn't a mock returning flowOf immediately
        val vm = MainViewModel(repository)
        
        assertEquals(internships, vm.allInternships.value)
    }
}
