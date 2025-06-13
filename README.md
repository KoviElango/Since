
---

# Since – Habit Breaker Tracker

**Since** is a focused Android application designed to help users break bad habits through commitment-based tracking. Whether you're reducing distractions, avoiding sugar, or limiting screen time, the app encourages long-term behavior change using simple psychology and minimal design.

---

## Key Features

- **Single Habit Focus** Track one streak at a time for better commitment and reduced mental clutter.

- **Real-Time Streak Timer** See exactly how long it’s been since your last reset, down to the second.

- **Reset Confirmation with Reflection** A custom message from your past self is shown before you reset—helping you reconnect with your original motivation.

- **Claimable Achievements** After reaching threshold on a streak, you can retire it and permanently archive your achievement.

- **Reward Centre** View all completed achievements in a clean, scrollable archive designed like a personal trophy cabinet.

- **Minimal and Purposeful UI** Designed to stay out of the way and let the habit do the work.

---

## Screenshots

![image](https://github.com/user-attachments/assets/c0d5c4ea-518b-4900-9cbf-f9cb412076a6)

![image](https://github.com/user-attachments/assets/a410b313-f808-4b0b-ab47-d6c43ff08b97)

![image](https://github.com/user-attachments/assets/74b933f4-1fd6-4e61-9c18-83dcce0cb1f9)



## Tech Stack

- **Kotlin** – Primary development language
- **Jetpack Compose** – UI framework
- **Room Database** – Local storage for streak and achievement data
- **MVVM Architecture** – Clean separation of concerns
- **Coroutines + StateFlow** – Reactive, efficient UI updates
- **Material Design 3** – Latest design components and theming

---

## Design Principles

- **Clarity** Every screen has a purpose. No clutter. No redundant stats.

- **Support at Key Decision Points** Special attention is given to the moment you reset—this is when most users break their progress.

- **Privacy** No account creation. All data is stored locally on-device.

- **Minimal** Achievements are visual and celebratory, but never intrusive.

---

## Development Notes

- One active streak is allowed at a time, design choice from behavioural study.
- Streak history is limited to three items to reduce cognitive load.
- A local Room database with two tables for streaks and claimed achievements, include a migration script when updating schema.
- All data transformations are handled in utility functions, keeping the UI layer presentation-focused.
- UI previews are available for major components via `@Preview`.

---

## Installation

1. Clone the repository.
2. Open in Android Studio (Hedgehog or newer recommended).
3. Sync Gradle and run the project on a device or emulator (minimum SDK 34).

---

## Roadmap

- [x] Core streak timer and reset system
- [x] Reflection system tied to resets
- [x] Achievement claiming after 45 days
- [x] Trophy archive and reward dashboard
- [x] Home screen widget
- [ ] Daily/weekly reminder system
- [ ] Notification centre
- [ ] ML system to predict habit completion

---



---

# Since - Technical Documentation
> _Version: 1.2.2_ 
> _Last Updated: 2025-04-25_  
> _Author: Koovendhan Elango_  

---

## 1. Overview

**Since** is a minimalist, offline-first Android app built to **track behavioral streaks** for habit-breaking and self-discipline reinforcement.  
It enables users to **start**, **pause**, and **reset** streaks while recording **personal bests** and **claiming achievements**.

The architecture emphasizes **Clean Architecture principles**, **scalability**, and **testability**, while maintaining **high code readability** and **MVVM** separation.

---

## 2. Architecture

| Layer | Responsibility |
|:-----|:----------------|
| Presentation Layer | Compose UI Screens, ViewModels managing UI state |
| Domain Layer | Business logic encapsulated in UseCases |
| Data Layer | Repositories abstracting Room database access |
| Data Source | Room DAOs for persistence |

---

## 3. Major Components

### 3.1 Presentation Layer
- **Jetpack Compose** for UI: Fully declarative, with Material3 design system.
- **Single Activity** architecture using `Compose Navigation`.
- **ViewModel** (AndroidViewModel) managing screen states via **StateFlow**.
- **Screen Breakdown**:
  - `LobbyScreen` – Manage multiple streaks (max 3)
  - `ActiveStreakScreen` – Track the currently active streak
  - `AchievementArchiveScreen` – View past claimed achievements (Trophy Room)

### 3.2 Domain Layer
- **UseCases** separate business rules from ViewModels:
  - Example: `ResetStreakUseCase`, `GetAchievementsUseCase`, `AddOrReplaceStreakUseCase`, etc.
- Each UseCase **performs one responsibility** and is **unit-testable**.

### 3.3 Data Layer
- **Repository Pattern** decouples ViewModel from DAO/database.
- **Interfaces** (`StreakRepository`, `AchievementRepository`) ensure loose coupling.
- **Implementations** (`StreakRepositoryImpl`, `AchievementRepositoryImpl`) connect to Room DAOs.

### 3.4 Data Source
- **Room Database** with two entities:
  - `UserStreak` (active/past streaks)
  - `ClaimedAchievement` (archived achievements)
- **Migrations** are supported (v1 → v2).

---

## 4. Data Models

| Model | Purpose |
|:------|:--------|
| `UserStreak` | Represents an ongoing or completed streak (habit name, reset clause, timestamps, personal best) |
| `ClaimedAchievement` | Represents a finalized achievement from a streak |

---

## 5. Tech Stack

| Area | Technology |
|:-----|:-----------|
| Language | Kotlin |
| UI | Jetpack Compose, Material3 |
| State Management | StateFlow, ViewModel |
| Database | Room Persistence Library |
| Navigation | Jetpack Compose Navigation |
| Background Tasks | CoroutineScope, ViewModelScope |
| Widget | Glance AppWidget for Home Screen widgets |
| Testing | JUnit5, Coroutine Test Library, Mocked Repositories |

---

## 6. Dependency Overview

- `Room`
- `Compose BOM`
- `Glance (for Widgets)`
- `Kotlinx.coroutines`
- `ViewModel / LiveData / Lifecycle-runtime`
- `JUnit / CoroutineTest`
- (Optional Future) `WorkManager` for scheduled updates

---

## 7. Special Features

| Feature | Description |
|:--------|:------------|
| Live Timer | Updates every second with a clean ticker using CoroutineScope |
| Personal Best Tracking | Tracks user's longest successful streak |
| Reminder/Reflection Storage | Reminds users of why they started before reset |
| Trophy Archive | Reward center with all earned achievements |
| Offline First | No network permissions required, 100% offline safe |
| Dynamic Widget | Streak timer available directly on the home screen |

---

## 8. Testing Strategy

| Target | Testing Approach |
|:-------|:-----------------|
| ViewModels | Unit tests with Fake Repositories |
| UseCases | Pure Unit tests |
| Widgets | Snapshot testing (future work) |
| DAO | Room Instrumentation tests (future work) |

- Mocked Repositories available under `testdata/`
- Clean separation allows easy fake injection without touching production code.
- Coroutine Dispatchers are managed using `runTest` during unit tests.

---

## 9. Future Improvements

- Move Widget updates into `WorkManager` for periodic refresh instead of manual updates.
- Expand Trophy Room (categories: Habit, Focus, Health)
- Allow users to archive more than 3 streaks
- Dark Mode support for widgets
- In-app achievements (not just post-streak)

---

## 10. Project Philosophy

- **Minimalism**: Only necessary features, no bloat.
- **Clarity First**: Code should be easy for future developers to maintain and extend.
- **Offline First**: Prioritize local storage and privacy.
- **Scalable Design**: Architecture capable of adding remote sync or advanced features without rewriting the core.

---

# Notes for Developers

- Follow the UseCase -> Repository -> DAO flow for any new features.
- Prefer `StateFlow` over `LiveData` unless explicitly necessary.
- Avoid direct DAO access inside the ViewModel or UI layer.
- Keep screen-specific states inside the ViewModel, and global app states separated if scaling bigger.
- Follow clean commit messages and PR standards.

---
