# **Family Shared List**

This project aims to utilize the power of [KMM - Kotlin Multiplatform Mobile](https://kotlinlang.org/lp/mobile/) to develop native Android and iOS mobile apps with a shared codebase for business and data layers. 

If anyone wants to join the study, they will be welcome, just request access to this repository.

The main focus of this study project is to utilize the benefits of KMM to create a shared codebase for both platforms, resulting in an efficient and streamlined development process.

## About the mobile application

This app provides a simple solution for users to create, manage and track items on a shopping/To-do list, with user account creation and the option to share the list with other users.

## About the technologies

### IOS Layer
| Library | description |
| ----------- | ----------- | 
| [SwiftUI](https://developer.apple.com/xcode/swiftui/) | Declarative user interface | 
| MVVM |  |
| Async/Await |  |
| Service Locator | for Dependency Injection |

### Android Layer
| Library | description |
| ----------- | ----------- | 
| [Jetpack Compose](https://developer.android.com/jetpack/compose)  | Declarative user interface |
| MVVM | |
| [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) |  | 
| [Koin](https://insert-koin.io/) | for Dependency Injection | 

### Shared Layer
| Library | description |
| ----------- | ----------- | 
| [Compose for IOS](https://blog.jetbrains.com/kotlin/2023/05/compose-multiplatform-for-ios-is-in-alpha/) | Shared interface using Compose Multiplatform for IOS to build user interface once, 100% in Kotlin, and use it on Android and iOS |
| UseCase Pattern | |
| Repository Pattern | |
| [ktor](https://ktor.io/) | Asynchronous HTTP client for requests | 
| [Koin](https://insert-koin.io/) | for Dependency Injection | 
| [BuildKonfig](https://github.com/yshrsmz/BuildKonfig)  | embedding values from gradle file to store tokens, secrets or sensitive data |

## TODO:

In which I intend to implement the following items as available:
[Project Board](https://github.com/users/haroldjose30/projects/1/views/2)

### Features to be developed: 
- [X] User account creation and login (guess account)
- [X] Adding, editing and deleting items on the list
- [X] Item completion tracking
- [X] Sharing the list with multiple users
- [ ] Item categorization
- [ ] Item priority setting
- [ ] Item search functionality

### IOS Layer:
- [ ] Handle Error for each use case
- [ ] Unit test for viewModel
- [ ] UITest
- [ ] Improve UI Design
- [ ] Publish on Apple Store
- [X] Mocked service injection during the preview process

### Android Layer:
- [ ] Handle Error for each use case
- [ ] Unit test for viewModel
- [ ] UITest
- [ ] Improve UI Design
- [ ] Publish on Google Play
- [X] Mocked service injection during the preview process

### KMM Shared Layer: 
- [X] Put APIKey and url to environment variables
- [ ] Handle Error in all request
- [ ] Add HttpClient to DI
- [ ] Unit test
- [ ] Create KMM library layer to Data and Domain
- [ ] Add Local database for cached request
- [ ] Add offline and sync process
- [ ] Real-Time Database with Realm or Socket.io
