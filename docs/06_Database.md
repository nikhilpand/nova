# 06 — NOVA Database Schema & Persistence Architecture

> **Version:** 1.0  
> **Codename:** NOVA  
> **Classification:** Relational & Encrypted Persistence Specification

---

## 1. Local Encrypted Storage (Room + SQLCipher)

On the Android client, all application data is stored in a 256-bit AES SQLCipher encrypted SQLite database managed by Android Room.

### Client Database Entities

```sql
-- Users Entity
CREATE TABLE IF NOT EXISTS users (
    id TEXT PRIMARY KEY NOT NULL,
    username TEXT UNIQUE NOT NULL,
    display_name TEXT NOT NULL,
    avatar_url TEXT DEFAULT '',
    banner_url TEXT DEFAULT '',
    bio TEXT DEFAULT '',
    pronouns TEXT DEFAULT '',
    status_text TEXT DEFAULT 'Online',
    is_online INTEGER NOT NULL DEFAULT 1,
    accent_color_hex TEXT DEFAULT '#6366F1',
    safety_number TEXT NOT NULL
);

-- Conversations Entity
CREATE TABLE IF NOT EXISTS conversations (
    id TEXT PRIMARY KEY NOT NULL,
    title TEXT NOT NULL,
    avatar_url TEXT DEFAULT '',
    last_message TEXT DEFAULT '',
    last_message_time TEXT DEFAULT '',
    unread_count INTEGER NOT NULL DEFAULT 0,
    is_group INTEGER NOT NULL DEFAULT 0,
    is_pinned INTEGER NOT NULL DEFAULT 0,
    is_secret INTEGER NOT NULL DEFAULT 0,
    category TEXT NOT NULL DEFAULT 'ALL'
);

-- Messages Entity
CREATE TABLE IF NOT EXISTS messages (
    id TEXT PRIMARY KEY NOT NULL,
    conversation_id TEXT NOT NULL,
    sender_id TEXT NOT NULL,
    sender_name TEXT NOT NULL,
    sender_avatar TEXT DEFAULT '',
    content TEXT NOT NULL,
    type TEXT NOT NULL DEFAULT 'TEXT',
    timestamp INTEGER NOT NULL,
    status TEXT NOT NULL DEFAULT 'SENT',
    is_e2ee INTEGER NOT NULL DEFAULT 1,
    code_language TEXT,
    is_pinned INTEGER NOT NULL DEFAULT 0,
    is_starred INTEGER NOT NULL DEFAULT 0,
    translation TEXT,
    ai_summary TEXT,
    FOREIGN KEY(conversation_id) REFERENCES conversations(id) ON DELETE CASCADE
);
```

---

## 2. Server-Side Database Architecture (PostgreSQL)

The Ktor backend relies on PostgreSQL 16+ for transactional state and metadata.

```sql
-- Users Table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(32) UNIQUE NOT NULL,
    display_name VARCHAR(64) NOT NULL,
    email VARCHAR(255) UNIQUE,
    public_identity_key BYTEA NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Conversations Table
CREATE TABLE conversations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    is_group BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Messages Table (Pre-encrypted payloads)
CREATE TABLE messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conversation_id UUID REFERENCES conversations(id) ON DELETE CASCADE,
    sender_id UUID REFERENCES users(id) ON DELETE CASCADE,
    ciphertext BYTEA NOT NULL,
    iv BYTEA NOT NULL,
    message_type VARCHAR(24) DEFAULT 'TEXT',
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_messages_conv_time ON messages(conversation_id, created_at DESC);
```

---

## 3. Redis Caching & Pub/Sub Layer

Redis handles high-speed ephemeral operations:
- `user:socket:{userId}` — Active WebSocket server node mapping.
- `chat:typing:{chatId}` — Active typing indicator set with 3-second auto-expiration.
- `ratelimit:{ip}` — Sliding window request counters (60 req/min).
