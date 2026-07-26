# NOVA — Open-Source Next-Gen Android Communication Platform

[![CI/CD Pipeline](https://github.com/nova-app/nova/actions/workflows/ci.yml/badge.svg)](https://github.com/nova-app/nova/actions/workflows/ci.yml)
[![Android](https://img.shields.io/badge/Platform-Android%2014%2B-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.x-purple.svg)](https://kotlinlang.org)
[![Supabase](https://img.shields.io/badge/BaaS-Supabase-emerald.svg)](https://supabase.com)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

> Modern messaging apps are excellent at communication but often compromise on Android-native design, extensibility, or privacy. **NOVA** aims to combine Android-first UI, strong end-to-end encryption, and a modular architecture into a single open platform — proving that a messaging app can be beautiful, private, and extensible at the same time.

---

## 🚧 Project Status

**Stage:** Active Development

| Phase | Status |
|:---|:---:|
| Architecture & Blueprint | ✅ Complete |
| Design System & Motion | ✅ Complete |
| Authentication (Passkeys + OAuth) | ✅ Complete |
| Signal E2EE Crypto Engine | ✅ Complete |
| Messaging Engine | 🚧 In Progress |
| Voice & Video Calls | 🚧 In Progress |
| Media Engine | 🚧 In Progress |
| Communities & Guilds | 🚧 In Progress |
| AI Studio | ⬜ Planned |
| Plugins & Extensions | ⬜ Planned |
| Public Beta | ⬜ Planned |

---

## 🛠️ Tech Stack

| Layer | Technology |
|:---|:---|
| **Language** | Kotlin 2.x |
| **UI Framework** | Jetpack Compose + Material 3 Expressive |
| **Backend-as-a-Service** | Supabase (Auth, PostgreSQL, Realtime, Storage, Edge Functions) |
| **Push Notifications** | Firebase Cloud Messaging (FCM) |
| **Database (Local)** | Room + SQLCipher (AES-256 encrypted) |
| **Database (Cloud)** | PostgreSQL (Supabase-hosted) |
| **Realtime** | Supabase Realtime WebSockets |
| **Encryption** | Signal Protocol (Double Ratchet) + Android KeyStore |
| **Dependency Injection** | Hilt |
| **Image Loading** | Coil 3 |
| **Media Playback** | AndroidX Media3 (ExoPlayer) |
| **Camera** | CameraX |
| **Video/Voice Calls** | WebRTC |
| **Serialization** | Kotlinx Serialization |
| **HTTP Client** | Ktor Client |
| **CI/CD** | GitHub Actions |

---

## 🏗️ Architecture

NOVA uses **Supabase** as the primary cloud backend. An optional **Ktor API Gateway** can be added later for specialized microservices (AI processing, media transcoding).

```
Android Native Application
        │
        ▼
 Supabase (Primary BaaS)
 ├── Authentication (Passkeys / Google / GitHub OAuth)
 ├── PostgreSQL Database
 ├── Realtime WebSockets (Messages, Typing, Presence)
 ├── Storage (Encrypted Media Buckets)
 └── Edge Functions (Serverless Deno Workers)

        +
 Firebase Cloud Messaging (Background Push Alerts)
        +
 WebRTC (Peer-to-Peer Calls via Supabase Realtime Signaling)
```

### Clean Architecture Data Flow

```
     ┌─────────────────────────────────────────────────────────┐
     │                     COMPOSE UI LAYER                    │
     └────────────────────────────┬────────────────────────────┘
                                  ▼
     ┌─────────────────────────────────────────────────────────┐
     │                     VIEWMODEL LAYER                     │
     └────────────────────────────┬────────────────────────────┘
                                  ▼
     ┌─────────────────────────────────────────────────────────┐
     │                     USECASE LAYER                       │
     └────────────────────────────┬────────────────────────────┘
                                  ▼
     ┌─────────────────────────────────────────────────────────┐
     │                    REPOSITORY LAYER                     │
     └──────────────┬───────────────────────────┬──────────────┘
                    ▼                           ▼
     ┌────────────────────────────┐ ┌───────────────────────────┐
     │      REMOTE DATA SOURCE   │ │     LOCAL DATA SOURCE      │
     │      (Supabase Client)    │ │   (Room SQLCipher DB)      │
     └────────────────────────────┘ └───────────────────────────┘
```

---

## ⚖️ Platform Comparison

| Feature | **NOVA** | **Telegram** | **Signal** | **Discord** |
|:---|:---:|:---:|:---:|:---:|
| No Mandatory Phone Number | ✅ | ✅ | ❌ | ✅ |
| Default End-to-End Encryption | ✅ | ❌ | ✅ | ❌ |
| Material 3 Expressive UI | ✅ | ❌ | ❌ | ❌ |
| Glass Shaders & Spring Physics | ✅ | ❌ | ❌ | ❌ |
| On-Device AI Assistant | ✅ | ❌ | ❌ | ❌ |
| Offline-First Queue & Sync | ✅ | ✅ | ✅ | ❌ |
| Extensible Plugin System | ✅ | ❌ | ❌ | ❌ |

---

## 📊 Performance Targets

| Metric | Target |
|:---|:---|
| Cold Launch | `< 500 ms` |
| Warm Launch | `< 250 ms` |
| UI Scroll Framerate | `120 FPS` (on supported devices) |
| Screen Transition Latency | `< 100 ms` |
| Memory Footprint | `< 180 MB` |
| APK Binary Size | `< 45 MB` |
| Offline Queue Sync | `< 300 ms` |

> **Note:** Benchmarks will be published per-device after initial beta testing on Pixel 9 and Samsung S24.

---

## 🛠️ Voice & Video Technical Stack

### Voice Pipeline (`:calls`)
| Component | Technology |
|:---|:---|
| Codec | Opus Fullband (48 kHz sampling) |
| Echo Cancellation | Hardware AEC3 |
| Noise Suppression | Deep Neural Network NS |
| Gain Control | Automatic Gain Control (AGC) |
| Packet Loss | Opus RED redundancy + AI-assisted PLC |
| Adaptive Bitrate | Dynamic rate switching (12–510 kbps) |

### Video Pipeline (`:calls`)
| Component | Technology |
|:---|:---|
| Codec Selection | Dynamic WebRTC negotiation (VP8, VP9, AV1, H.264) |
| Frame Pacing | Scalable Video Coding (SVC): 60 → 30 → 15 FPS |
| Low Bandwidth Fallback | Audio-first mode under < 50 kbps |

---

## 🧩 Plugin & Extension Architecture

NOVA is designed for extensibility from day one:

```
plugins/          # Plugin lifecycle manager & registry
sdk/              # Public NOVA SDK for third-party developers
extensions/       # First-party feature extensions
bot-api/          # Bot & automation webhook API
miniapps/         # In-chat mini-application framework
theme-api/        # Custom theme pack & shader API
```

Supported plugin categories: Mini-Apps, Bots, AI Tools, Theme Packs, Sticker Packs, Automation Scripts.

---

## 📋 Release Roadmap

| Version | Milestone | Status |
|:---|:---|:---:|
| `v0.1-alpha` | Authentication & Design System | ✅ |
| `v0.2-alpha` | Messaging Engine & Offline Sync | 🚧 |
| `v0.3-alpha` | Media (Photos, Videos, Voice Notes) | ⬜ |
| `v0.4-alpha` | Voice & Video Calls (WebRTC) | ⬜ |
| `v0.5-alpha` | Communities & Guilds | ⬜ |
| `v0.6-alpha` | On-Device AI Studio | ⬜ |
| `v0.7-alpha` | Plugin System & SDK | ⬜ |
| `v0.8-beta` | Performance Optimization & Benchmarking | ⬜ |
| `v0.9-beta` | Security Audit & Accessibility | ⬜ |
| `v1.0` | Public Release | ⬜ |

---

## 🔒 Security

NOVA takes security seriously at every layer:

| Layer | Implementation |
|:---|:---|
| **Message Encryption** | Signal Protocol Double Ratchet (per-message keys) |
| **Local Database** | SQLCipher AES-256 encrypted Room database |
| **Key Storage** | Android Hardware KeyStore (RSA/AES-GCM) |
| **Authentication** | WebAuthn Passkeys + Biometric (no passwords) |
| **Session Management** | Automatic session rotation with Perfect Forward Secrecy |
| **Multi-Device Keys** | Per-device identity keys with cross-device verification |

### Responsible Disclosure

If you discover a security vulnerability, please report it privately via [Security Advisories](https://github.com/nova-app/nova/security/advisories) rather than opening a public issue.

---

## 📂 Repository Layout

```
NOVA/
├── .github/workflows/   # CI/CD (Build → Lint → Test → APK)
├── android/
│   ├── app/             # Main Application Module (Compose UI & Navigation)
│   ├── core/            # Core Kotlin Base Utilities
│   ├── domain/          # Pure Domain Models & UseCases
│   ├── data/            # Repository Implementations
│   ├── network/         # Supabase Client Manager
│   ├── database/        # Room SQLCipher Encrypted Local Storage
│   ├── auth/            # Passkeys, Biometrics, GitHub OAuth
│   ├── messaging/       # Chat Engine & Offline Message Queue
│   ├── media/           # Coil, Media3 ExoPlayer, CameraX
│   ├── calls/           # WebRTC Dynamic Codec Engine
│   ├── ai/              # On-Device ML Kit OCR & AI Assistant
│   ├── notifications/   # FCM Push Service & Quiet Hours
│   ├── settings/        # Preferences & Theme Studio
│   ├── designsystem/    # M3 Expressive Tokens & Glass Shaders
│   ├── communities/     # Guild & Channel Manager
│   └── benchmark/       # Macrobenchmark Startup & Frame Auditor
├── plugins/             # Plugin Lifecycle Manager & Registry
├── sdk/                 # Public NOVA SDK for Extensions
├── extensions/          # First-party Feature Extensions
├── bot-api/             # Bot & Automation Webhook API
├── miniapps/            # In-Chat Mini-App Framework
├── theme-api/           # Custom Theme Pack & Shader API
├── backend/             # Optional Ktor Microservice Server
├── docs/                # 8-Part Architecture Blueprint Suite
├── scripts/             # Build & Utility Scripts
├── design/              # Design Assets & Figma References
└── assets/              # Static Assets & Icons
```

---

## 🚀 Quick Start

```bash
# Clone
git clone https://github.com/nova-app/nova.git
cd nova

# Build
./gradlew assembleDebug

# Run Tests
./gradlew testDebugUnitTest

# Lint
./gradlew lint
```

---

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Clone** your fork locally
3. **Create a branch** from `develop` (`git checkout -b feature/your-feature`)
4. **Commit** using [Conventional Commits](https://www.conventionalcommits.org/) (`feat:`, `fix:`, `docs:`, `refactor:`)
5. **Push** your branch and open a **Pull Request** against `develop`

### Code Style
- Kotlin code follows [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Compose functions use `PascalCase`
- All public APIs must include KDoc comments
- UI components must use design system tokens from `:designsystem`

---

## 💡 Inspiration

NOVA draws inspiration from the best in modern messaging and communication:

- [Signal](https://signal.org) — End-to-end encryption & privacy-first design
- [Telegram](https://telegram.org) — Speed, offline sync, and cloud architecture
- [Discord](https://discord.com) — Communities, channels, and roles
- [Element X](https://element.io) — Matrix protocol & decentralized messaging
- [SimpleX Chat](https://simplex.chat) — No user identifiers & metadata privacy
- [Session](https://getsession.org) — Onion routing & anonymous messaging
- [Material Design 3](https://m3.material.io) — Expressive, adaptive, and dynamic UI system

---

## 📄 License

This project is licensed under the Apache License 2.0 — see the [LICENSE](LICENSE) file for details.

---

## ❓ FAQ

**Q: Does NOVA require a phone number?**
No. NOVA uses WebAuthn Passkeys and OAuth (Google, GitHub) for authentication. No phone number is ever required.

**Q: Is encryption enabled by default?**
Yes. All messages are encrypted using the Signal Protocol Double Ratchet. Local data is stored in a SQLCipher-encrypted database.

**Q: What devices are supported?**
Android 14+ (API 34+) with Jetpack Compose and Material 3 Expressive support.

**Q: Can I build plugins for NOVA?**
Yes. NOVA has a built-in plugin architecture supporting Mini-Apps, Bots, AI Tools, Theme Packs, and Automation Scripts.
