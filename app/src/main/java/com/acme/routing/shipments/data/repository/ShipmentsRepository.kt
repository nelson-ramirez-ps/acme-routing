package com.acme.routing.shipments.data.repository

import com.acme.domain.models.Shipment

class ShipmentsRepository(dataSource: ShipmentsDataSource) {
    val shipments: List<Shipment> by lazy {
        dataSource.shipments
    }
}

class ShipmentsDataSource {
    //Please Note!: A typical implementation would use a DTO object to transform json input into
    // a Shipment Domain model. For the sake of time, here, we skip the DTO transformation
    // as well as a method to extract the street name from the full address.

    // ASSUMPTION: Because the algorithm uses the street name for its SS calculation, we assume
    // that we have to extract only the street name without the street number or unit information

    // To extract the street name a regex rule that finds all words bounded by a numeric word on the left
    // and by Suite or Apt. on the right. The extraction would populate the `streetName` field
    // For this exercise the method is not implemented

    //The ShipmentsDataSource is here to show that this class
    // is what is typically responsible for parsing input data

    val shipments = listOf(
        Shipment("215 Osinski Manors", "Osinski Manors"),
        Shipment("9856 Marvin Stravenue", "Marvin Stravenue"),
        Shipment("27127 Kathlyn Ferry", "Kathlyn Ferry"),
        Shipment("987 Champlin Lake", "Champlin Lake"),
        Shipment("63187 Volkman Garden Suite 447", "Volkman Garden"),
        Shipment("75855 Dessie Lights", "Dessie Lights"),
        Shipment("1797 Adolf Island Apt. 744", "Adolf Island"),
        Shipment("2431 Lindgren Corners", "Lindgren Corners"),
        Shipment("8725 Aufderhar River Suite 859", "Aufderhar River"),
        Shipment("79035 Shanna Light Apt. 322", "Shanna Light"),
    )
}
