package com.knowroaming.esim.app.presentation.model

import com.knowroaming.esim.app.domain.data.Destination
import com.knowroaming.esim.app.domain.model.PackagePlan
import com.knowroaming.esim.app.domain.repository.CountryRepository
import com.knowroaming.esim.app.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope


data class PackageListState(
    val error: String? = null,
    val loading: Boolean = false,
    val packages: List<PackagePlan> = listOf()
)

class PackageListViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PackageListState())
    val state get() = _state.asStateFlow()

    fun getPackages(destination: Destination) = viewModelScope.launch {
        repository.getPackages(destination).collectLatest { response ->
            when (response) {
                is Response.Success -> _state.update {
                    it.copy(
                        packages = response.data, loading = false
                    )
                }

                is Response.Error -> _state.update {
                    it.copy(
                        error = response.message, loading = false
                    )
                }

                Response.Loading -> _state.update { it.copy(loading = true) }
            }
        }
    }

    fun getPackageById(packageTypeId: Int): Flow<PackagePlan?> {
        return state.map {
            it.packages.firstOrNull { product ->
                product.packageTypeId == packageTypeId.toLong()
            }
        }
    }
}