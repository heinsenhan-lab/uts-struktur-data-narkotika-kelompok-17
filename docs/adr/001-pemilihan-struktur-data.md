ADR 001: Pemilihan Struktur Data

Status
Diterima (Accepted)

Konteks
Aplikasi pengelolaan data putusan narkotika membutuhkan struktur data untuk menyimpan dan mengelola data putusan secara dinamis.

Struktur data yang digunakan harus mendukung penambahan, penyisipan, pencarian, perubahan, dan penghapusan data.

Keputusan

Proyek ini menggunakan:

1. **Dynamic Array** sebagai struktur data utama untuk menyimpan data putusan narkotika.
2. **Stack** sebagai struktur data pendukung untuk menyimpan riwayat aktivitas atau audit.

Dynamic Array dipilih karena kapasitas penyimpanannya dapat bertambah sesuai kebutuhan dan mendukung akses data berdasarkan indeks.

Stack digunakan karena menerapkan prinsip LIFO (Last In, First Out), sehingga aktivitas terakhir dapat diakses terlebih dahulu.

Konsekuensi

- Dynamic Array harus diimplementasikan secara manual.
- Kapasitas array harus dapat bertambah ketika penuh.
- Stack harus memiliki operasi push, pop, dan peek.
- Penggunaan struktur data bawaan Java seperti ArrayList dan Stack tidak diperbolehkan untuk penyimpanan utama proyek.
