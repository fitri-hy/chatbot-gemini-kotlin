package com.hytech.myapplication

import GeminiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GeminiApiService {
    @GET("gemini/{question}")
    suspend fun getAnswer(@Path("question") question: String): Response<GeminiResponse>
}
