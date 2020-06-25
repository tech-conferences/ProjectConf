# ProjectConf / ConfX

![Android CI](https://github.com/RotBolt/ProjectConf/workflows/Android%20CI/badge.svg)
[![codecov](https://codecov.io/gh/RotBolt/ProjectConf/branch/master/graph/badge.svg)](https://codecov.io/gh/RotBolt/ProjectConf)


ConfX is unofficial [confs.tech](https://confs.tech/) android companion app

## About
It loads the data from **Confs.tech github repo**. There is no proper api for loading the data, so combination of **RxJava Operators** are used to load and deliver the proper content. It stores the conferences data into local database for offline accessibility as well
- Its offline first  😃. 
- Clean and Simple Material UI.
- It supports dark theme too 🌗.
- Set Alerts for tech/topic interested, and it will notify :bell: for new conferences
- CFP Reminders - :bellhop_bell: Notify to remind about about Call for proposals
- Bookmark interested conferences :bookmark:
- Directly add the event to calendar :calendar:

***Install and test beta ConfX app from below 👇***

[![ConfX App](https://img.shields.io/badge/ConfX-APK-brightgreen?style=for-the-badge&logo=android)](https://github.com/RotBolt/ProjectConf/releases/download/v0.3.1-beta/app-debug.apk)

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [RxJava2](https://github.com/ReactiveX/RxJava/tree/2.x) - Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
  - [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - API that makes it easy to schedule deferrable, asynchronous tasks that are expected to run even if the app exits or the device restarts. 
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Dagger2](https://dagger.dev/) - Vanilla Dagger to do dependency injection into an Android application.
  - [Complete Dagger Tutorial](https://blog.mindorks.com/a-complete-guide-to-learn-dagger-2-b4c7a570d99c) - Mindorks Complete Dagger2 tutorial + Dagger Hilt
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [GSON](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects into JSON and back 
- [GSON Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) - A Converter which uses GSON for serialization to and from JSON.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)


## ScreenShots

### Light / Dark Mode
| Dark | Light | Theme Change |
| --- | --- | --- |
|<img src="https://user-images.githubusercontent.com/24780524/85631681-9b4a3180-b693-11ea-9e34-8aacdc6cfc05.jpeg" width=360>|<img src="https://user-images.githubusercontent.com/24780524/85631679-9ab19b00-b693-11ea-8c8b-35a62e260f00.jpeg" width=360>|<img src="https://user-images.githubusercontent.com/24780524/85631678-99806e00-b693-11ea-9c22-46e37ab39514.jpeg" width=360>

### Search
| Search | Search Results | 
| --- | --- |
|<img src="https://user-images.githubusercontent.com/24780524/85632090-465aeb00-b694-11ea-8989-ead58d8cdee6.jpeg" width=360>|<img src="https://user-images.githubusercontent.com/24780524/85632088-4529be00-b694-11ea-98b4-6f0e7d7e4986.jpeg" width=360>

### More Features
| Category Wise Conferences | Conference Details | Set CFP Reminder |
| --- | --- | --- |
|<img src="https://user-images.githubusercontent.com/24780524/85633289-989d0b80-b696-11ea-938d-ea6c4efe5034.jpeg" width=360>|<img src="https://user-images.githubusercontent.com/24780524/85633293-99ce3880-b696-11ea-998f-609f1e0bd18a.jpeg" width=360>|<img src="https://user-images.githubusercontent.com/24780524/85633295-9a66cf00-b696-11ea-8d1f-0c5a6e5ed41c.jpeg" width=360>|

| View Bookmarks/CFP Reminders | Choose Topics for Alerts | Archives |
| --- | --- | --- |
|<img src="https://user-images.githubusercontent.com/24780524/85633711-68a23800-b697-11ea-93e9-62f7bfaf4b2e.jpeg" width=360>|<img src="https://user-images.githubusercontent.com/24780524/85633717-6a6bfb80-b697-11ea-9591-4389d619a8ac.jpeg" width=360>|<img src="https://user-images.githubusercontent.com/24780524/85633714-69d36500-b697-11ea-8a7f-b1dc0e8b2164.jpeg" width=360>|

## Illustrations Credits
[Icons 8 Ouch](https://icons8.com/illustrations) - Awesome free illustrations for your hobby and work projects
