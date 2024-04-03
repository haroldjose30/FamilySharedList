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
| UseCase Pattern | |
| Repository Pattern | |
| [ktor](https://ktor.io/) | Asynchronous HTTP client for requests | 
| [Koin](https://insert-koin.io/) | for Dependency Injection | 
| [BuildKonfig](https://github.com/yshrsmz/BuildKonfig)  | embedding values from gradle file to store tokens, secrets or sensitive data |

In which I intend to implement the following items as available:
[Project Board](https://github.com/users/haroldjose30/projects/1/views/2)
