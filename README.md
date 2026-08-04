# MOE Primary Learning Hub 🎓

A professional educational Android application built with Jetpack Compose, designed to align with the **Singapore MOE Syllabus (2021-2025)** for Primary 1–6.

## 🌟 Key Features
- **Comprehensive Subject Coverage**: Mathematics (Numerical & Logic), English (STELLAR Literacy), and Science (Inquiry-based).
- **Choice-Based Learning**: Students can choose their preferred study method: Quiz Quest, Memory Puzzles, Smart Flashcards, or Interactive Labs.
- **Ethical Architecture**: 
  - **Privacy-First**: Zero data collection. All progress is stored locally via Room Database (GDPR-K & COPPA compliant).
  - **Inclusion-Focused**: High-contrast UI and 48dp+ touch targets for accessibility.
  - **Offline-Ready**: Local fallback question banks to bridge the "Digital Divide."
- **Gamified Progress**: XP and Trophy tracking system with advanced data aggregation (Aggregate SQL Queries).

## 🛠️ Technical Stack
- **UI**: Jetpack Compose (Material 3) with custom animations and responsive design (Phone/Tablet).
- **Architecture**: Clean Architecture (MVVM) with Repository pattern.
- **Dependency Injection**: Hilt for modularity and testability.
- **Persistence**: Room Database for secure local storage.
- **Networking**: Retrofit & GSON for external API integration with robust error handling.
- **Testing**: JUnit 4 for logic (MockK) and Compose UI Tests (Instrumented) for the GUI.

## 🧪 Testing Results
The project includes a suite of unit and instrumented tests to ensure stability:
- **Unit Tests**: Verifies ViewModel logic and flow state. (Status: **Passed**)
- **Instrumented Tests**: Verifies navigation flow and UI semantic integrity. (Status: **Passed**)

## 📜 Ethical Alignment (ACS Code of Ethics)
1. **Primacy of Public Interest**: Implemented local storage to protect child data privacy.
2. **Enhancement of Quality of Life**: Added haptic and auditory feedback for inclusive sensory reinforcement.
3. **Honesty**: Clear curriculum transparency in the "Parents & Help" section.
4. **Competence**: Followed modern Android development standards (MVVM, DI, Compose).

---

## Getting Started
1.  Open in **Android Studio**.
2.  Go to **Play & Learn** to explore the MOE modules.
3.  Use the **Parents & Help** section for curriculum details.

---

## 📚 Course Details
**Student**: [Your Name/ID]
**Subject**: CP3406/CP5307 Assessment 3
**Template Source**: CP3406 Utility App Starter Template
