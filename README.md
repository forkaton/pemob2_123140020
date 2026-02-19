# Tugas 2 Praktikum - News Feed Simulator

**Nama:** Anselmus Herpin Hasugian  
**NIM:** 123140020  
**Kelas:** RA  
**Mata Kuliah:** Pengembangan Aplikasi Mobile  

## Deskripsi Program
Proyek ini adalah aplikasi Kotlin Multiplatform (KMP) yang mensimulasikan sistem *News Feed* secara asynchronous. Aplikasi ini dibangun untuk mendemonstrasikan pemahaman mendalam terkait implementasi Advanced Kotlin, Coroutines, dan arsitektur data reaktif menggunakan Kotlin Flow.

## Pemenuhan Kriteria Rubrik
1. **Implementasi Flow:** Menggunakan `flow {}` builder dan `emit()` pada lapisan *Repository* untuk menghasilkan data berita baru setiap 2 detik. Aliran data ini diakses menggunakan `collect()` pada lapisan antarmuka.
2. **Penggunaan Operators:** Mengaplikasikan operator `.filter()` untuk menyaring berita berdasarkan kategori, `.map()` untuk transformasi string judul berita, dan `.onEach()` untuk keperluan pelacakan (*logging*) di konsol.
3. **StateFlow Implementation:** Menggunakan `MutableStateFlow` dan `StateFlow` untuk manajemen *state* reaktif, termasuk penyimpanan daftar berita, status filter kategori aktif, dan kalkulasi "Total Dibaca".
4. **Coroutines Usage:** Mengimplementasikan fungsi *suspend*, *coroutine builders* (`launch`, `async`), `await`, serta mengatur *thread* menggunakan `Dispatchers.Default` untuk simulasi pengambilan detail berita tanpa memblokir *Main Thread*.
5. **Bonus - Error Handling:** Mengimplementasikan fungsi `.catch()` dan `.retryWhen()` pada struktur Flow untuk mencegah *force close* saat terjadi simulasi kegagalan jaringan (probabilitas *error* 15%).

## Cara Menjalankan Aplikasi
1. Lakukan *clone* pada repository ini ke direktori lokal.
2. Buka proyek menggunakan Android Studio versi terbaru yang mendukung ekosistem KMP.
3. Tunggu hingga proses *Gradle Sync* selesai sepenuhnya.
4. Pilih modul `composeApp` pada menu *Run Configuration*.
5. Tekan tombol *Run* untuk menjalankan aplikasi pada Emulator Android atau perangkat fisik.

## Dokumentasi Visual

**1. Tampilan Utama (Berhasil Memuat Data)**
![Tampilan Utama]<img width="1919" height="1199" alt="Screenshot 2026-02-19 201406" src="https://github.com/user-attachments/assets/dcfd6f33-b135-42e0-8151-547a6db6cd88" />

> **Deskripsi:** Menampilkan simulasi aliran data (Flow) berita yang berhasil dimuat secara asynchronous. Komponen "Dibaca: 0" di sudut kanan atas terikat dengan `StateFlow` dan akan merespons perubahan secara *real-time*.

**2. Detail Berita (Asynchronous Fetch)**
![Detail Berita]<img width="1919" height="1199" alt="Screenshot 2026-02-19 201450" src="https://github.com/user-attachments/assets/4fe9e8ce-8e6e-4d34-bd59-34f302357927" />

> **Deskripsi:** Menampilkan *Modal Bottom Sheet* beserta ID berita saat salah satu kartu diklik. Hal ini membuktikan berjalannya blok *Coroutines* (`async`/`await`) dengan jeda simulasi 1,5 detik untuk memuat konten secara non-blokir.

**3. Simulasi Error Handling (Kategori Semua)**
![Error Semua Kategori]<img width="1919" height="1199" alt="Screenshot 2026-02-19 203106" src="https://github.com/user-attachments/assets/3e973f98-ffdc-4abd-a517-427a03cf8671" />

> **Deskripsi:** Menampilkan penanganan eksepsi (Exception) ketika simulasi koneksi terputus. Operator `.catch` menangkap kegagalan tersebut dan merendernya sebagai antarmuka peringatan tanpa menghentikan atau melakukan *crash* pada aplikasi.

**4. Filter Kategori dan Rekaman Error (Kategori Teknologi)**
![Error Kategori Teknologi]<img width="1919" height="1199" alt="Screenshot 2026-02-19 201513" src="https://github.com/user-attachments/assets/0f1ce3e2-6fb7-4eda-9533-a07b15ae2704" />

> **Deskripsi:** Menunjukkan berjalannya operator `.filter{}` pada lapisan ViewModel. Layar hanya menampilkan berita dan *history error* yang diklasifikasikan masuk ke dalam memori *state* kategori "Teknologi".

**5. Penanganan Eksepsi Interaksi (Klik pada Item Error)**
![Klik Error]<img width="1919" height="1199" alt="Screenshot 2026-02-19 201538" src="https://github.com/user-attachments/assets/fda5b449-e179-4659-b4cc-13555aa55244" />

> **Deskripsi:** Menunjukkan ketahanan aplikasi (*robustness*). Ketika pengguna mencoba mengklik kartu laporan *error*, blok `try-catch` di dalam fungsi interaksi ViewModel berhasil mencegah *crash* sistem dan memberikan umpan balik (feedback) kegagalan memuat detail.
