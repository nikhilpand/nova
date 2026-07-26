# 02 — NOVA Software Architecture & Tech Stack

> **Version:** 1.0  
> **Codename:** NOVA  
> **Classification:** Technical Architecture Specification

---

## 1. Multi-Module Topology

NOVA is structured into strict, decoupled feature and core modules to guarantee clean separation of concerns, fast incremental build times, and parallel development:

```
NOVA/
├── docs/             # Complete system blueprint specifications (01 - 08)
├── app/              # Main Android Application module (Hilt entrypoint, Navigation graph)
├── core/             # Base utilities, extension functions, common Dispatchers
├── designsystem/     # Design tokens, Material 3 Expressive, Liquid Glass shaders, Motion
├── domain/           # Pure Kotlin Business Logic, Models, UseCases, Repository Interfaces
├── data/             # Repository Implementations, Offline Sync, Cache Coordinators
├── network/          # Ktor HTTP client, WebSocket real-time engine, gRPC stubs
├── database/         # Room Database entities, DAOs, SQLCipher E2EE encryption wrappers
├── auth/             # Onboarding, Passkeys, OAuth, Biometric Auth manager
├── messaging/        # Chat list, Chat detail, Message composer, Message actions UI
├── media/            # Image viewer, Media3 ExoPlayer, CameraX, PdfRenderer, Audio recorder
├── calls/            # WebRTC engine, Spatial Audio, Group call UI, Screen sharing
├── ai/               # On-device ML Kit OCR, Smart Reply generator, Thread summarizer
├── notifications/    # High-priority push notification service, background sync
├── settings/         # Theme switcher, E2EE safety code audit, performance benchmarks
└── benchmark/        # Macrobenchmark & Microbenchmark modules for 120 FPS / startup testing
```

---

## 2. Frozen Tech Stack Decision

### Frontend (Android Native)
- **Language:** Kotlin 2.x (Strict Coroutines + Flow)
- **UI Framework:** Jetpack Compose + Compose Navigation 3
- **Design System:** Material 3 Expressive + Custom Liquid Glass & Glassmorphism Shaders
- **Dependency Injection:** Hilt / Dagger
- **Local Database:** Room (Encrypted with SQLCipher)
- **Image & Media Engine:** Coil (Image loading) + Media3 ExoPlayer (Audio/Video streaming)
- **Camera & Scanning:** CameraX + ZXing QR + ML Kit On-Device Text Recognition
- **E2EE Crypto:** `libsignal-client` (Signal Protocol) + Android KeyStore
- **Calls & Streaming:** WebRTC Native SDK

### Backend (Microservice & Real-Time Platform)
- **Application Server:** Kotlin + Ktor (Asynchronous I/O Engine)
- **Relational Database:** PostgreSQL (Transactional User/Chat Metadata)
- **In-Memory Cache & Pub/Sub:** Redis (Active Sockets, Rate Limiting, Session Storage)
- **Real-Time Transport:** WebSockets + gRPC (Low-latency bidirectional streaming)
- **Object Storage:** MinIO / S3 (Encrypted media attachments & avatars)
- **Containerization:** Docker & Docker Compose

---

## 3. Layer Separation & Data Flow Rules

NOVA enforces strict Unidirectional Data Flow (UDF) across three distinct layers:

```
┌────────────────────────────────────────────────────────┐
│                   UI LAYER (Compose)                   │
│   Composable Screens ◄── StateFlow ── ViewModels      │
└───────────────────────────┬────────────────────────────┘
                            │ Invokes UseCases
                            ▼
┌────────────────────────────────────────────────────────┐
│                   DOMAIN LAYER (Pure)                  │
│       UseCases / Interactors ◄── Data Models          │
└───────────────────────────┬────────────────────────────┘
                            │ Accesses Repositories
                            ▼
┌────────────────────────────────────────────────────────┐
│                   DATA LAYER (Sync)                    │
│   Room Database (SQLCipher)  ◄►  Ktor / WebSockets / S3 │
└────────────────────────────────────────────────────────┘
```

1. **UI Layer:** Components observe immutable `StateFlow` from ViewModels. User actions are passed as events (`UiEvent`).
2. **Domain Layer:** Pure Kotlin code (no Android framework dependencies). Contains business validation, E2EE key exchange logic, and domain models.
3. **Data Layer:** Single source of truth. Manages database caching, network synchronization, and encryption before persisting.

---

## 4. Security & Encryption Architecture

```
User A Message ──► [Signal Double-Ratchet Engine] ──► [AES-256-GCM Payload]
                                                          │ (Network Transport)
                                                          ▼
User B Message ◄── [Signal Double-Ratchet Engine] ◄── [Decrypt Payload]
```

- **Signal Protocol:** Every private and secret group chat establishes a Double Ratchet session using X3DH key agreement.
- **Hardware Protection:** Session keys are protected inside the Android KeyStore (`AES/GCM/NoPadding`).
- **Encrypted Local Storage:** Room database files are encrypted using 256-bit SQLCipher keys derived from KeyStore secrets.
- **Biometric App Lock:** Fingerprint and Face Authentication gate app entry, wiping transient memory on failed attempts.
