package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Bus
import com.example.myapplication.data.model.Ping
import com.example.myapplication.data.model.PingType
import com.example.myapplication.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    private val _currentBus = MutableStateFlow<Bus?>(null)
    val currentBus: StateFlow<Bus?> = _currentBus

    init {
        // In a real app, we'd observe a specific route
        // observeBusStatus("route1")
    }

    fun observeBusStatus(routeId: String) {
        viewModelScope.launch {
            repository.getBusStatus(routeId).collect {
                _currentBus.value = it
            }
        }
    }

    fun reportBusArrival(stopId: String, routeId: String) {
        val ping = Ping(
            routeId = routeId,
            stopId = stopId,
            type = PingType.BUS_ARRIVED,
            reportedBy = "User" // Replace with actual user ID
        )
        repository.sendPing(ping)
    }
}
