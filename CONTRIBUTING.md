# Contributing to NOVA

Thank you for your interest in contributing to NOVA! This document provides guidelines and instructions for contributing.

## Getting Started

1. **Fork** the repository on GitHub
2. **Clone** your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/nova.git
   cd nova
   ```
3. **Create a branch** from `develop`:
   ```bash
   git checkout -b feature/your-feature-name
   ```

## Development Setup

### Prerequisites
- Android Studio Ladybug (2024.2+) or later
- JDK 17+
- Android SDK 34+

### Build
```bash
./gradlew assembleDebug
```

### Test
```bash
./gradlew testDebugUnitTest
```

## Code Style

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Compose functions use `PascalCase`
- All public APIs must include KDoc comments
- UI components must reference design tokens from `:designsystem` — no hardcoded colors or dimensions
- Keep functions small and focused (< 40 lines preferred)

## Commit Convention

We follow [Conventional Commits](https://www.conventionalcommits.org/):

```
feat: add voice note waveform player
fix: resolve offline queue retry loop
docs: update API specification for /messages endpoint
refactor: extract message parsing into UseCase
test: add unit tests for SignalSessionManager
chore: update Gradle wrapper to 8.5
```

## Pull Request Process

1. Ensure your branch builds without errors: `./gradlew assembleDebug`
2. Ensure all tests pass: `./gradlew testDebugUnitTest`
3. Ensure lint passes: `./gradlew lint`
4. Update documentation if your change affects public APIs
5. Open a PR against the `develop` branch
6. Fill in the PR template with a clear description of your changes

## Architecture Rules

- **No cross-module UI imports.** Feature modules must not import from other feature modules.
- **Domain module is pure Kotlin.** No Android framework dependencies.
- **All network calls go through `:network`.** No direct HTTP calls from UI or domain layers.
- **All database access goes through `:database`.** No direct Room queries from ViewModels.

## Questions?

Open a [Discussion](https://github.com/nova-app/nova/discussions) on GitHub.
