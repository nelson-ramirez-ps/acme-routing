package com.acme.routing.ui

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.acme.routing.shipments.router.ShipmentRouter
import com.acme.domain.models.Driver
import com.acme.routing.drivers.data.repository.DriverDataSource
import com.acme.routing.drivers.data.repository.DriverRepository
import com.acme.routing.shipments.data.repository.ShipmentsDataSource
import com.acme.routing.shipments.data.repository.ShipmentsRepository

class AcmeRoutingViewModel : ViewModel() {
    //TODO Dependency injection
    private val driverRepository: DriverRepository = DriverRepository(DriverDataSource())
    private val shipmentRouter: ShipmentRouter = ShipmentRouter(
        ShipmentsRepository(
            ShipmentsDataSource()
        )
    )

    private val _drivers = driverRepository.drivers.toMutableStateList()

    //TODO create a ViewState that can represent Error,Success,Loading states for the view to
    // render, for now only Success is done.
    val driversStateList: List<Driver>
        get() = _drivers


    fun findRouteForDriver(driver: Driver) {
        _drivers.find { driver.name == it.name && it.shipment == null }?.let { foundDriver ->
            shipmentRouter.assignBestScoringShipment(foundDriver).fold({
                val index = _drivers.indexOf(it)
                _drivers.removeAt(index)
                _drivers.add(index, it)
            }, {
                //TODO handle error
            })

        }

    }
}