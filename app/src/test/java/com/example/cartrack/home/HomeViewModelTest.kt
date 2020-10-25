package com.example.cartrack.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cartrack.TestCoroutineRule
import com.example.cartrack.repository.NetworkUserRepository
import com.example.cartrack.response.User
import com.example.cartrack.util.Resource
import com.example.cartrack.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*
import java.lang.Exception

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var networkUserRepository: NetworkUserRepository

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<List<User>>>

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<User>())
                .`when`(networkUserRepository)
                .getUsers()
            val viewModel = HomeViewModel(networkUserRepository)
            viewModel.getUsers().observeForever(apiUsersObserver)
            verify(networkUserRepository).getUsers()
            verify(apiUsersObserver).onChanged(Resource.success(emptyList()))
            viewModel.getUsers().removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            doThrow(Exception(errorMessage))
                .`when`(networkUserRepository)
                .getUsers()
            val viewModel = HomeViewModel(networkUserRepository)
            viewModel.getUsers().observeForever(apiUsersObserver)
            verify(networkUserRepository).getUsers()
            verify(apiUsersObserver).onChanged(
                Resource.error(null,errorMessage)
            )
            viewModel.getUsers().removeObserver(apiUsersObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }
}