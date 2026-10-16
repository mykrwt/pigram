# Pigram engineering audit

## Repository shape

Pigram is an Android patch project, not a standalone social app. The Gradle root uses the Morphe patching plugin (`app.morphe.patches`, 1.3.2). `patches/` contains Kotlin resource/bytecode patches; `extensions/extension/` is a Java Android library bundled into the patched APK. The input is an Instagram APK supplied by the builder. The patch does not own Instagram's navigation framework, feed renderer, account/profile model, messaging service, stories implementation, or app resources.

The build targets the official Android package `com.instagram.android`. The optional Clone patch changes its package and localized app label (default clone label: Pigram). The actual launcher icon and splash screen still come from the supplied Instagram APK; replacing them requires a safe resource replacement facility in the patching pipeline, which is not currently wired. `docs/app_icon.png`, `assets/pigram-wordmark.svg`, and `assets/social-preview.png` are Pigram's repository/marketing identity assets, not proof that a built APK uses the launcher icon.

## Existing capabilities observed in source

- **Feed:** Network blocking is toggleable. A separate patch can change the feed pagination header to Instagram's `following` mode. New installs now keep the feed visible and enable that mode by default; Instagram's server response remains authoritative. The feed is still rendered by Instagram.
- **Distraction controls:** Existing patches hide/block Reels, Explore, suggested units, ads, selected navigation tabs, and other surfaces. These controls are implemented as endpoint/view hooks and may need updates as Instagram changes.
- **Private messages:** There is no Pigram messaging backend or replacement UI. The patch leaves Instagram's existing Direct experience and private-media handling in place. This code review cannot verify login, delivery, requests, reads, reactions, media sends, or safety controls against a live Instagram account. No end-to-end-encryption claim is made.
- **Stories, search, profiles, notifications:** These remain features of the supplied Instagram app, except for existing optional surface-hiding controls. They were not live-device tested here.
- **Settings:** A long-press on Instagram's Home tab opens a custom, code-built dark settings panel. It has runtime block controls, a following-feed toggle, tab visibility, landing-page selection, update settings, and restart/lock flows. The settings panel is not a replacement navigation shell. A Pigram light/system appearance selector and reduced-motion toggle are not implemented.
- **Branding:** The patch's clone option uses the Pigram label and package suffix. The repository includes new Pigram identity artwork. Icon/splash replacement inside the APK is not implemented.

The settings store continues to use the historic `feurstagram_prefs` key intentionally: changing this internal storage name would reset existing users' preferences. The updater also recognizes both the historical `.feurstagram` clone package suffix and the new `.pigram` suffix.

## Baseline and verification

No automated test sources were present in the repository at audit time. This workspace does not have `java`/JDK or an Android SDK installed, and no Instagram APK or Morphe CLI bundle is checked into the repository. Consequently a baseline Gradle/APK build, install/launch, authentication, network/offline behavior, accessibility, and DM send/receive tests cannot be reproduced in this environment.

Available checks for this change are static source review and shell syntax validation. A reproducible device verification needs JDK 21, Android SDK/build-tools, the configured Morphe dependency access, the desired Instagram APK, a connected Android test device/emulator, and a test account. Do not use real credentials in logs or issue attachments.

## Known product limitations

- Feed content, account data, chats, stories and safety controls are supplied by Instagram. Pigram does not create new endpoints or claim to secure/retain service behavior beyond the hooks visible in this repository.
- The following-feed mode depends on Instagram accepting the rewritten request header and cannot promise a strictly chronological feed.
- Current branding can change the clone label and settings surfaces; it cannot yet replace the launcher icon, splash screen, or stock Instagram UI throughout the APK.
- The custom settings surface is dark-only. Light/system theme support would require a tested palette adaptation across settings, onboarding and update dialogs.
- No in-app end-of-feed marker, autoplay preference, quiet-hours scheduler, notification-category controls, or new conversation-list UI exists in this patch architecture.
