package com.acme.domain.models

/**
 * Represents a [Shipment] that can be assigned to a [Driver] by the [ShipmentAssigner]
 * based on the [Shipment.suitabilityScore] calculated by the top-secret algorithm
 */
data class Shipment(
    val fullAddress: String,
    val streetName: String,
    var suitabilityScore: Float = 0f
)