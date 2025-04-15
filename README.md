# Since – Habit Breaker Tracker

**Since** is a minimalist Android app that helps users break bad habits by tracking how long they've stayed committed. Whether you're quitting smoking, limiting phone usage, or avoiding alcohol, Since keeps you accountable with a simple, distraction-free interface.

---

## Features

- Behavioral framework to support habit change: Identify the cue, disrupt the pattern, and replace the habit.
- Live timer showing days, hours, minutes, and seconds since your last reset.
- Final reminder: A custom message you write to your future self that appears before you reset.
- Streak history: Keeps the last three streaks for quick reuse.
- Reset confirmation: You must confirm and reread your own reminder before resetting.
- Minimalist UI with a dark theme and clean layout.
- Optional widget support (planned).

---

## Screenshots

*Include screenshots here once available.*

---

## Tech Stack

- Kotlin
- Jetpack Compose
- Room Database
- MVVM Architecture
- Material Design 3

---

## Development Notes

- Uses a local Room database to persist streaks.
- Only one active streak is allowed at a time.
- Streak history is capped at three entries, with older ones replaced.
- The database is the single source of truth.
- UI state is managed through `StateFlow` and `ViewModel`.

---

## Installation

1. Clone the repository.
2. Open in Android Studio.
3. Build and run on a device with minimum SDK 34.

---
