package binar.academy.flightgoadmin.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
object APIClient {

    @Singleton
    @Provides
    fun apiService(): APIService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        val retrofit =
            Retrofit.Builder().baseUrl("https://flightgo-be-server.up.railway.app/v1/api/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
        return retrofit.create(APIService::class.java)
    }
}