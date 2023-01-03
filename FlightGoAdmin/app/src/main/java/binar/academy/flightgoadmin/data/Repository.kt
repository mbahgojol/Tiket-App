package binar.academy.flightgoadmin.data

import binar.academy.flightgoadmin.data.database.DataStoreAdmin
import binar.academy.flightgoadmin.data.model.ResponseAccTrx
import binar.academy.flightgoadmin.data.model.ResponseOrderDetail
import binar.academy.flightgoadmin.data.model.ResponseTransaction
import binar.academy.flightgoadmin.data.model.tiket.ResponseMessage
import binar.academy.flightgoadmin.data.model.tiket.TiketResponse
import binar.academy.flightgoadmin.data.remote.ApiAdminService
import binar.academy.flightgoadmin.utils.ResultState
import binar.academy.flightgoadmin.utils.UiState
import binar.academy.flightgoadmin.utils.flowApi
import binar.academy.flightgoadmin.utils.flowState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.RequestBody
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiAdminService, private val pref: DataStoreAdmin
) {
    fun listFlight(): Flow<UiState<TiketResponse>> = flowState {
        val token = pref.getToken().first()
        apiService.listFlight(token.addBearer())
    }

    fun getProfile(): Flow<ResultState> = flowApi {
        val token = pref.getToken().first()
        apiService.getProfile(token.addBearer())
    }

    fun getOrderDetail(
        id: Int
    ): Flow<UiState<ResponseOrderDetail>> = flowState {
        val token = pref.getToken().first()
        apiService.getOrderDetail(token.addBearer(), id)
    }

    fun getTransaction(): Flow<UiState<ResponseTransaction>> = flowState {
        val token = pref.getToken().first()
        apiService.getTransaction(token.addBearer())
    }

    fun createTiket(
        body: RequestBody
    ): Flow<UiState<ResponseMessage>> = flowState {
        val token = pref.getToken().first()
        apiService.createTiket(token.addBearer(), body)
    }

    fun accTransaction(
        id: Int
    ): Flow<UiState<ResponseAccTrx>> = flowState {
        val token = pref.getToken().first()
        apiService.accTransaction(token.addBearer(), id)
    }

    fun rejectTransaction(
        id: Int
    ): Flow<UiState<ResponseAccTrx>> = flowState {
        val token = pref.getToken().first()
        apiService.rejectTransaction(token.addBearer(), id)
    }
}

fun String.addBearer() = "Bearer $this"