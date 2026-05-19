package com.example.myapplication.data.repository

import com.example.myapplication.data.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseBusRepository(
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
) {
    private val routesRef = database.getReference("routes")
    private val pingsRef = database.getReference("pings")
    private val alertsRef = database.getReference("alerts")

    fun getRoutes(): Flow<List<Route>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val routes = snapshot.children.mapNotNull { it.getValue(Route::class.java) }
                trySend(routes)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        routesRef.addValueEventListener(listener)
        awaitClose { routesRef.removeEventListener(listener) }
    }

    fun getRoutePings(routeId: String): Flow<List<Ping>> = callbackFlow {
        val query = pingsRef.orderByChild("routeId").equalTo(routeId)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pings = snapshot.children.mapNotNull { it.getValue(Ping::class.java) }
                    .sortedByDescending { it.timestamp }
                trySend(pings)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    fun getRouteAlerts(routeId: String): Flow<List<Alert>> = callbackFlow {
        val query = alertsRef.orderByChild("routeId").equalTo(routeId)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val alerts = snapshot.children.mapNotNull { it.getValue(Alert::class.java) }
                    .sortedByDescending { it.timestamp }
                trySend(alerts)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }

    suspend fun sendPing(ping: Ping) {
        val key = pingsRef.push().key ?: return
        pingsRef.child(key).setValue(ping.copy(id = key)).await()
    }

    suspend fun sendAlert(alert: Alert) {
        val key = alertsRef.push().key ?: return
        alertsRef.child(key).setValue(alert.copy(id = key)).await()
    }

    suspend fun saveRoute(route: Route) {
        val key = if (route.id.isEmpty()) routesRef.push().key ?: return else route.id
        routesRef.child(key).setValue(route.copy(id = key)).await()
    }
}
