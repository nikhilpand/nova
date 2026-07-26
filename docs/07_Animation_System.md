# 07 — NOVA Motion & Animation System

> **Version:** 1.0  
> **Codename:** NOVA  
> **Classification:** Motion Physics & Transition Specification

---

## 1. Motion Philosophy

Consistency in motion physics is what transforms a functional interface into a premium product. In NOVA:
- **No static UI state changes:** Every item insertion, deletion, tab switch, and dialog display must animate.
- **Physics over fixed curves:** We prioritize spring physics over linear tweens for natural, tactile feedback.
- **Fluid 120 FPS targets:** Animations are calculated on the UI thread without triggering unnecessary Compose recompositions.

---

## 2. Motion Duration & Curve Standards

| Preset Name | Duration | Easing Curve / Physics | Use Case |
|:---|:---|:---|:---|
| **Fast** | `120ms` | `FastOutSlowInEasing` | Button presses, micro-interactions, badge updates |
| **Normal** | `220ms` | `EmphasizedEasing (0.2, 0.0, 0.0, 1.0)` | Card entry, dropdown menus, filter chips |
| **Large** | `350ms` | `Spring(MediumBouncy, LowStiffness)` | Screen transitions, bottom sheet expand |
| **Hero** | `500ms` | `ExpressiveEasing (0.34, 1.56, 0.64, 1.0)` | Shared element morphing, theme switcher |

---

## 3. Transition Rules & Gestures

### A. Swipe-to-Reply Gesture
- **Trigger:** Drag message bubble horizontally past `48dp` threshold.
- **Feedback:** Spring resistance physics with haptic vibration tick on threshold trigger.
- **Action:** Message composer enters reply preview mode with fluid height expansion.

### B. Shared Element Avatar & Image Morphing
- **Trigger:** Tapping a user avatar or chat media image.
- **Motion:** The thumbnail morphs smoothly from its inline list bounds to full-screen view bounds using `Hero (500ms)` expressive spring physics.

### C. Liquid Glass Blur Backdrop Morphing
- **Trigger:** Opening a modal dialog, bottom sheet, or message action context menu.
- **Motion:** Background surfaces blur progressively from `0px` to `32px` while surface alpha scales from `0%` to `15%`.

### D. Elastic Overscroll Bounce
- **Trigger:** Reaching the top or bottom of chat list or conversation list.
- **Physics:** Elastic rubber-band spring dampening preventing harsh wall stops.

### E. Skeleton Shimmer Loading
- **Trigger:** Initial screen load or asynchronous query fetching.
- **Animation:** Linear gradient sweep (`1200ms` repeat duration) animating across placeholder card outlines.
