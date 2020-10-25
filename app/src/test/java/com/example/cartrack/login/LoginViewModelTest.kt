package com.example.cartrack.login

import android.content.SharedPreferences
import com.example.cartrack.dao.AppUserDao
import com.example.cartrack.repository.UserRepository
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import javax.inject.Inject

class LoginViewModelTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var userDao: AppUserDao


    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun hashAndSavePasswordHash() {

        val userRepository = UserRepository(userDao,sharedPref)

        val viewModel = LoginViewModel(userRepository,sharedPref)
        assertEquals("F1ED466388E237098BDD23F9440F41D79CC508CE", viewModel.hashAndSavePasswordHash("Cartrack"))
    }
}