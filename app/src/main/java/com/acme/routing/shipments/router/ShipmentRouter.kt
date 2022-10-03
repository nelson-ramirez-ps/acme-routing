package com.acme.routing.shipments.router

import com.acme.routing.shipments.router.errors.NoShipmentFoundError
import com.acme.routing.shipments.router.errors.NoShipmentsAvailableError
import com.acme.domain.models.Driver
import com.acme.domain.models.Shipment
import com.acme.routing.shipments.data.repository.ShipmentsRepository
import com.acme.utils.consonants
import com.acme.utils.isLengthEven
import com.acme.utils.vowels

/**
 * Responsible for finding the [Shipment] best suited for a particular [Driver]
 */
class ShipmentRouter(shipmentsRepository: ShipmentsRepository) {
    private val evenMultiplier = 1.5f
    private val oddMultiplier = 1f

    /**
     * Note: In this exercise [ShipmentsRepository] is not async, but typically it needs to be
     * since it will often retrive data from db or from a network resource
     * When that becomes necessary the assignBestScoringShipment
     * method could become a coroutine `suspend` function, allowing shipmentsRepository.shipments
     * to be called inline
     *
     */
    private val _shipmentsList = shipmentsRepository.shipments.toMutableList()

    /**
     * Assigns the best available [Shipment] to the given [Driver] based on the
     * Suitability Score (SS) calculated for the shipment.
     * @return the same received [Driver] with a mutated [Driver.shipment].
     * The [Shipment] contains the calculated [Shipment.suitabilityScore]
     */
    @Throws(NoShipmentFoundError::class, NoShipmentsAvailableError::class)
    fun assignBestScoringShipment(driver: Driver): Result<Driver> {
        return try {
            val bestShipment = findBestScoringShipment(driver.name, _shipmentsList)
            driver.shipment = bestShipment
            _shipmentsList.remove(bestShipment)
            Result.success(driver)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    /*  If the length of the shipment's destination street name is even, the base suitability score
        (SS) is the number of vowels in the driver’s name multiplied by 1.5.
        ● If the length of the shipment's destination street name is odd, the base SS is the number
        of consonants in the driver’s name multiplied by 1.
        ● If the length of the shipment's destination street name shares any common factors
            (besides 1) with the length of the driver’s name, the SS is increased by 50% above the
        base SS.*/

    // This could be optimized, maybe a BSD would be faster. this would not work great
    // for very large lists.
    private fun findBestScoringShipment(driverName: String, shipments: List<Shipment>): Shipment {
        if (shipments.isEmpty()) throw NoShipmentsAvailableError()
        var suitabilityScore = 0f
        var bestShipment: Shipment? = null
        shipments.forEach { item ->
            var score = calculateBaseScore(driverName, item.streetName)
            score += calculateBaseIncrease(driverName, item.streetName, score)
            if (score > suitabilityScore) {
                suitabilityScore = score
                bestShipment = item
            }
        }

        bestShipment?.let {
            it.suitabilityScore = suitabilityScore
            return it
        } ?: throw NoShipmentFoundError("no shipment found for driver $driverName")
    }

    internal fun calculateBaseIncrease(
        driverName: String,
        streetName: String,
        score: Float
    ): Float {
        return if (hasCommonFactors(driverName.length, streetName.length)) {
            score * 0.5f
        } else {
            0f
        }
    }


    internal fun calculateBaseScore(driverName: String, streetName: String): Float {
        return when (streetName.isLengthEven()) {
            true -> driverName.vowels().length * evenMultiplier
            else -> driverName.consonants().length * oddMultiplier
        }
    }

    //TODO this could probably  be optimized..
    internal fun hasCommonFactors(length1: Int, length2: Int): Boolean {
        var i = 2
        while (i <= length1 && i <= length1) {
            if (length1 % i == 0 && length2 % i == 0)
                return true
            ++i
        }
        return false
    }

}


