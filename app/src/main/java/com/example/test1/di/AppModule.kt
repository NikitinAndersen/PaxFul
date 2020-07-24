package com.example.test1.di

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.example.test1.BuildConfig
import com.example.test1.ShakeDetectorUtil
import com.example.test1.data.converter.JokeConverter
import com.example.test1.data.converter.JokeConverterImpl
import com.example.test1.data.db.AppDatabase
import com.example.test1.data.db.SharedPreferenceManager
import com.example.test1.data.db.SharedPreferenceManagerImpl
import com.example.test1.data.network.JokesApi
import com.example.test1.data.repository.AppRepository
import com.example.test1.data.repository.AppRepositoryImpl
import com.example.test1.di.QualifierNames.getNamed
import com.example.test1.domain.JokeInteractor
import com.example.test1.domain.MyJokeInteractor
import com.example.test1.domain.SettingsInteractor
import com.example.test1.domain.interactors.JokeInteractorImpl
import com.example.test1.domain.interactors.MyJokeInteractorImpl
import com.example.test1.domain.interactors.SettingsInteractorImpl
import com.example.test1.ui.home.JokesViewModel
import com.example.test1.ui.myjokes.JokeSharedViewModel
import com.example.test1.ui.myjokes.addjokedialog.AddJokeViewModel
import com.example.test1.ui.myjokes.MyJokesViewModel
import com.example.test1.ui.settings.SettingsViewModel
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

var baseUrl = BuildConfig.BASE_URL

val appModule = module {
    single {
        ConnectionPool(20, 5, TimeUnit.MINUTES)
    }

    single {
        OkHttpClient.Builder().run {
            val timeout = if (BuildConfig.DEBUG) 15L else 20L
            readTimeout(timeout, TimeUnit.SECONDS)
            writeTimeout(timeout, TimeUnit.SECONDS)
            connectTimeout(timeout, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
            connectionPool(get())
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(httpLoggingInterceptor)
            build()
        }
    }

    factory(getNamed { QUALIFIER_BASE_URL }) {
        baseUrl
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<String>(getNamed { QUALIFIER_BASE_URL }))
            .client(get())
            .addConverterFactory(get())
            .build()
    }

    single<AppRepository> {
        AppRepositoryImpl(get())
    }
    single {
        get<Retrofit>().create(JokesApi::class.java)
    }

    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().getDao() }


    single<JokeInteractor> {
        JokeInteractorImpl(get(), get(), get(), get())
    }

    single<MyJokeInteractor> {
        MyJokeInteractorImpl(get(), get(), get())
    }

    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    single<SharedPreferenceManager> {
        SharedPreferenceManagerImpl(androidContext())
    }

    single<JokeConverter> {
        JokeConverterImpl(get())
    }

    factory {
        ShakeDetectorUtil(get())
    }

    viewModel { (handle: SavedStateHandle) -> JokesViewModel(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> MyJokesViewModel(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> SettingsViewModel(handle, get()) }
    viewModel { (handle: SavedStateHandle) -> AddJokeViewModel(handle, get()) }

}