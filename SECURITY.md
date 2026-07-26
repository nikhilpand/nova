# NOVA Security Policy

## Supported Versions

| Version | Supported |
|:---|:---:|
| v0.x-alpha | ✅ (Active Development) |
| < v0.1 | ❌ |

## Reporting a Vulnerability

If you discover a security vulnerability in NOVA, please report it responsibly:

1. **Do NOT** open a public GitHub issue.
2. Use [GitHub Security Advisories](https://github.com/nova-app/nova/security/advisories) to report privately.
3. Include a description, steps to reproduce, and potential impact.
4. We will acknowledge your report within **48 hours** and aim to resolve critical issues within **7 days**.

## Security Architecture

| Layer | Implementation |
|:---|:---|
| Message Encryption | Signal Protocol Double Ratchet |
| Local Database | SQLCipher AES-256 encrypted Room DB |
| Key Storage | Android Hardware KeyStore (RSA/AES-GCM) |
| Authentication | WebAuthn Passkeys + Biometric |
| Session Management | Automatic rotation with Perfect Forward Secrecy |
| Multi-Device Keys | Per-device identity keys with cross-device verification |

## Threat Model

NOVA is designed to protect against:
- **Passive eavesdropping** — All messages are E2EE by default.
- **Server compromise** — The server never holds plaintext messages or private keys.
- **Device theft** — Local data is encrypted with SQLCipher; biometric unlock required.
- **Replay attacks** — Signal Protocol ratchets ensure each message key is unique.

NOVA does **not** currently protect against:
- Compromised device OS (rooted/jailbroken devices)
- Side-channel attacks on the device hardware
- Screen capture by malicious apps with accessibility permissions
