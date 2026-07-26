# 03 — NOVA Design System & Visual Tokens

> **Version:** 1.0  
> **Codename:** NOVA  
> **Classification:** Visual Design System Specification

---

## 1. Design Token Architecture

The NOVA Design System (`:designsystem`) defines immutable design tokens that govern every visual element across all feature modules. No ad-hoc colors, hardcoded paddings, or arbitrary text sizes are permitted.

---

## 2. Color Palettes & Theme Presets

### A. AMOLED Dark (Power Saving Default)
- `Primary:` `#7C4DFF` (Deep Purple Accent)
- `PrimaryContainer:` `#311B92`
- `Background:` `#000000` (Pure Black)
- `Surface:` `#121212` (Elevated Surface 1)
- `SurfaceVariant:` `#1E1E1E` (Elevated Surface 2)
- `Secondary:` `#00E676` (Emerald Accent)

### B. Standard Dark (Slate Blue)
- `Primary:` `#6366F1` (Indigo Accent)
- `PrimaryContainer:` `#3730A3`
- `Background:` `#0F172A` (Slate 900)
- `Surface:` `#1E293B` (Slate 800)
- `SurfaceVariant:` `#334155` (Slate 700)
- `Secondary:` `#10B981` (Teal Accent)

### C. Liquid Glass (Glassmorphic Transparent)
- `Primary:` `#8B5CF6` (Violet Accent)
- `SurfaceGradient:` `LinearGradient(White 15%, White 5%)`
- `BorderBrush:` `LinearGradient(White 30%, Primary 15%)`
- `GlowSpot:` `#7C4DFF` (30% Alpha Shadow)

### D. Cyberpunk Neon
- `Primary:` `#FF007F` (Neon Pink)
- `Secondary:` `#00F0FF` (Cyan Glow)
- `Background:` `#0A0014` (Deep Magenta Night)
- `Surface:` `#1D0033`

### E. Nature Forest
- `Primary:` `#10B981` (Emerald Green)
- `Secondary:` `#F59E0B` (Amber Sun)
- `Background:` `#061A14` (Deep Forest)
- `Surface:` `#0B2E24`

---

## 3. Typography Scale

Built using Material 3 Expressive typography rules:

| Category | Size | Line Height | Weight | Usage |
|:---|:---|:---|:---|:---|
| **Display Large** | 57sp | 64sp | Bold (700) | Hero Headers |
| **Headline Large** | 32sp | 40sp | Bold (700) | Section Titles |
| **Title Medium** | 16sp | 24sp | SemiBold (600) | Chat Item Title |
| **Body Medium** | 14sp | 20sp | Regular (400) | Chat Bubble Text |
| **Label Small** | 11sp | 16sp | Bold (700) | Badges & Timestamps |
| **Monospace Code** | 13sp | 18sp | Medium (500) | Code Snippet Bubbles |

---

## 4. Spacing, Radius & Elevation Tokens

### Spacing (4dp Grid)
- `SpaceXS:` 4dp
- `SpaceSM:` 8dp
- `SpaceMD:` 12dp
- `SpaceLG:` 16dp
- `SpaceXL:` 24dp
- `Space2XL:` 32dp
- `Space3XL:` 48dp

### Border Radius
- `RadiusXS:` 4dp (Code blocks, small badges)
- `RadiusSM:` 8dp (Chips, notification pills)
- `RadiusMD:` 12dp (Buttons, message action menus)
- `RadiusLG:` 16dp (Input fields, chat items)
- `RadiusXL:` 20dp (Cards, dialog containers)
- `Radius2XL:` 28dp (Message composer, bottom bar)
- `RadiusFull:` 999dp (Avatars, FABs, online indicators)

### Blur & Glass Levels
- `BlurSubtle:` 8px (Background card overlay)
- `BlurMedium:` 16px (Navigation bottom bar)
- `BlurHeavy:` 32px (Full-screen modal backdrop)

---

## 5. Component Tokens & Standards

Every screen must assemble UI using standardized `:designsystem` components:
- `NovaTopBar`: Glassmorphic top app bar with E2EE status shield.
- `NovaBottomBar`: Floating liquid glass tab bar.
- `NovaAvatar`: Circle avatar with online status indicator dot.
- `NovaMessageBubble`: Bubble container supporting text, markdown, code blocks, polls, voice.
- `NovaButton`: Filled, Outlined, and Glass variant buttons with haptic feedback.
- `NovaTextField`: Glassmorphism outlined input field with rounded corners.
