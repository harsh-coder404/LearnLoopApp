# LearnLoop

LearnLoop is a peer-to-peer academic help Android app. Students post help requests, other students accept and teach them. Credits are earned by teaching and spent to learn.

Tagline: "Learn. Teach. Grow Together."

## Features
- OTP email signup flow with starter credits
- Post help requests with subject, topic, urgency, and duration
- Tutor matching (backend-driven)
- Session room (chat + shared whiteboard placeholder)
- Session summary with ratings and credit transfer preview
- Credits wallet, notifications, leaderboard, and profile

## Tech Stack
- Android: Kotlin + Jetpack Compose + MVVM
- Backend (planned): Spring Boot + PostgreSQL
- Realtime (planned): Socket.io
- Auth (planned): JWT + OTP verification

## Project Structure (MVVM)
Each screen owns its ViewModel and UI in a single package:

```
app/src/main/java/com/example/learnloop/ui/
  screens/
	login/
	  LoginScreen.kt
	  LoginViewModel.kt
	register/
	  RegisterScreen.kt
	  RegisterViewModel.kt
	...
```

Shared layers:
- `app/src/main/java/com/example/learnloop/data/` for models, repository, fake data
- `app/src/main/java/com/example/learnloop/data/remote/` for Retrofit DTOs and APIs
- `app/src/main/java/com/example/learnloop/di/` for app container / DI wiring

## Data Layer (Retrofit-ready)
- API interface: `app/src/main/java/com/example/learnloop/data/remote/LearnLoopApi.kt`
- DTOs: `app/src/main/java/com/example/learnloop/data/remote/dto/Dtos.kt`
- DTO mappers: `app/src/main/java/com/example/learnloop/data/remote/mapper/DtoMappers.kt`
- Network module: `app/src/main/java/com/example/learnloop/data/remote/NetworkModule.kt`
- Remote data source: `app/src/main/java/com/example/learnloop/data/remote/RemoteLearnLoopDataSource.kt`

## Dummy Data (Current Behavior)
This project uses a fake repository by default:

`app/src/main/java/com/example/learnloop/di/LearnLoopAppContainer.kt`

Dummy content comes from:

`app/src/main/java/com/example/learnloop/data/models/DummyData.kt`

Signup fields are prefilled for demo:
- Email: `email12@gmail.com`
- Password: `Email@123`
- Name: `Harsh`

## Credits Logic (Temporary UI Behavior)
- Credits are **not** calculated on device.
- For demo, the Session Summary screen applies a manual credit adjustment once per session using the fake repository, so the UI appears functional until backend integration.

## Base URL
Set the backend URL in `app/build.gradle.kts` via:

`buildConfigField("String", "BASE_URL", "https://your-api")`

## Build & Test
Use the Gradle wrapper from the project root:

```powershell
.\gradlew.bat test
```

```bash
./gradlew test
```

To run on device/emulator, use Android Studio or run a standard `assembleDebug` task.

## Design Notes
- Primary: #1E3A5F
- Accent: #00B4A0
- Credits/Badges: #F5A623
- Urgency: LOW=grey, MEDIUM=blue, HIGH=orange, URGENT=red

## Rules (Must Stay on Backend)
- Credit math and transfers
- Badge awarding and streak logic
- AI matching + AI session summaries

## 📱 Download the App
You can download the latest version of the LearnLoop Android app here:

[![Download APK](https://img.shields.io/badge/Download-APK-green.svg)](https://github.com/harsh-coder404/LearnLoopApp/releases/download/untagged-73d88505557593cfeab5/app-debug.apk)

## License
Private project for LearnLoop.
