package com.example.amphibians.data

import com.example.amphibians.model.Amphibian
import com.example.amphibians.network.AmphibianApiService

/**
 * Interface that all repositories most extends it
 */
interface AmphibianRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

/**
 * Amphibian repository that takes data from internet
 */
class NetworkAmphibianRepository(
    private val amphibianApiService: AmphibianApiService
) : AmphibianRepository{
    override suspend fun getAmphibians(): List<Amphibian> = amphibianApiService.getAmphibians()
}