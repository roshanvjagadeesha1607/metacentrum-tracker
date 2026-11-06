# MetaCentrum Tracker (Android)

> **Project status:** Recovered Android source snapshot from July 23, 2026.
> The current source has not been independently rebuilt or verified
> against the latest installed application.
> SSH host-key verification is disabled in this version.
> Do not use with sensitive production SSH credentials until corrected.


A native Android app that connects to MetaCentrum over SSH (using your existing
passwordless SSH key) and shows your job status: Running / Queued / Completed —
with a full-screen ringing alarm when a running job's walltime is about to run
out, and a notification when a job finishes.

No web/Kerberos login is used — it authenticates the same way your terminal
SSH session does, using your private key.

---

## 1. How to open and build this project

1. Install **Android Studio** (free): https://developer.android.com/studio
2. Open Android Studio -> **File > Open** -> select this folder
   (the one containing `settings.gradle`, `build.gradle`, `app/`, etc.)
3. Android Studio will sync Gradle automatically. If it shows a banner about
   the Gradle wrapper, click through it — it will use its own bundled Gradle,
   which is fine.
4. If it complains about the launcher icon resource, that's cosmetic:
   right-click `app` in the project tree -> **New > Image Asset** -> generate
   any icon -> then change `android:icon` in
   `app/src/main/AndroidManifest.xml` to `@mipmap/ic_launcher`.
5. Once Gradle sync finishes, click the green **Run ▶** button with a phone
   plugged in (USB debugging enabled), OR build a standalone file via:
   **Build > Build App Bundle(s) / APK(s) > Build APK(s)**
6. When it finishes, click the "locate" link in the notification, or find the
   file at: `app/build/outputs/apk/debug/app-debug.apk`
7. Copy that `.apk` to your phone (email, cloud drive, USB cable — any way you
   like) and tap it to install. You may need to allow "install unknown apps"
   for whatever app you used to open the file.

If a dependency version fails to resolve (rare, but library versions do get
pulled/renamed over time), the two most likely ones to need a bump are in
`app/build.gradle`:
- `com.github.mwiede:jsch:0.2.17` — check
  https://mvnrepository.com/artifact/com.github.mwiede/jsch for the latest
  version number if this one fails.
- `androidx.security:security-crypto:1.1.0-alpha06` — check
  https://mvnrepository.com/artifact/androidx.security/security-crypto

## 2. First run / configuring the app

On first launch it'll ask for:
- **Host** — leave as `ssh.metacentrum.cz` unless you use a specific frontend.
- **Username** — your MetaCentrum username.
- **SSH private key** — paste the full contents of your private key file
  (e.g. `id_ed25519`, starting with `-----BEGIN OPENSSH PRIVATE KEY-----`),
  or tap "Browse for key file..." to pick the file directly from your phone's
  storage. The key is stored encrypted on-device (EncryptedSharedPreferences)
  and never leaves your phone except to authenticate the SSH connection.
- **Alarm threshold (minutes)** — how much walltime remaining should trigger
  the ringing alarm (default 60).

## 3. What it does

- Shows three sections: **Running**, **Queued**, **Completed**, each job with
  its queue name and walltime used/requested.
- Pull down to refresh manually any time.
- Checks automatically every 15 minutes in the background (the minimum
  interval Android's WorkManager allows for periodic work — fine for
  multi-hour/day jobs).
- When a **running** job's remaining walltime drops below your threshold, it
  triggers a full-screen ringing alarm (like a phone alarm clock) until you
  tap Dismiss.
- When a job's state becomes `C` (completed) or it disappears entirely from
  `qstat` (already purged by PBS), you get a normal notification that it
  finished.

## 4. Notes / limitations

- Android's background check interval can't go below 15 minutes without
  turning this into a persistent foreground service (possible as a future
  upgrade if you want tighter timing).
- Host key verification is disabled for simplicity (`StrictHostKeyChecking=no`).
  This is common for personal tools but means no protection against a
  man-in-the-middle on the SSH connection itself.
- Uses `qstat -f -u <username>` under the hood — if MetaCentrum ever changes
  its PBS scheduler output format, the parser in `JobParser.kt` may need
  small tweaks.
