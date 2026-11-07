Sistem Pengelolaan Data Putusan Narkotika

Anggota Kelompok

* Farhan Nugi Dwirafif 2020510370110092
* Muhammad Nurul Iman 202510370110097

Deskripsi Proyek

Aplikasi ini dibuat untuk memenuhi tugas Ujian Tengah Semester (UTS) mata kuliah Struktur Data.

Aplikasi digunakan untuk mengelola data putusan pengadilan terkait kasus narkotika menggunakan bahasa pemrograman Java.

Tujuan

- Mengelola data putusan narkotika.
- Menerapkan struktur data Dynamic Array.
- Menerapkan Stack untuk menyimpan riwayat aktivitas.
- Menyediakan fitur pencarian, penyaringan, pengurutan, dan rekapitulasi data.

Teknologi

- Java 17
- Maven
- JUnit 5
- IntelliJ IDEA
- Git dan GitHub

Struktur Data

1. **Dynamic Array** sebagai struktur data utama untuk menyimpan data putusan.
2. **Stack** sebagai struktur data pendukung untuk mencatat riwayat aktivitas.

Struktur Proyek

```text
src/
├── main/java/
└── test/java/
data/
docs/
├── adr/
├── trace/
└── demo/
pom.xml
README.md
```

Cara Menjalankan

Pastikan Java 17 dan Maven sudah terpasang.

Jalankan pengujian menggunakan perintah:

```bash
mvn test
```

Sumber Data

Data putusan bersumber dari Direktori Putusan Mahkamah Agung Republik Indonesia. Informasi setiap putusan akan dicatat beserta sumbernya agar dapat ditelusuri kembali.
