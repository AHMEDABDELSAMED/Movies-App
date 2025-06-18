package com.go.movie_tmdb.di

import androidx.room.Room
import com.go.movie_tmdb.data.local.PostDatabase
import com.go.movie_tmdb.data.remote.ApiClient
import com.go.movie_tmdb.data.remote.PostApiService
import com.go.movie_tmdb.data.repository.PostRemoteMediator
import com.go.movie_tmdb.domain.repository.PostRepository
import com.go.movie_tmdb.data.repository.PostRepositoryImpl
import com.go.movie_tmdb.domain.usecase.GetPostsUseCase
import com.go.movie_tmdb.presentation.Viewmodels.PostViewModel
import io.ktor.client.HttpClient

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        PostRemoteMediator(
            apiService = get(),
            postDao = get()
        )
    }
    // ApiService
    single { PostApiService(get()) }
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
    // DAO
    single<HttpClient> { ApiClient.provideClient() }
    single {
        Room.databaseBuilder(
            get(),
            PostDatabase::class.java,
            "post_database"
        ).build()
    }
    single { get<PostDatabase>().postDao() }
    // UseCase
    factory { GetPostsUseCase(get()) }
    // ViewModel
    viewModel { PostViewModel(get()) }
}

