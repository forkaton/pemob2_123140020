# Tugas Praktikum 3 - My Profile App (Pemrograman Aplikasi Mobile)

**Nama:** Anselmus Herpin Hasugian  
**NIM:** 123140020  

## Deskripsi Proyek
Proyek ini adalah pengembangan aplikasi "My Profile App" menggunakan **Kotlin Multiplatform** dan **Compose Multiplatform**. Aplikasi ini dirancang untuk mendemonstrasikan pemahaman mendalam mengenai paradigma UI Deklaratif, komponen UI dasar, dan modifikasi tata letak sesuai dengan standar Material Design 3.

## Pemenuhan Kriteria Rubrik
Aplikasi ini dibangun dengan memenuhi seluruh kriteria penilaian Tugas Praktikum 3:
1. **Layout Implementation:** Mengimplementasikan tata letak dasar secara komprehensif menggunakan `Column` (susunan vertikal), `Row` (susunan horizontal), dan `Box` (susunan bertumpuk untuk *header*).
2. **Reusable Composables:** Menerapkan prinsip modularitas dengan membuat 3 fungsi *composable* kustom yang dapat digunakan ulang, yaitu: `ProfileHeader`, `ProfileCard`, dan `InfoItem`.
3. **UI Components:** Menggunakan variasi komponen UI standar Jetpack Compose secara optimal, termasuk `Text`, `Button`, `Image` (foto profil), `Card` (kontainer biodata), dan `Icon` (menggunakan kumpulan ImageVector `Icons.Default`).
4. **Modifiers:** Menerapkan *chaining modifiers* untuk *styling* dan *positioning*, seperti penambahan *padding*, konfigurasi *fillMaxSize/fillMaxWidth*, *clipping* foto menjadi `CircleShape`, dan penerapan *shadow elevation*.
5. **Bonus (+10%) - Animasi:** Mengimplementasikan efek transisi visual menggunakan `AnimatedVisibility` (dengan properti `slideInVertically` dan `fadeIn`) yang akan terpicu (*trigger*) ketika pengguna berinteraksi dengan tombol "Hubungi Saya".

## Dokumentasi Visual

<img src="https://github.com/user-attachments/assets/9eee6ac7-c316-4e8d-b3bd-234c6f567e68" width="300" alt="Android Screenshot" />

## Cara Menjalankan Aplikasi

**Menjalankan di Emulator/Perangkat Android:**
```shell
.\gradlew.bat :composeApp:assembleDebug
