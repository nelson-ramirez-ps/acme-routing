package com.acme.routing.drivers.data.repository

import com.acme.domain.models.Driver

class DriverRepository(private val driverDataSource: DriverDataSource) {
    //Note: this is typically async, it's not for this exercise
    val drivers: List<Driver> by lazy { driverDataSource.drivers }
}

//NOTE: there would normally be a DTO that transforms to Driver.
// e.g DriverDTO.toDriver()
data class DriverDataSource(
    val drivers: List<Driver> = listOf(
        Driver("Everardo Welch"),
        Driver("Orval Mayert"),
        Driver("Howard Emmerich"),
        Driver("Izaiah Lowe"),
        Driver("Monica Hermann"),
        Driver("Ellis Wisozk"),
        Driver("Noemie Murphy"),
        Driver("Cleve Durgan"),
        Driver("Murphy Mosciski"),
        Driver("Kaiser Sose"),
    )
)