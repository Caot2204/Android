package com.example.amphibians.network

import com.example.amphibians.model.Amphibian
import retrofit2.http.GET

/**
 * Retrofit object to call RESTful server
 */
interface AmphibianApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}