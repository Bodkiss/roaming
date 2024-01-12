package com.knowroaming.esim.app.domain.service

import SearchBody
import com.knowroaming.esim.app.domain.data.Destination
import com.knowroaming.esim.app.domain.model.Country
import com.knowroaming.esim.app.domain.model.PackagePlan
import com.knowroaming.esim.app.domain.network.CountryAPI
import com.knowroaming.esim.app.domain.repository.CountryRepository
import com.knowroaming.esim.app.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountryService(val api: CountryAPI) : CountryRepository {
    override suspend fun getCountryList(): Flow<Response<List<Country>>> {
        return flow {
            try {
                emit(Response.Loading)
                emit(Response.Success(api.getCountryList()))
            } catch (e: Exception) {
                emit(Response.Error(e.message))
            }
        }
    }

    override suspend fun getCountryListBy(destination: Destination): Flow<Response<List<Country>>> {
        return flow {
            try {
                emit(Response.Loading)
                emit(Response.Success(api.getCountryListBy(destination)))
            } catch (e: Exception) {
                emit(Response.Error(e.message))
            }
        }
    }

    override suspend fun getPackages(destination: Destination): Flow<Response<List<PackagePlan>>> {
        return flow {
            try {
                emit(Response.Loading)
                emit(Response.Success(api.getPackageListBy(destination)))
            } catch (e: Exception) {
                emit(Response.Error(e.message))
            }
        }
    }

    override suspend fun search(query: String): Flow<Response<List<Country>>> {
        return flow {
            try {
                emit(Response.Loading)
                emit(Response.Success(api.search(SearchBody(query))))
            } catch (e: Exception) {
                emit(Response.Error(e.message))
            }
        }
    }
}
