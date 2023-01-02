package com.rzl.flightgotiketbooking.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.rzl.flightgotiketbooking.BuildConfig
import com.rzl.flightgotiketbooking.data.local.SharedPref
import com.rzl.flightgotiketbooking.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun okHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
                addInterceptor(
                    ChuckerInterceptor.Builder(context).build()
                )
            }
        }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun setupRetrofitGithub(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://flightgo-be-server.up.railway.app/v1/api/")
            .client(okHttp).addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun sharedPref(@ApplicationContext context: Context): SharedPref = SharedPref(context)
}