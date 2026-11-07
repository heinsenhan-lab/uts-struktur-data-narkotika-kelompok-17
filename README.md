# Sistem Pengelolaan Data Putusan Narkotika

Aplikasi berbasis Java untuk menyimpan, mencari, memfilter, mengurutkan, dan mengolah data putusan pengadilan terkait perkara narkotika. Proyek ini dibuat untuk memenuhi tugas Ujian Tengah Semester (UTS) mata kuliah Struktur Data, Universitas Muhammadiyah Malang, Semester Ganjil 2026/2027.

## Identitas Kelompok

**Anggota Kelompok:**

1. Farhan Nugi Dwirafif — 202510370110092
2. Muhammad Nurul Iman — 202510370110097

## Deskripsi Proyek

Aplikasi mengelola 50 data terstruktur putusan perkara narkotika dari Pengadilan Negeri Garut. Data disimpan dalam format CSV dan diproses menggunakan struktur data yang diimplementasikan sendiri.

Tujuan proyek:
- Menerapkan konsep struktur data linier.
- Mengimplementasikan Dynamic Array dari awal sebagai struktur data utama.
- Mengimplementasikan Stack sebagai struktur data pendukung.
- Mengembangkan operasi pengelolaan dan pencarian data putusan.
- Menerapkan pengujian untuk memastikan operasi dan invariant struktur data berjalan dengan benar.

## Struktur Data yang Digunakan

### 1. Dynamic Array — Struktur Utama

Dynamic Array digunakan untuk menyimpan seluruh objek `Putusan`.

Operasi yang digunakan meliputi:
- Menambahkan data.
- Mengambil data berdasarkan indeks.
- Menghapus data.
- Mencari data.
- Mengelola kapasitas penyimpanan secara dinamis.

### 2. Stack — Struktur Pendukung

Stack digunakan untuk menyimpan riwayat aktivitas pengguna dengan prinsip LIFO (*Last In, First Out*).

Riwayat dapat ditampilkan dari aktivitas terbaru ke aktivitas terlama tanpa menghilangkan isi Stack.

## Fitur Aplikasi

| Fitur | Keterangan |
|---|---|
| Impor data | Membaca data putusan dari CSV |
| Tampilkan data | Menampilkan seluruh putusan |
| Tambah data | Menambahkan putusan baru |
| Hapus data | Menghapus putusan berdasarkan Case ID |
| Pencarian | Mencari berdasarkan Case ID atau nomor perkara |
| Filter narkotika | Memfilter berdasarkan jenis narkotika |
| Filter berat | Memfilter berdasarkan rentang berat barang bukti |
| Filter pasal | Memfilter berdasarkan pasal terbukti |
| Filter vonis | Memfilter berdasarkan rentang vonis |
| Statistik | Menghitung jumlah perkara, rata-rata vonis, total denda, dan distribusi jenis narkotika |
| Pengurutan | Mengurutkan tampilan berdasarkan vonis tanpa mengubah data utama |
| Audit | Menampilkan riwayat aktivitas pengguna |
| Ekspor | Mengekspor hasil ke format TXT atau CSV |

## Teknologi

- Java 17
- Apache Maven
- JUnit 5
- Apache PDFBox
- IntelliJ IDEA
- Git dan GitHub

## Struktur Direktori

```text
uts-struktur-data-narkotika-kelompok-17/
├── data/
│   └── putusan-50.csv
├── docs/
│   ├── adr/
│   ├── trace/
│   └── demo/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── datastructure/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── Main.java
│   └── test/
│       └── java/
├── pom.xml
├── README.md
├── CONTRIBUTIONS.md
└── AI_USAGE.md
```

## Persyaratan Sistem

Sebelum menjalankan aplikasi, pastikan perangkat telah memiliki:
- JDK 17 atau lebih baru.
- Apache Maven.
- Git (jika ingin mengambil repository).

## Cara Build

Buka terminal pada direktori utama proyek, kemudian jalankan:

```bash
mvn clean compile
```

Untuk membangun proyek sekaligus menjalankan proses pengujian:

```bash
mvn clean test
```

## Cara Menjalankan Aplikasi

Jalankan perintah berikut dari direktori utama proyek:

```bash
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=Main
```

Alternatifnya, buka proyek melalui IntelliJ IDEA, kemudian jalankan file:

```text
src/main/java/Main.java
```

Pastikan file dataset tersedia di:

```text
data/putusan-50.csv
```

## Contoh Penggunaan

Ketika aplikasi dijalankan, sistem akan membaca dataset dan menampilkan menu utama.

```text
MENU UTAMA
1.  Tampilkan seluruh data
2.  Tambah data putusan
3.  Hapus data berdasarkan Case ID
4.  Cari berdasarkan Case ID
5.  Cari berdasarkan nomor perkara
6.  Filter jenis narkotika
7.  Filter rentang berat barang bukti
8.  Filter pasal terbukti
9.  Filter rentang vonis
10. Tampilkan statistik dan agregasi
11. Urutkan berdasarkan vonis
12. Tampilkan riwayat aktivitas
13. Ekspor hasil ke TXT/CSV
0.  Keluar
```

Contoh pencarian:

```text
Pilih menu: 4
Masukkan Case ID: NARK-001
```

Contoh filter:

```text
Pilih menu: 6
Masukkan kata kunci jenis (contoh: sabu): sabu
```

## Dataset dan Sumber Data

Dataset utama tersimpan pada:

```text
data/putusan-50.csv
```

Data bersumber dari Direktori Putusan Mahkamah Agung Republik Indonesia.

Nama terdakwa disamarkan menggunakan kode anonim. Informasi yang tidak ditemukan pada dokumen sumber tidak diperkirakan atau dibuat-buat.

Informasi sumber dan halaman untuk setiap atribut penting dicatat dalam dokumentasi provenance.

## Dokumentasi Tambahan

- `docs/adr/` — Catatan keputusan desain dan pemilihan struktur data.
- `docs/trace/` — Dokumentasi penelusuran dan pemeriksaan data.
- `docs/demo/` — Bukti demonstrasi aplikasi.
- `CONTRIBUTIONS.md` — Catatan kontribusi masing-masing anggota.
- `AI_USAGE.md` — Dokumentasi penggunaan dan verifikasi AI.

## Pengujian

Pengujian otomatis menggunakan JUnit 5 dan dapat dijalankan melalui:

```bash
mvn test
```

Pengujian ditujukan untuk memeriksa operasi struktur data, pencarian, penambahan dan penghapusan elemen, serta konsistensi data.

## Repository dan Rilis

Repository: https://github.com/heinsenhan-lab/uts-struktur-data-narkotika-kelompok-17

Tag rilis: `uts-v1.0`