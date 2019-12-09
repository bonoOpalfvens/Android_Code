package com.example.fluxcode.network

import com.example.fluxcode.network.dtos.PostResponseDTO
import com.example.fluxcode.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.lang.reflect.Type
import java.util.*

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val nullOnEmptyConverterFactory = object : Converter.Factory() {
    fun converterFactory() = this
    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
        override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
    }
}

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(nullOnEmptyConverterFactory)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface CodeApiService {
    @GET("Post")
    suspend fun getPosts() : Response<List<PostResponseDTO>>
}

object CodeApi {
    val retrofitService: CodeApiService by lazy { retrofit.create(CodeApiService::class.java) }
}
