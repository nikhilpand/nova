# 05 — NOVA Backend API & WebSocket Contracts

> **Version:** 1.0  
> **Codename:** NOVA  
> **Classification:** API & Real-time Protocol Specification

---

## 1. Protocol Architecture

NOVA backend communicates via two channels:
1. **REST / HTTP Gateway (Ktor):** Transactional requests (Auth, Profile updates, Media upload URLs, Historical queries).
2. **WebSocket & gRPC Engine (Ktor + Netty):** Real-time bidirectional event pipeline (Messages, Typing indicators, Online presence, WebRTC call signaling).

---

## 2. REST Endpoint Specifications

### A. Authentication & Identity
- `POST /api/v1/auth/passkey/register` — Begin WebAuthn Passkey registration.
- `POST /api/v1/auth/passkey/verify` — Verify signed challenge and return JWT session token.
- `POST /api/v1/auth/logout` — Revoke session token and purge push registration.

### B. User Profile & Discovery
- `GET /api/v1/users/me` — Get authenticated user details & identity keys.
- `PUT /api/v1/users/profile` — Update display name, bio, pronouns, accent color.
- `GET /api/v1/users/search?q={query}` — Search users by username handle or QR token.

### C. Messaging & Conversations
- `GET /api/v1/chats` — Fetch user conversation list with unread counters.
- `GET /api/v1/chats/{chatId}/messages?before={cursor}&limit=50` — Fetch encrypted message history.
- `POST /api/v1/chats/{chatId}/messages` — Post a new message payload.
- `DELETE /api/v1/chats/{chatId}/messages/{messageId}` — Delete message for everyone.

### D. Media & AI Services
- `POST /api/v1/media/upload-url` — Request presigned MinIO/S3 upload URL for encrypted media.
- `POST /api/v1/ai/summarize` — Request server-side fallback summarization of message IDs.

---

## 3. Real-Time WebSocket Protocols

**Endpoint:** `wss://api.nova.app/v1/ws?token={jwt}`

### Client-to-Server Event Frame (JSON)
```json
{
  "event": "SEND_MESSAGE",
  "payload": {
    "conversationId": "conv_89124",
    "encryptedContent": "Base64PayloadString...",
    "iv": "Base64IvString...",
    "type": "TEXT",
    "timestamp": 1721749200000
  }
}
```

### Server-to-Client Broadcast Frame (JSON)
```json
{
  "event": "NEW_MESSAGE",
  "payload": {
    "id": "msg_99210",
    "conversationId": "conv_89124",
    "senderId": "usr_7712",
    "senderName": "Sarah Connor",
    "encryptedContent": "Base64PayloadString...",
    "iv": "Base64IvString...",
    "timestamp": 1721749200050,
    "status": "DELIVERED"
  }
}
```

### Typing Indicator Event Frame
```json
{
  "event": "TYPING_STATUS",
  "payload": {
    "conversationId": "conv_89124",
    "userId": "usr_7712",
    "isTyping": true
  }
}
```

---

## 4. WebRTC Signaling Protocol

- `WEBRTC_OFFER` — Transmit SDP offer for 1-on-1 call initiation.
- `WEBRTC_ANSWER` — Transmit SDP answer response.
- `WEBRTC_ICE_CANDIDATE` — Exchange ICE candidate network routes.
