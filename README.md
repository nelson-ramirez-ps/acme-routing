# acme-routing
Sample app for routing shipments to the most suitable drivers


# Pre-requisites
Latest version Android Studio 

# How to Run
* clone this repository
* Open in Android studio
* Run via the Run dialog

![image](https://user-images.githubusercontent.com/114951188/193660346-4d913039-2d6f-4e88-9e80-a25a1352212b.png)

alternatively you can install with gradle using: 
`./gradlew installDebug`

# Testing
Run unit tests by right-clicking on the desired test and selecting run test.

There is full test coverage for the logic of the `ShipmentRouter`

![image](https://user-images.githubusercontent.com/114951188/193660791-be7d61d4-ed15-4aa8-b744-d5764b870274.png)

alternatively you can run all tests with gradle using:
`./gradlew test`


# About the App:

This app uses simplified MVVM and implements the Ui with Jetpack Compose.
The packages are arranged by feature to make future modularization easy. 
All functionality is contained within the `com.acme.routing` package and shared domain models are in `com.acme.domain.models`

<img width="325" alt="image" src="https://user-images.githubusercontent.com/114951188/193663065-b04d3b88-dcc1-4932-875e-146e364b409f.png">



# Developer notes
* The app illustrates a basic setup but leaves out a few key things out:
* Dependency injection setup
* Mocking for tests
* DTOs for transforming incoming data into Domain models
* ViewState that represents loading, error and success states
* Efficiency optimizations in the `ShipmentRouter` implementation


## Assumptions
Two assumptions about the functionality of the app were made:
* Once an assignment is made for a driver, the assignment cannot be undone or reasigned
* The algorithm relies on the street name only and not the full address. Street name extraction is not implemented and was done by hand.

## Trade-offs
* Do another code and polish pass on the ui implementation, it's very basic!
* For the sake of time no DI was introduced. Would most likely go with Koin or Hilt. 
* I left out my favorite testing tools Kotest and Mockk
* The Suitability Score is calculated and stored with the `Shipment` model, but it is not exposed in the Ui


## Next steps
* Add async capabilities to data fetching via the Repositories using coroutines
* Think about data persistence and cacheing
* Formalize View State and take another pass at the Compose ui implementation
* Add test coverage to viewModel
* Setup Koin or Hilt DI
* Bring in Kotest and Mockk to help write better tests









