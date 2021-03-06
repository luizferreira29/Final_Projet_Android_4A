package com.example.final_projet_android_4a.injection

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.final_projet_android_4a.data.local.AppDatabase
import com.example.final_projet_android_4a.data.local.DatabaseDao
import com.example.final_projet_android_4a.data.repository.UserRepository
import com.example.final_projet_android_4a.domain.usecase.CreateUserUseCase
import com.example.final_projet_android_4a.domain.usecase.GetUserUseCase
import com.example.final_projet_android_4a.presentation.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val presentationModule = module {
    factory { MainViewModel(get(), get()) }
}

val domainModule = module {
    factory { CreateUserUseCase(get()) }
    factory { GetUserUseCase(get()) }
}

val dataModule = module {
    single { UserRepository(get()) }
    single { createDataBase(androidContext()) }
}

fun createDataBase(context: Context): DatabaseDao {
    val appDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "luiz_db_app"
    ).build()
    return appDatabase.databaseDao()
}