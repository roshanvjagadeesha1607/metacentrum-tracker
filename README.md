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

## Download and install on Android

**[Download MetaCentrum Tracker v1.0 Beta](https://github.com/roshanvjagadeesha1607/metacentrum-tracker/releases/tag/v1.0.0-beta)**

1. Open the release link above.
2. Under **Assets**, download `MetaCentrum_Tracker_v1.0-beta.apk`.
3. Open the downloaded APK on your Android phone.
4. Allow installation from your browser or Files app if Android requests permission.
5. Tap **Install**, then **Open**.

Requires Android 7.0 or newer. Android Studio and ADB are not required.

## How to use the application

1. Open **MetaCentrum Tracker**.
2. Select **SETTINGS**.
3. Set the SSH hostname to `tarkil.metacentrum.cz`.
   The APK's original default, `ssh.metacentrum.cz`, may not resolve.
4. Enter your own authorized MetaCentrum username.
5. Choose password or SSH private-key authentication.
6. Configure the walltime warning threshold.
7. Tap **SAVE AND CONTINUE**.
8. View your running, queued, and completed PBS jobs.
9. Enable background monitoring and Android notifications if needed.

If Tarkil is unavailable, try another official MetaCentrum
frontend, such as `skirit.metacentrum.cz`.

## Tested functionality

The beta APK was successfully installed and opened on
another Android phone. SSH login and PBS job retrieval were
also successfully demonstrated.

Background notifications and compatibility across different
Android devices have not yet been fully verified.

## Important security limitations

This beta retains the security limitations of the original app:

- SSH server host-key verification is disabled.
- Saved passwords do not have application-level encryption.
- The application has not undergone a security audit.

This version is intended for testing and evaluation.
It is not recommended for production credentials until
these issues are corrected.

The project is unofficial and is not affiliated with
or endorsed by MetaCentrum or CESNET.

## Source code

The `installed-version-reference/` folder contains recovered
Java code from the installed application. It is a technical
reference, not a complete buildable Android Studio project.
