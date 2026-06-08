# Movie App — Technical Assessment

Android app for browsing movies by genre using the [TMDB API](https://www.themoviedb.org/documentation/api).

## Features

- Browse all official movie genres
- Discover movies by genre with infinite scroll
- Sort movies by popularity, top rated, or newest
- View movie detail: overview, trailers, and reviews

## Tech Stack

| Category | Library | Version |
|---|---|---|
| Language | Kotlin | 2.2.10 |
| UI | Jetpack Compose + Material3 | BOM 2025.05.01 |
| Architecture | Clean Architecture + MVVM | — |
| DI | Hilt | 2.59.2 |
| Navigation | Navigation Compose | 2.9.0 |
| Networking | Retrofit + OkHttp | 2.11.0 / 4.12.0 |
| Serialization | Kotlinx Serialization JSON | 1.8.1 |
| Image Loading | Coil 3 | 3.2.0 |
| Async | Kotlin Coroutines + Flow | 1.10.2 |
| Build | AGP | 9.1.1 |

## Architecture

```
app/
├── core/
│   ├── di/          # Hilt modules
│   ├── network/     # Auth interceptor
│   └── utils/       # ApiResult, extensions
├── data/
│   ├── dto/         # API response models
│   ├── remote/      # Retrofit API client & data source
│   └── repository/  # Repository implementations
├── domain/
│   ├── model/       # Domain models
│   ├── mapper/      # DTO → Domain mappers
│   ├── usecase/     # Use cases
│   └── repository/  # Repository interfaces
└── presentation/
    ├── navigation/  # NavGraph
    └── ui/
        ├── genre/   # Genre list screen
        ├── movies/  # Movie list screen
        └── detail/  # Movie detail screen
```

## Setup

1. Get a free API key from [TMDB](https://www.themoviedb.org/settings/api)
2. Add it to `local.properties`:
   ```
   TMDB_API_KEY=your_api_key_here
   ```
3. Build and run
