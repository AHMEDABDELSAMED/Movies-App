# Movie App (Test)

A simple Android application that displays a list of movies using a TMDb API. It supports a Search feature, loads data using Paging 3, and follows a modern MVVM architecture with Koin.

The **MVVM (Model-View-ViewModel)  architecture pattern was used to clearly separate concerns and support testability, as follows:

> **Data Flow:**  
> Movie data is fetched from the API, then saved into the local Room database.
> The UI always displays data from Room. This approach ensures offline support and a smooth user experience.
> Paging 3 is integrated with Room to load data efficiently in pages.
> Search queries are executed directly against the API to provide real-time and accurate results.

## ▶ How to Run the Project
1. Open the project using Android Studio (Meerkat | 2024.3.1) or  newer .
2. Make sure you are connected to the internet.
3. Click "Run" to start the app on an emulator or a real device.

## use
- Jetpack Compose
- Kotlin + Coroutines + Flow
- MVVM + Paging 3 + Room
- Koin for DI
- Ktor Client for networking
- Navigation Components + Single Activity Architecture

