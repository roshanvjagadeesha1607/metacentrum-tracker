# MetaCentrum Tracker

An independent Android application developed to monitor
MetaCentrum HPC jobs using SSH.

## Features

- SSH connection to MetaCentrum
- Password and private-key authentication
- Running, queued, and completed job monitoring
- PBS job information and walltime tracking
- Job completion notifications
- Walltime alerts

## Repository contents

The `installed-version-reference/` directory contains
decompiled Java code recovered from the developer's
working Android application.

This is a code reference, not the original Android Studio
source project. It has not been independently rebuilt
or verified as a complete buildable project.

## Security

No personal login credentials, SSH private keys, or
saved application settings are intentionally included.

The recovered application has known security limitations
documented in SECURITY.md.

## Disclaimer

This is an independent, unofficial project.
It is not developed, endorsed, or maintained by
MetaCentrum or CESNET.

## Installation

MetaCentrum Tracker is currently published as a source-code
reference. A public, installable APK has not yet been released.

The recovered installed version is an Android development build.
It requires additional security fixes and release testing before
distribution.

Once an installable version is available, it will be published
under GitHub Releases with installation instructions.

## How to use MetaCentrum Tracker

The application is intended for users with an authorized
MetaCentrum account.

1. Open MetaCentrum Tracker on your Android phone.
2. Enter the MetaCentrum SSH host assigned to you.
3. Enter your own MetaCentrum username.
4. Select password or SSH private-key authentication.
5. Configure the walltime alarm threshold in minutes.
6. Tap "Save and Continue".
7. View your running, queued, and completed PBS jobs.
8. Refresh the job list when needed.
9. Enable Android notifications to receive supported job alerts.

Never share your password or private key with anyone,
or commit credentials to GitHub.

## Important security notice

The recovered development version does not verify SSH server
host keys and does not provide application-level encryption
for the stored password.

It is not recommended for general distribution or
production password authentication until these issues
have been corrected.

## Project status

This repository documents a working personal Android application.

The recovered Java files are provided for technical reference.
They are not a complete, independently verified buildable
Android Studio project.

A public release APK is not yet available.
