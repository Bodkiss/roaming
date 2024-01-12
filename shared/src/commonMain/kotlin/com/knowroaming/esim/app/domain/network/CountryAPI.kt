package com.knowroaming.esim.app.domain.network

import SearchBody
import com.knowroaming.esim.app.domain.data.Destination
import com.knowroaming.esim.app.domain.model.Country
import com.knowroaming.esim.app.domain.model.PackagePlan
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST

interface CountryAPI {

    @GET("countries_list")
    suspend fun getCountryList(): List<Country>

    @POST("countries_list/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getCountryListBy(@Body data: Destination): List<Country>

    @POST("packages_list/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getPackageListBy(@Body data: Destination): List<PackagePlan>

    @POST("search/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun search(@Body data: SearchBody): List<Country>
}