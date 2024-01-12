package com.knowroaming.esim.app.presentation.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.knowroaming.esim.app.domain.data.Destination
import com.knowroaming.esim.app.domain.model.Country
import com.knowroaming.esim.app.domain.repository.CountryRepository
import com.knowroaming.esim.app.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope


data class CountryListState(
    val data: List<Country> = listOf(),
    val error: String? = null,
    val loading: Boolean = false,
)

class CountryListViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val _search = MutableStateFlow(CountryListState())
    private val _popular = MutableStateFlow(CountryListState())
    private val _countries = MutableStateFlow(CountryListState())

    val countries
        @Composable get() = _countries.collectAsState()

    val popular
        @Composable get() = _popular.collectAsState()

    val search
        @Composable get() = _search.collectAsState()

    init {
        getCountries()
        getCountries(Destination(region = "Popular"))
    }

    private fun getCountries() = viewModelScope.launch {
        repository.getCountryList().collectLatest { response ->
            when (response) {
                is Response.Success -> _countries.update {
                    it.copy(
                        data = response.data, loading = false
                    )
                }

                is Response.Error -> _countries.update {
                    it.copy(
                        error = response.message, loading = false
                    )
                }

                Response.Loading -> _countries.update { it.copy(loading = true) }
            }
        }
    }

    private fun getCountries(destination: Destination) = viewModelScope.launch {
        repository.getCountryListBy(destination).collectLatest { response ->
            when (response) {
                is Response.Success -> _popular.update {
                    it.copy(
                        data = response.data, loading = false
                    )
                }

                is Response.Error -> _popular.update {
                    it.copy(
                        error = response.message, loading = false
                    )
                }

                Response.Loading -> _popular.update { it.copy(loading = true) }
            }
        }
    }

    fun search(query: String) = viewModelScope.launch {
        repository.search(query).collectLatest { response ->
            when (response) {
                is Response.Success -> _search.update {
                    it.copy(data = response.data, loading = false)
                }

                is Response.Error -> _search.update {
                    it.copy(
                        error = response.message, loading = false
                    )
                }

                Response.Loading -> _search.update { it.copy(loading = true) }
            }
        }
    }
}