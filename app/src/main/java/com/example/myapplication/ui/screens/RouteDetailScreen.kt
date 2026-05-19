package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BusAlert
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Stop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailScreen(routeId: String, onBack: () -> Unit) {
    // Mock data for stops
    val stops = listOf(
        Stop("1", "Village A", 0, 0),
        Stop("2", "Village B", 1, 10),
        Stop("3", "Village C", 2, 12),
        Stop("4", "Village D", 3, 15)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Route Timeline") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            itemsIndexed(stops) { index, stop ->
                StopTimelineItem(
                    stop = stop,
                    isFirst = index == 0,
                    isLast = index == stops.size - 1,
                    status = when (index) {
                        0 -> StopStatus.PASSED
                        1 -> StopStatus.CURRENT
                        else -> StopStatus.UPCOMING
                    }
                )
            }
        }
    }
}

enum class StopStatus {
    PASSED, CURRENT, UPCOMING, CANCELLED
}

@Composable
fun StopTimelineItem(
    stop: Stop,
    isFirst: Boolean,
    isLast: Boolean,
    status: StopStatus
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(48.dp)
        ) {
            // Top line
            if (!isFirst) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(Color.Gray)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            // Indicator
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = when (status) {
                            StopStatus.PASSED -> Color(0xFF4CAF50)
                            StopStatus.CURRENT -> MaterialTheme.colorScheme.primary
                            StopStatus.UPCOMING -> Color.Gray
                            StopStatus.CANCELLED -> Color.Red
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (status) {
                        StopStatus.PASSED -> Icons.Default.CheckCircle
                        StopStatus.CURRENT -> Icons.Default.DirectionsBus
                        StopStatus.UPCOMING -> Icons.Default.DirectionsBus // Placeholder
                        StopStatus.CANCELLED -> Icons.Default.BusAlert
                    },
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.White
                )
            }

            // Bottom line
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(Color.Gray)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = stop.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (status == StopStatus.CURRENT) FontWeight.Bold else FontWeight.Normal,
                color = if (status == StopStatus.CURRENT) MaterialTheme.colorScheme.primary else Color.Unspecified
            )
            if (status == StopStatus.UPCOMING) {
                Text(
                    text = "ETA: ${stop.averageTravelTimeFromPreviousMinutes} mins",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            } else if (status == StopStatus.PASSED) {
                Text(
                    text = "Passed",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}
