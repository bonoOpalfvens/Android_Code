package com.example.fluxcode.network

import com.example.fluxcode.network.dtos.*
import com.example.fluxcode.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.lang.reflect.Type

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
    // Account
    // Post login details -> Get Bearer token
    @POST("Account/login/android")
    suspend fun login(@Body loginDTO: LoginDTO) : Response<TokenResponseDTO>

    // Post register details -> Get Bearer token
    @POST("Account/android")
    suspend fun register(@Body registerDTO: RegisterDTO) : Response<TokenResponseDTO>

    // Board
    // Get featured boards
    @GET("Board")
    suspend fun getTopBoards() : Response<List<MinimalBoardDTO>>

    // Get detailed board by id
    @GET("Board/{id}")
    suspend fun getBoardById(@Path("id") id: Int) : Response<BoardResponseDTO>

    // Post
    // Get featured posts
    @GET("Post")
    suspend fun getPosts() : Response<List<PostResponseDTO>>

    // Get detailed post by id
    @GET("Post/{id}")
    suspend fun getPostById(@Path("id") id: Int) : Response<PostDetailResponseDTO>

    // Invert like from post by ID -> Get Bearer token
    @POST("Post/{id}/like")
    suspend fun likePost(@Path("id") id: Int, @Header("Authorization") token: String) : Response<Unit>

    // Post comment -> Get Bearer token
    @POST("Post/Comment")
    suspend fun createComment(@Body commentDTO: CreateCommentDTO, @Header("Authorization") token: String) : Response<Unit>
}

object CodeApi {
    val retrofitService: CodeApiService by lazy { retrofit.create(CodeApiService::class.java) }
}
