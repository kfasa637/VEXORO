# VEXORA

An Android floating-menu shell prototype. It is not a Minecraft client modification and does not directly alter Minecraft gameplay or guarantee higher FPS.

## Build APK without a PC (GitHub Actions)

1. On your phone, sign in to GitHub and create a new repository named `VEXORA`.
2. Upload the contents of this folder to the repository (make sure `.github/workflows/android.yml` is included).
3. Open the repository's **Actions** tab and allow workflows if GitHub asks.
4. Choose **Build VEXORA APK** → **Run workflow**.
5. When the run finishes successfully, open that run and download the `VEXORA-debug-apk` artifact.
6. Extract the downloaded artifact ZIP on your phone. It contains `app-debug.apk`.

This is an unsigned debug build for testing. Android may ask you to allow installation from the browser or file manager you used. Only install APKs you built or trust.
