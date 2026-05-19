package com.example.myapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Stop(
    val id: String = "",
    val name: String = "",
    val order: Int = 0,
    val averageTravelTimeFromPreviousMinutes: Int = 0
)

@Serializable
data class Route(
    val id: String = "",
    val name: String = "",
    val stops: List<Stop> = emptyList()
)

@Serializable
enum class PingType {
    BUS_ARRIVED,
    BUS_DELAYED,
    BUS_CANCELLED,
    TRAFFIC_JAM,
    BUS_FULL
}

@Serializable
data class Ping(
    val id: String = "",
    val routeId: String = "",
    val stopId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val reportedBy: String = "",
    val type: PingType = PingType.BUS_ARRIVED,
    val message: String? = null
)

@Serializable
data class Bus(
    val id: String = "",
    val number: String = "",
    val routeId: String = "",
    val currentStopId: String = "",
    val lastUpdated: Long = System.currentTimeMillis(),
    val status: PingType = PingType.BUS_ARRIVED,
    val etas: Map<String, Long> = emptyMap() // key: stopId, value: timestamp
)

@Serializable
data class Alert(
    val id: String = "",
    val routeId: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val reportedBy: String = ""
)

@Serializable
data class RouteStatus(
    val routeId: String = "",
    val currentBus: Bus? = null,
    val activeAlerts: List<Alert> = emptyList()
)
