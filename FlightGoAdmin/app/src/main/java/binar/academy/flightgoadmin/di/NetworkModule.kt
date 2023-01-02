package binar.academy.flightgoadmin.di

import android.content.Context
import binar.academy.flightgoadmin.BuildConfig
import binar.academy.flightgoadmin.data.database.DataStoreAdmin
import binar.academy.flightgoadmin.data.remote.ApiAdminService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
                addInterceptor(
                    ChuckerInterceptor.Builder(context).build()
                )
            }
        }.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttp)
            .baseUrl("https://flightgo-be-server.up.railway.app/v1/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiAdminService =
        retrofit.create(ApiAdminService::class.java)

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): DataStoreAdmin = DataStoreAdmin(context)
}