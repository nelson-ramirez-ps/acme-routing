package com.acme.routing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.acme.domain.models.Driver
import com.acme.routing.ui.AcmeRoutingViewModel
import com.acme.routing.ui.theme.AcmeRoutingTheme

class AcmeRoutingActivity : ComponentActivity() {
    private val viewModel: AcmeRoutingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcmeRoutingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Acme Routing"
                    )
                    DriverList(drivers = viewModel.driversStateList)
                }
            }
        }

    }

    @Composable
    fun DriverList(drivers: List<Driver>) {
        LazyColumn {
            items(
                count = drivers.size,
                itemContent = { driver ->
                    CardItem(
                        driver = drivers[driver]
                    )
                })
        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun CardItem(driver: Driver) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { (driver.shipment == null) },
            elevation = 4.dp,
            onClick = { viewModel.findRouteForDriver(driver) }
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("Driver: ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.W900,
                                color = Color(0xFF4552B8)
                            )
                        ) {
                            append(driver.name)
                        }
                    }
                )

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        append("Routed to: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                            if (driver.shipment != null) {
                                append(driver.shipment!!.fullAddress)
                            } else {
                                append("Tap to assign shipment.")
                            }
                        }
                    }
                )
            }
        }
    }
}