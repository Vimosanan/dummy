package com.example.cartrack.di

import com.example.cartrack.login.LoginActivity
import com.example.cartrack.register.RegisterActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,  NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(registerActivity: RegisterActivity)
    fun inject(loginActivity: LoginActivity)
}