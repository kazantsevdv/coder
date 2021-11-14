package com.kazantsev.coder.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kazantsev.coder.BuildConfig
import com.kazantsev.coder.repo.api.DataSource
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApiModule {

    @Named("baseUrl")
    @Provides
    fun baseUrl() = "https://stoplight.io/mocks/kode-education/trainee-test/25143926/"

    @Singleton
    @Provides
    fun api(
        @Named("baseUrl") baseUrl: String,
        gson: Gson,
        client: OkHttpClient
    ): DataSource =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DataSource::class.java)

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder()
        .create()


    @Singleton
    @Provides
    fun client(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor(HeaderInterceptor())
            .build()

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "code=200, example=success")
                //.addHeader("Prefer", "code=200, dynamic=true")
                //.addHeader("Prefer", "code=500, example=error-500")
                .build()
            return chain.proceed(request)
        }
    }

}

