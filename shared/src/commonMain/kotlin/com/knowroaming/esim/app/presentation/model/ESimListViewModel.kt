package com.knowroaming.esim.app.presentation.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.knowroaming.esim.app.domain.data.AssignedEsim
import com.knowroaming.esim.app.domain.model.OrderDetail
import com.knowroaming.esim.app.domain.repository.ESimRepository
import com.knowroaming.esim.app.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope


data class ESimListState(
    val data: List<AssignedEsim> = listOf(),
    val error: String? = null,
    val loading: Boolean = false,
)

data class ESimOrderState(
    val order: OrderDetail? = null,
    val error: String? = null,
    val loading: Boolean = false,
)

class ESimListViewModel(
    private val repository: ESimRepository
) : ViewModel() {

    private var orderUUID by mutableStateOf<String?>(null)
    private val state = MutableStateFlow(ESimListState())
    private val orderState = MutableStateFlow(ESimOrderState())

    val order
        @Composable get() = orderState.collectAsState()

    val esims
        @Composable get() = state.collectAsState()


    init {
        orderUUID?.let { orderId ->
            viewModelScope.launch {
                orderState.collect {
                    if (!it.loading && it.order?.orderStatus == "pending") {
                        delay(1000)
                        getOrderById(orderId)
                    }
                }
            }
        }
    }

    fun getCustomerEsimList(customerId: String) = viewModelScope.launch {
        repository.getCustomerEsimListById(customerId).collect { response ->
            when (response) {
                is Response.Success -> state.update {
                    it.copy(
                        data = response.data, loading = false
                    )
                }

                is Response.Error -> state.update {
                    it.copy(
                        error = response.message, loading = false
                    )
                }

                Response.Loading -> state.update { it.copy(loading = true) }
            }
        }
    }

    fun getOrderById(id: String) = viewModelScope.launch {
        orderUUID = id
        repository.getOrderDetailBy(id).collect { response ->
            when (response) {
                is Response.Success -> orderState.update {
                    it.copy(
                        order = response.data, loading = false
                    )
                }

                is Response.Error -> orderState.update {
                    it.copy(
                        error = response.message, loading = false
                    )
                }

                Response.Loading -> orderState.update { it.copy(loading = true) }
            }
        }
    }
}