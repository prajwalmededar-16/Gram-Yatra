package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Bus
import com.example.myapplication.data.model.Ping
import com.example.myapplication.data.model.Route
import com.example.myapplication.data.model.RouteStatus
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepository {
    private val database = FirebaseDatabase.getInstance()
    private val routesRef = database.getReference("routes")
    private val busesRef = database.getReference("buses")
    private val pingsRef = database.getReference("pings")

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

    fun sendPing(ping: Ping) {
        val id = pingsRef.push().key ?: return
        pingsRef.child(id).setValue(ping.copy(id = id))
        
        // Logic to update bus status and ETAs would typically be a Cloud Function
        // or handled here for simple cases.
        updateBusStatus(ping)
    }

    private fun updateBusStatus(ping: Ping) {
        // Find the bus for this route and update its current stop and last ping
        // This is where you'd implement the ETA calculation logic
    }

    fun getBusStatus(routeId: String): Flow<Bus?> = callbackFlow {
        val query = busesRef.orderByChild("routeId").equalTo(routeId)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bus = snapshot.children.firstOrNull()?.getValue(Bus::class.java)
                trySend(bus)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)
        awaitClose { query.removeEventListener(listener) }
    }
}
