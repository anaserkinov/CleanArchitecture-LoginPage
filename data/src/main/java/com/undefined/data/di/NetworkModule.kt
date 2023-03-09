/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.di

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.undefined.data.network.api.AuthService
import com.undefined.data.utils.Server
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(preference: SharedPreferences): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://quotes.esoteric.uz/")
            .client(
                OkHttpClient
                    .Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(
                        Interceptor {
                            val requestBuilder = it.request().newBuilder()
                            requestBuilder.addHeader(
                                "Authorization",
                                "Bearer ${preference.getString("token", "")}"
                            )
                            requestBuilder.addHeader(
                                "Accept-Language",
                                "uz"
                            )
                            val request = requestBuilder.build()
                            Server.makeResponse(request, it.proceed(request))
                        }
                    )
                    .build()
            ).addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            ))
            .build()
    }

    @Provides
    fun provideAuthService(retrofit: Retrofit) = retrofit.create(AuthService::class.java)

}