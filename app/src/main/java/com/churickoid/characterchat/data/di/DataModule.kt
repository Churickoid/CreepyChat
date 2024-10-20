package com.churickoid.characterchat.data.di

import com.churickoid.characterchat.data.ChatApiService
import com.churickoid.characterchat.data.ChatRepository
import com.squareup.moshi.Json
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideChatApiService(): ChatApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(ChatApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideChatRepository(
        chatApiService: ChatApiService
    ): ChatRepository {
        return ChatRepository(chatApiService)
    }

    companion object {
        private const val BASE_URL = "http://fnafhuggy.ru:5500/"
    }
}