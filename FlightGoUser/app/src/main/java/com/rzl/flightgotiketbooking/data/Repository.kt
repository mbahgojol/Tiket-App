package com.rzl.flightgotiketbooking.data

import com.rzl.flightgotiketbooking.data.local.SharedPref
import com.rzl.flightgotiketbooking.data.local.addBearer
import com.rzl.flightgotiketbooking.data.model.ResponseFlightList
import com.rzl.flightgotiketbooking.data.model.register.BodyAuth
import com.rzl.flightgotiketbooking.data.network.ApiService
import com.rzl.flightgotiketbooking.utils.ResultState
import com.rzl.flightgotiketbooking.utils.UiState
import com.rzl.flightgotiketbooking.utils.flowApi
import com.rzl.flightgotiketbooking.utils.flowState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService, private val pref: SharedPref
) {

    fun register(bodyRegister: BodyAuth): Flow<ResultState> = flowApi {
        apiService.register(bodyRegister)
    }

    fun login(body: BodyAuth): Flow<ResultState> = flowApi {
        val response = apiService.login(body)
        pref.saveToken(response.data.accessToken)
        response
    }

    fun listFlight(): Flow<UiState<ResponseFlightList>> = flowState {
        val token = pref.getToken().first()
        apiService.listFlight(token.addBearer())
    }

    fun getProfile(): Flow<ResultState> = flowApi {
        val token = pref.getToken().first()
        apiService.getProfile(token.addBearer())
    }

    fun getDetailTicket(id: Int): Flow<UiState<ResponseFlightList.ResponseFlightListItem>> =
        flowState {
            val token = pref.getToken().first()
            apiService.getDetailTicket(token.addBearer(), id)
        }
}