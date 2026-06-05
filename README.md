<div align="center">

# ScreenStudio

### A polished Android screen-recording studio starter built for fast capture workflows, clean local history, and a premium Compose-first experience.

ScreenStudio is designed to feel like a modern creator tool from the first tap: a focused home screen, a bold **Start Recording** call to action, a guided recording setup flow, and a persistent recording library powered by a local Android data layer. It combines Jetpack Compose, Material 3, Room, Kotlin coroutines, and a neumorphic design system so developers can evolve it from a beautiful prototype into a production-ready screen recorder without rebuilding the foundation.

</div>

---

## Table of Contents

- [Why ScreenStudio?](#why-screenstudio)
- [Current Features](#current-features)
- [Backend and Data Layer](#backend-and-data-layer)
- [Technology Stack](#technology-stack)
- [Project Structure Map](#project-structure-map)
- [How the App Flows](#how-the-app-flows)
- [Run Locally](#run-locally)
- [Build and Test Commands](#build-and-test-commands)
- [Configuration Notes](#configuration-notes)
- [Roadmap Ideas](#roadmap-ideas)

---

## Why ScreenStudio?

Screen recording apps should be quick, trustworthy, and easy to understand. ScreenStudio starts with that goal and gives you a clean Android foundation for:

- **Instant intent** — users land on a home screen with a large recording CTA.
- **Creator-friendly setup** — the setup screen previews capture details such as resolution and storage destination.
- **Local recording history** — recording metadata is modeled and persisted locally with Room.
- **Modern Android architecture** — Compose UI, Navigation Compose, ViewModel state, Kotlin Flow, and a simple application container keep the codebase approachable.
- **Premium visual identity** — custom neumorphic cards, buttons, shadows, colors, and typography give the product a distinctive look.

---

## Current Features

### Product Experience

- **Home dashboard** with a welcome message and a prominent **Start Recording** button.
- **Recent recordings list** that observes local database records and renders them newest-first.
- **Empty-state messaging** that encourages users to start their first presentation when no recordings exist.
- **Recording setup screen** showing default capture information:
  - Resolution: `1080p`
  - Storage destination: `Internal/Movies`
- **Confirmation CTA** for the future permission and foreground-service recording flow.
- **Edge-to-edge app shell** for a modern Android layout.
- **Compose navigation** between the home and setup screens.

### Recording Metadata Model

ScreenStudio stores structured recording metadata, including:

- Unique recording ID
- File path
- Duration in milliseconds
- Resolution
- Frames per second
- Codec
- File size
- Creation timestamp
- Recovery status flag

### UI System

- **Material 3 theme** with custom light and dark color schemes.
- **Neumorphic cards** with custom drawn light and dark shadows.
- **Neumorphic buttons** with pressed/active styling.
- **Centralized typography** using Compose Material typography.
- **Reusable UI components** for consistent layouts.

### Testing Foundation

- Unit test setup with JUnit.
- Robolectric test support.
- Compose UI test support.
- Roborazzi screenshot testing dependencies and sample screenshot test coverage.
- Android instrumentation test setup.

---

## Backend and Data Layer

ScreenStudio does not depend on a remote server today. Its “backend” is an on-device Android data layer built for privacy, speed, and offline-first usage.

| Layer | Technology | Purpose |
| --- | --- | --- |
| Local database | Room | Persists recording metadata in `screenstudio_db`. |
| DAO | Room DAO + Kotlin Flow | Reads all recordings ordered by newest first, inserts records, and deletes records by ID. |
| Entity model | Kotlin data class + Room annotations | Defines the `recordings` table schema. |
| Dependency container | Custom `AppContainer` | Creates and exposes the Room database and DAO from the application object. |
| State observation | ViewModel + Flow + Compose state | Streams database changes into the home screen UI. |
| Async foundation | Kotlin coroutines | Supports suspend database writes and reactive streams. |

### Data Flow

```text
ScreenStudioApplication
        │
        ▼
AppContainer
        │
        ▼
Room database: screenstudio_db
        │
        ▼
RecordingDao ── Flow<List<RecordingEntity>>
        │
        ▼
HomeViewModel
        │
        ▼
HomeScreen recent recordings list
```

---

## Technology Stack

### Core Android

- **Kotlin** for application code.
- **Android Gradle Plugin** for Android builds.
- **Gradle Kotlin DSL** for build configuration.
- **Compile SDK 36.1**, **target SDK 36**, and **minimum SDK 24**.
- **Java 11 compatibility**.

### UI and Navigation

- **Jetpack Compose** for declarative UI.
- **Compose Material 3** for modern Material components and theming.
- **Compose BOM** for aligned Compose dependencies.
- **Navigation Compose** for screen-to-screen routing.
- **Activity Compose** for Compose integration in `MainActivity`.
- **Compose UI tooling and previews** for development support.
- **Google Fonts support for Compose text**.

### State, Lifecycle, and Architecture

- **Lifecycle Runtime KTX** and **Lifecycle Runtime Compose**.
- **ViewModel Compose** for screen state ownership.
- **Kotlin Flow** for reactive database reads.
- **Custom lightweight dependency injection** through `ScreenStudioApplication` and `AppContainer`.
- **AndroidX Core KTX** for Kotlin-friendly Android APIs.

### Local Persistence

- **Room Runtime** and **Room KTX**.
- **KSP Room compiler** for annotation processing.
- **DataStore Preferences dependency** available for future settings/preferences work.

### Recording-Oriented Dependencies Included

These dependencies are available in the project and position the app for richer capture and permission workflows:

- **Accompanist Permissions** for Compose-friendly runtime permission handling.
- **CameraX Core, Camera2, Lifecycle, and View** for camera/capture-related extensions.

### Networking and Serialization Foundation

The project includes dependencies useful for future upload, sync, API, or cloud features:

- **OkHttp**
- **OkHttp Logging Interceptor**
- **Moshi Kotlin**
- **Moshi KSP codegen**

### Build, Secrets, and Release

- **Secrets Gradle Plugin** configured to read `.env` and `.env.example`-style files.
- **KSP** for code generation.
- **Debug signing config** using a local debug keystore.
- **Release signing config** using environment-backed keystore values.
- **ProGuard rules file** included, with release minification currently disabled.

### Testing

- **JUnit 4**
- **AndroidX Test JUnit**
- **Espresso Core**
- **AndroidX Runner**
- **Robolectric**
- **Compose UI Test JUnit4**
- **Roborazzi** and **Roborazzi Compose**
- **Kotlinx Coroutines Test**

---

## Project Structure Map

```text
Screen-recorder-/
├── README.md
├── build.gradle.kts                  # Root Gradle plugin declarations
├── settings.gradle.kts               # Repository setup and :app module inclusion
├── gradle.properties                 # Gradle and Android project properties
├── gradlew / gradlew.bat             # Gradle wrapper launchers
├── gradle/
│   ├── libs.versions.toml            # Centralized versions, libraries, and plugins
│   └── wrapper/                      # Gradle wrapper files
├── app/
│   ├── build.gradle.kts              # Android app config, dependencies, signing, KSP, tests
│   ├── proguard-rules.pro            # Release shrinker/proguard rules
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml   # Application and launcher activity declaration
│       │   ├── java/com/example/
│       │   │   ├── MainActivity.kt   # Compose app shell and navigation graph
│       │   │   ├── ScreenStudioApplication.kt
│       │   │   ├── data/
│       │   │   │   ├── AppDatabase.kt
│       │   │   │   ├── RecordingDao.kt
│       │   │   │   └── RecordingEntity.kt
│       │   │   ├── di/
│       │   │   │   └── AppContainer.kt
│       │   │   ├── presentation/
│       │   │   │   ├── home/HomeScreen.kt
│       │   │   │   └── setup/SetupScreen.kt
│       │   │   └── ui/
│       │   │       ├── components/
│       │   │       │   ├── Greeting.kt
│       │   │       │   ├── NeumorphicButton.kt
│       │   │       │   └── NeumorphicCard.kt
│       │   │       └── theme/
│       │   │           ├── Color.kt
│       │   │           ├── Theme.kt
│       │   │           └── Type.kt
│       │   └── res/
│       │       ├── drawable/         # Launcher foreground/background drawables
│       │       ├── mipmap-anydpi-v26/# Adaptive launcher icons
│       │       ├── values/           # Colors, strings, themes, font certs
│       │       └── xml/              # Backup and data extraction rules
│       ├── test/                     # Unit, Robolectric, and screenshot tests
│       └── androidTest/              # Instrumentation tests
├── metadata.json
└── debug.keystore.base64
```

---

## How the App Flows

```text
Launch app
   │
   ▼
MainActivity
   │
   ▼
HomeScreen
   ├── Observes recent recordings from Room via HomeViewModel
   ├── Shows empty state when the database has no recordings
   └── Start Recording button navigates to setup
          │
          ▼
      SetupScreen
      ├── Displays recording settings preview
      └── Provides confirmation CTA for the future permission/start-recording flow
```

---

## Run Locally

### Prerequisites

- [Android Studio](https://developer.android.com/studio)
- JDK compatible with the project toolchain
- Android emulator or physical Android device

### Steps

1. Clone or download this repository.
2. Open Android Studio.
3. Select **Open** and choose this project directory.
4. Let Android Studio sync the Gradle project.
5. Create a `.env` file in the project root if you need secret-backed configuration. The Gradle secrets plugin is already configured to read `.env`.
6. If your local debug keystore is unavailable, remove or adjust the debug signing line in `app/build.gradle.kts`:

   ```kotlin
   signingConfig = signingConfigs.getByName("debugConfig")
   ```

7. Run the app on an emulator or physical device.

---

## Build and Test Commands

From the repository root:

```bash
./gradlew assembleDebug
```

```bash
./gradlew test
```

```bash
./gradlew connectedAndroidTest
```

> `connectedAndroidTest` requires a running emulator or connected Android device.

---

## CI Builds (GitHub Actions)

When building a release APK in CI environments like GitHub Actions (`./gradlew assembleRelease`), you must provide the following environment variables. If they are not provided, the build will fail during the signing configuration step:

- `KEYSTORE_PATH`: The path to your release keystore file. If not set, it defaults to `my-upload-key.jks` in the project root.
- `STORE_PASSWORD`: The password for the keystore.
- `KEY_PASSWORD`: The password for the specific key alias.

In GitHub Actions, these should be configured via Repository Secrets and passed into the environment of your build step.

---

## Configuration Notes

- App namespace: `com.example`
- Application ID: `com.aistudio.screenstudio.bxmn`
- Root project name: `My Application`
- Room database name: `screenstudio_db`
- Main launcher activity: `MainActivity`
- Application class: `ScreenStudioApplication`
- Release signing expects:
  - `KEYSTORE_PATH` or `my-upload-key.jks`
  - `STORE_PASSWORD`
  - `KEY_PASSWORD`

---

## Roadmap Ideas

ScreenStudio already has the UI, navigation, database model, and build foundation for a production recorder. Natural next steps include:

- Implement Android MediaProjection permission flow.
- Add a foreground recording service.
- Save real video files into the Movies directory.
- Insert completed recordings into Room automatically.
- Add playback, sharing, rename, delete, and file-size formatting.
- Add recording-quality controls for resolution, bitrate, frame rate, and codec.
- Add crash recovery that marks unfinished recordings with `isRecovered`.
- Add settings backed by DataStore Preferences.
- Add cloud upload or sync using OkHttp and Moshi.

---

<div align="center">

**ScreenStudio gives you a sleek Compose front end and a practical local Android backend for building a creator-grade screen recorder.**

</div>
