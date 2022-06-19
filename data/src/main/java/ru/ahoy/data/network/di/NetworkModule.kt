package ru.ahoy.data.network.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ahoy.data.network.WeatherService

object NetworkModule {

    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun provideOkHttpClient(apiKey: String): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient()
            .newBuilder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .build()
    }

    fun provideWeatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val url = chain.request().url.newBuilder()
                .addQueryParameter("key", apiKey)
                .addQueryParameter("lang", "ru")
                .addQueryParameter("aqi", "yes")
                .build()

            return chain.proceed(chain.request().newBuilder().url(url).build())
        }
    }
}