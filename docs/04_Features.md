# 04 — NOVA Detailed Feature Specifications

> **Version:** 1.0  
> **Codename:** NOVA  
> **Classification:** Comprehensive Feature Specification

---

## 1. Module Breakdown & Matrix

NOVA features are decomposed into 6 primary functional domain modules:

```
                  ┌───────────────────────────────────────────┐
                  │            NOVA FEATURE ENGINE            │
                  └─────────────────────┬─────────────────────┘
                                        │
        ┌──────────────┬────────────────┼──────────────┬──────────────┐
        ▼              ▼                ▼              ▼              ▼
   [Foundation]   [Messaging]        [Media]        [Calls]       [AI Studio]
```

---

## 2. Feature Specifications

### Module 1: Foundation (`:auth`, `:settings`, `:designsystem`)
- **Splash & Onboarding:** Passwordless onboarding via WebAuthn Passkeys, Google OAuth, GitHub, or Biometrics. Zero mandatory phone numbers.
- **Identity System:** Unique Username handle, Display Name, Custom Banner, Avatar, Bio, Pronouns, Accent Color Picker, Badges (Verified, Supporter, E2EE Master).
- **Discovery Engine:** User lookup via Username, QR Code generation/scanning (ZXing), Invite Link, and Friend Code.
- **Theme Builder Engine:** Live preset switching between AMOLED Dark, Slate Dark, Light, Liquid Glass, Cyberpunk Neon, and Nature Forest with Material 3 Expressive Dynamic Colors.

### Module 2: Core Messaging (`:messaging`)
- **Chat Directory & Folders:** Folder categories (All, Personal, Work, Communities, Secret), Pinned Chats, Unread Count Badges, Swipe Actions (Pin, Archive, Mute, Delete), Global Search.
- **Message Types:**
  - `Text & Markdown:` Plain text, bold/italic, lists, blockquotes.
  - `Code Snippets:` Syntax-highlighted code blocks with copy-to-clipboard and execution simulation.
  - `Interactive Polls & Quizzes:` Single/multi-choice voting with real-time percentage animation.
  - `Voice Notes:` Waveform visualizer playback with 1.5x / 2.0x speed toggles.
  - `Disappearing Messages:` Self-destruct timers (5s, 1m, 1h, 1d) with screenshot prevention.
  - `Secret Chats:` Signal Protocol Double-Ratchet channels stored exclusively in local SQLCipher DB.
- **Message Actions Menu:** Reply, Forward, Quote, Edit (with version history log), Delete (Undo grace period), Pin, Bookmark, Translate, Summarize, Reminders.

### Module 3: Media Engine (`:media`)
- **Lossless Photos & HD Video:** Original quality preservation with optional smart compression.
- **Coil Image Loader:** Fast memory caching, progressive loading, and glassmorphism fallback.
- **Media3 ExoPlayer:** Custom video player with picture-in-picture, playback speed control, and gesture brightness/volume adjustments.
- **CameraX Integration:** In-app photo & video capture with flash controls and lens switching.
- **Document Viewer:** Built-in Android `PdfRenderer` for previewing PDF files without third-party apps.

### Module 4: Calls & Real-Time Engine (`:calls`)
- **WebRTC Audio/Video Calls:** Peer-to-peer 1-on-1 and group voice/video calls.
- **Spatial Audio & Noise Reduction:** 48kHz HD audio with background noise suppression.
- **Screen Sharing & Recording:** Low-latency display streaming for community presentations.

### Module 5: On-Device AI Studio (`:ai`)
- **Smart Reply Engine:** On-device contextual response chip suggestions.
- **Grammar & Tone Rewriter:** Professional, Concise, Creative, and Pirate tone options.
- **Thread Summarizer:** Condensed bulleted summaries of long chat threads.
- **On-Device ML Kit OCR:** Image text extraction into editable text notes.
- **Task Extractor:** Automatic parsing of action items from conversation messages into task checklists.
