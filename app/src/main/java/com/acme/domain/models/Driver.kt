package com.acme.domain.models

data class Driver(
    val name: String,
    var shipment: Shipment? = null
)