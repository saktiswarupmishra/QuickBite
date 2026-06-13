# 🍔 QuickBite Local

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5+-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Material3](https://img.shields.io/badge/Material%203-Latest-757575?style=for-the-badge&logo=materialdesign&logoColor=white)](https://m3.material.io/)
[![Hilt](https://img.shields.io/badge/Hilt-Dependency%20Injection-orange?style=for-the-badge&logo=google&logoColor=white)](https://dagger.dev/hilt/)

**QuickBite Local** is a modern, high-performance Android application designed to revolutionize the way you discover and order food from local restaurants. Built with the latest Android technologies, it offers a seamless, intuitive, and AI-powered dining experience.

---

## 🚀 Key Features

- 🔍 **Smart Discovery**: Easily find local restaurants with advanced search and category filtering.
- 🤖 **AI-Powered Recommendations**: Personalized food suggestions driven by **Google Gemini AI** based on your order history.
- 📍 **Interactive Maps**: Visualize restaurant locations and track your cravings with integrated Google Maps.
- 🛒 **Effortless Ordering**: A streamlined checkout process for a quick and hassle-free experience.
- ❤️ **Favorites**: Save your most-loved spots for quick access.
- 🌓 **Modern UI**: Fully responsive design with Material 3 and Dark Mode support.

---

## 🛠 Technology Stack

<p align="left">
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/kotlin/kotlin-original.svg" alt="kotlin" width="40" height="40"/> &nbsp;
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-original.svg" alt="android" width="40" height="40"/> &nbsp;
  <img src="https://www.vectorlogo.zone/logos/firebase/firebase-icon.svg" alt="firebase" width="40" height="40"/> &nbsp;
  <img src="https://www.vectorlogo.zone/logos/google_cloud/google_cloud-icon.svg" alt="google_cloud" width="40" height="40"/> &nbsp;
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/>
</p>

### Core
- **Language**: [Kotlin](https://kotlinlang.org/) - 100% type-safe and modern code.
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - Declarative UI development.
- **Architecture**: MVVM (Model-View-ViewModel) with Clean Architecture principles.
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/) - Standardized DI for Android.

### Data & Networking
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room) - Robust local persistence.
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) - Type-safe HTTP client.
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) - Modern image loading for Android.

### Services & AI
- **Backend Services**: [Firebase](https://firebase.google.com/) - Authentication, Analytics, and Cloud Messaging.
- **Intelligence**: [Google AI SDK (Gemini)](https://ai.google.dev/) - Personalization engine.
- **Maps**: [Google Maps Compose](https://github.com/googlemaps/android-maps-compose) - Seamless map integration in Compose.

---

## 🏗 Architecture

The app follows the recommended **Android Architecture Components** and **Clean Architecture** patterns:

```text
app/
├── data/           # Data sources (Local DB, API, Repository Impls)
├── domain/         # Business logic (Models, Use Cases, Repository Interfaces)
├── ui/             # UI layer (Screens, ViewModels, Theme)
└── di/             # Hilt modules
```

---

## 📸 Screenshots

<div align="center">
  <table style="border: none;">
    <tr>
      <td align="center">
        <img src="https://via.placeholder.com/300x600?text=Home+Screen" width="200" alt="Home Screen"/><br/>
        <b>Home Screen</b>
      </td>
      <td align="center">
        <img src="https://via.placeholder.com/300x600?text=Details+Screen" width="200" alt="Details Screen"/><br/>
        <b>Details</b>
      </td>
      <td align="center">
        <img src="https://via.placeholder.com/300x600?text=Map+View" width="200" alt="Map View"/><br/>
        <b>Map View</b>
      </td>
    </tr>
  </table>
</div>

---

## ⚙️ Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/QuickBiteLocal.git
   ```
2. **Setup Firebase**:
   - Place your `google-services.json` in the `app/` directory.
3. **API Keys**:
   - Add your `GOOGLE_MAPS_API_KEY` to `local.properties`.
   - Add your `GEMINI_API_KEY` for AI features.
4. **Build & Run**:
   - Open in Android Studio (Ladybug or newer recommended).
   - Sync Gradle and hit **Run**.

---

## 🤝 Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request.

---

## 📄 License

```text
Copyright 2024 QuickBite Local Team

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
```

---
<p align="center">Made with ❤️ for food lovers everywhere.</p>
