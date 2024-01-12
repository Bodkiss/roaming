package com.knowroaming.esim.app.domain.repository

import com.knowroaming.esim.app.domain.data.Destination
import com.knowroaming.esim.app.domain.model.Country
import com.knowroaming.esim.app.domain.model.PackagePlan
import com.knowroaming.esim.app.util.Response
import kotlinx.coroutines.flow.Flow

interface CountryRepository {

    suspend fun getCountryList(): Flow<Response<List<Country>>>

    suspend fun getCountryListBy(destination: Destination): Flow<Response<List<Country>>>

    suspend fun getPackages(destination: Destination): Flow<Response<List<PackagePlan>>>

    suspend fun search(query: String): Flow<Response<List<Country>>>
}