
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