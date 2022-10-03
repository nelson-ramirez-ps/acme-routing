package com.acme.routing

import com.acme.routing.shipments.router.ShipmentRouter
import com.acme.domain.models.Driver
import com.acme.routing.shipments.data.repository.ShipmentsDataSource
import com.acme.routing.shipments.data.repository.ShipmentsRepository
import org.junit.Assert
import org.junit.Test

/**
 * Verifies implementation of the top-secret algorithm
 */
class ShipmentAssignmentTest {
    //Assumptions:
    // To determine street name, we assume that all shipments are in the same city
    // and shipments only go to houses (123 fake st.) , apartments, or suites
    // name suffix like road and street, or direction prefix are considered part of the name

    //TODO mocking. Using the DataSource as is would break this test if the datasource changes.

    private var sut = ShipmentRouter(ShipmentsRepository(ShipmentsDataSource()))
    private val expectedStreet = "Aufderhar River"
    private val expectedDriver = "Orval Mayert"

    @Test
    fun `can assign correct shipment with correct score`() {
        val driver = Driver(expectedDriver) //4 vowels  7 consonants
        val result = sut.assignBestScoringShipment(driver).getOrThrow()
        Assert.assertEquals(10.5f, result.shipment?.suitabilityScore)
        Assert.assertEquals(expectedStreet, result.shipment?.streetName)
    }

    @Test
    fun `calculates correct base score`() {
        Assert.assertEquals(0f, sut.calculateBaseScore("a", "a"))
        Assert.assertEquals(0f, sut.calculateBaseScore("b", "aa"))
        Assert.assertEquals(1f, sut.calculateBaseScore("b", "a"))
        Assert.assertEquals(3f, sut.calculateBaseScore("bbc", "a"))
        Assert.assertEquals(3f, sut.calculateBaseScore("bcd", "abc"))
        Assert.assertEquals(1.5f, sut.calculateBaseScore("a", "ab"))
        Assert.assertEquals(4.5f, sut.calculateBaseScore("aei", "ab"))
        Assert.assertEquals(7f, sut.calculateBaseScore(expectedDriver, expectedStreet))
    }

    @Test
    fun `calculates correct base increase`() {
        Assert.assertEquals(0f, sut.calculateBaseIncrease("a", "b", 5f))
        Assert.assertEquals(0f, sut.calculateBaseIncrease("", "", 5f))
        Assert.assertEquals(0f, sut.calculateBaseIncrease("", "a", 5f))
        Assert.assertEquals(2.5f, sut.calculateBaseIncrease("aa", "bb", 5f))
        Assert.assertEquals(3.5f, sut.calculateBaseIncrease(expectedDriver, expectedStreet, 7f))
    }

    @Test
    fun `has common factors`() {
        Assert.assertFalse(sut.hasCommonFactors(0, 1))
        Assert.assertFalse(sut.hasCommonFactors(1, 1))
        Assert.assertFalse(sut.hasCommonFactors(1, 2))
        Assert.assertFalse(sut.hasCommonFactors(6, 7))
        Assert.assertTrue(sut.hasCommonFactors(2, 2))
        Assert.assertTrue(sut.hasCommonFactors(2, 4))
        Assert.assertTrue(sut.hasCommonFactors(100, 100000))
        Assert.assertTrue(sut.hasCommonFactors(expectedDriver.length, expectedStreet.length))
    }
}