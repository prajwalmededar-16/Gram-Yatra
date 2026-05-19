# Project Plan

Grama-Yatri: A crowdsourced real-time bus tracker for village transit. Passengers 'ping' the app when a bus arrives, and the app calculates expected arrival times for subsequent stops using Firebase Realtime Database and a timeline-based UI. The app should be modern, clear, and optimized for low-data usage.

## Project Brief

# Project Brief: Grama-Yatri

## Features
- **Crowdsourced Live Pings**: Enables passengers to report bus arrivals at their current stop with a single tap, fueling the real-time data for the community.
- **Dynamic Timeline Route Tracker**: A clear, vertical stepper UI showing all villages on a route with live status indicators and estimated arrival times.
- **Real-Time ETA Calculation**: Automatically computes and updates expected arrival times for all subsequent stops based on the latest community pings.
- **Community Alerts**: Allows users to report and view critical updates such as bus cancellations or significant delays to keep everyone informed.

## High-Level Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Navigation**: Jetpack Navigation 3 (State-driven)
- **Adaptive Strategy**: Compose Material Adaptive Library for responsive layouts across different screen sizes.
- **Backend**: Firebase Realtime Database for low-latency, real-time data synchronization.
- **Asynchronous Programming**: Kotlin Coroutines and Flow for efficient background processing and data streaming.

## Implementation Steps

### Task_1_DataLayer: Initialize Firebase Realtime Database and define the core data models (Route, Stop, Ping, Alert) along with a repository for real-time data sync.
- **Status:** IN_PROGRESS
- **Acceptance Criteria:**
  - Firebase dependencies added and configured
  - Data models for Grama-Yatri defined
  - Repository for Realtime Database integration implemented
  - Project builds successfully
- **StartTime:** 2026-05-18 21:04:09 IST

### Task_2_Timeline_UI: Develop the main UI featuring a dynamic vertical stepper/timeline for route tracking and implement the 'Ping' functionality for passengers.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Vertical timeline UI implemented using Jetpack Compose
  - Real-time ping submission functional
  - The implemented UI must match the design provided in C:/Users/Prajwal Mededar/AndroidStudioProjects/MyApplication/input_images/image_0.png
  - Navigation between views is working

### Task_3_Logic_Alerts: Implement the logic for real-time ETA calculation based on community pings and build the Community Alerts system for cancellations and delays.
- **Status:** PENDING
- **Acceptance Criteria:**
  - ETA calculations update in real-time across devices
  - Community alerts system (view and post) functional
  - Low-data usage optimizations considered in data flow

### Task_4_Verification: Apply Material 3 theme with a vibrant color scheme, implement Full Edge-to-Edge display, create an adaptive app icon, and perform final verification.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Material 3 theme and vibrant color scheme applied
  - Adaptive app icon matches 'Grama-Yatri' branding
  - Full Edge-to-Edge display implemented
  - App does not crash and meets all user requirements
  - Critic_agent verifies stability and UI alignment
  - Build pass

