This is a Kotlin Multiplatform project targeting Android, iOS.

* `/shared` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/ios` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* Check that KMP is setup and ready:
    ```bash
    kotlin-app git:(main) ✗ kdoctor -v
    Environment diagnose:
    [✓] Operation System
      ➤ Version OS: macOS 14.2
        CPU: Apple M1 Pro
    
    [✓] Java
      ➤ Java (openjdk version "21.0.1" 2023-10-17)
    
    [✓] Android Studio
      ➤ Android Studio (AI-231.9392.1.2311.11076708)
        Location: /Applications/Android Studio.app
        Bundled Java: openjdk 17.0.7 2023-04-18
        Kotlin Plugin: 231-1.9.10-release-459-AS9392.1.2311.11076708
        Kotlin Multiplatform Mobile Plugin: 0.8.1(231)-23
    
    [✓] Xcode
      ➤ Xcode (15.1)
        Location: /Applications/Xcode.app
    
    [✓] CocoaPods
      ➤ ruby (ruby 3.2.2 (2023-03-30 revision e51014f9c0) [arm64-darwin23])
      ➤ ruby gems (3.4.22)
      ➤ cocoapods (1.14.3)
    
    Recommendations:
      ➤ IDE doesn't suggest running all tests in file if it contains more than one class
        More details: https://youtrack.jetbrains.com/issue/KTIJ-22078
    Conclusion:
      ✓ Your operation system is ready for Kotlin Multiplatform Mobile Development!
    ```

* Added the required values in the local.properties:
    ```
    sdk.dir=~/Library/Android/sdk

    api.url=https://flux.ngrok.dev/api/
    api.key=4afc8dc72bb6aa12066fa8b1eba5f6ff2c30b2fc
    ```

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
