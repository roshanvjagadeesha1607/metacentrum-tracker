# Security

This repository contains an experimental Android application for
monitoring MetaCentrum HPC jobs through SSH.

## Known limitations

- SSH host-key verification is disabled in the recovered source.
  This can permit SSH server impersonation.
- The application supports importing an unencrypted SSH private key.
- The source has not undergone a comprehensive security audit.
- The recovered project has not been independently build-tested.

## Credential handling

The application uses AndroidX EncryptedSharedPreferences to store
configuration values, including the imported SSH private key.

Never commit personal SSH keys, passwords, tokens, or credentials.

## Before production use

Implement verified SSH host-key checking, review credential handling,
and rebuild and test the application.
