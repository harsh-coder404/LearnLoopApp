# LearnLoop

Minimal notes for the data layer integration.

## Data Layer
- Retrofit API interface: `app/src/main/java/com/example/learnloop/data/remote/LearnLoopApi.kt`
- DTOs: `app/src/main/java/com/example/learnloop/data/remote/dto/Dtos.kt`
- DTO mappers: `app/src/main/java/com/example/learnloop/data/remote/mapper/DtoMappers.kt`
- Network module (Retrofit/OkHttp/Moshi): `app/src/main/java/com/example/learnloop/data/remote/NetworkModule.kt`
- Remote data source: `app/src/main/java/com/example/learnloop/data/remote/RemoteLearnLoopDataSource.kt`

## Base URL
Set the backend URL in `app/build.gradle.kts` via `buildConfigField("String", "BASE_URL", "...")`.

## Notes
- Mobile still does not calculate or mutate credits; it only displays backend values.
- Badge and AI logic stays on backend.
