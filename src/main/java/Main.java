import datastructure.AuditStack;
import datastructure.DynamicArray;
import model.Putusan;
import repository.CsvReader;
import service.LaporanService;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final String CSV_PATH = "data/putusan-50.csv";

    private static final Scanner input = new Scanner(System.in);
    private static final DynamicArray<Putusan> data = new DynamicArray<>();
    private static DynamicArray<Putusan> hasilTerakhir = new DynamicArray<>();

    private static final AuditStack<String> riwayat = new AuditStack<>();

    private static final LaporanService laporanService = new LaporanService();
    private static final NumberFormat formatRupiah =
            NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));

    public static void main(String[] args) {
        muatData();

        if (data.isEmpty()) {
            System.out.println("Program dihentikan karena data tidak berhasil dimuat.");
            return;
        }

        hasilTerakhir = salinData(data);
        riwayat.push("Program dimulai. Jumlah data dimuat: " + data.size());

        boolean berjalan = true;

        while (berjalan) {
            tampilkanMenu();
            int pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    tampilkanSemuaData();
                    break;
                case 2:
                    tambahData();
                    break;
                case 3:
                    hapusData();
                    break;
                case 4:
                    cariBerdasarkanCaseId();
                    break;
                case 5:
                    cariBerdasarkanNomorPerkara();
                    break;
                case 6:
                    filterJenisNarkotika();
                    break;
                case 7:
                    filterRentangBerat();
                    break;
                case 8:
                    filterPasalTerbukti();
                    break;
                case 9:
                    filterRentangVonis();
                    break;
                case 10:
                    tampilkanStatistik();
                    break;
                case 11:
                    tampilkanUrutanVonis();
                    break;
                case 12:
                    tampilkanRiwayat();
                    break;
                case 13:
                    eksporData();
                    break;
                case 0:
                    riwayat.push("Program ditutup.");
                    berjalan = false;
                    System.out.println("Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
            }

            if (berjalan) {
                System.out.println();
            }
        }
    }

    private static void muatData() {
        try {
            CsvReader reader = new CsvReader();
            DynamicArray<Putusan> hasilBaca = reader.readCsv(CSV_PATH);

            for (int i = 0; i < hasilBaca.size(); i++) {
                data.add(hasilBaca.get(i));
            }

            System.out.println("==============================================");
            System.out.println(" APLIKASI PENGELOLAAN DATA PUTUSAN NARKOTIKA");
            System.out.println("==============================================");
            System.out.println("File data : " + CSV_PATH);
            System.out.println("Data dimuat: " + data.size() + " perkara");

            if (data.size() != 50) {
                System.out.println("PERINGATAN: Data yang terbaca bukan 50 baris.");
                System.out.println("Periksa kembali file CSV dan pesan baris yang dilewati.");
            } else {
                System.out.println("Status: 50 data berhasil dimuat.");
            }

        } catch (IOException e) {
            System.out.println("Gagal membaca CSV: " + e.getMessage());
            System.out.println("Pastikan file berada di: " + CSV_PATH);
        }
    }

    private static void tampilkanMenu() {
        System.out.println("----------------------------------------------");
        System.out.println("MENU UTAMA");
        System.out.println("1.  Tampilkan seluruh data");
        System.out.println("2.  Tambah data putusan");
        System.out.println("3.  Hapus data berdasarkan Case ID");
        System.out.println("4.  Cari berdasarkan Case ID");
        System.out.println("5.  Cari berdasarkan nomor perkara");
        System.out.println("6.  Filter jenis narkotika");
        System.out.println("7.  Filter rentang berat barang bukti");
        System.out.println("8.  Filter pasal terbukti");
        System.out.println("9.  Filter rentang vonis");
        System.out.println("10. Tampilkan statistik dan agregasi");
        System.out.println("11. Urutkan berdasarkan vonis");
        System.out.println("12. Tampilkan riwayat aktivitas");
        System.out.println("13. Ekspor hasil ke TXT/CSV");
        System.out.println("0.  Keluar");
        System.out.println("----------------------------------------------");
    }

    private static void tampilkanSemuaData() {
        System.out.println("\nDAFTAR SELURUH PUTUSAN");
        tampilkanTabel(data);
        hasilTerakhir = salinData(data);
        riwayat.push("Menampilkan seluruh data (" + data.size() + " perkara).");
    }

    private static void tampilkanTabel(DynamicArray<Putusan> daftar) {
        if (daftar == null || daftar.isEmpty()) {
            System.out.println("Tidak ada data untuk ditampilkan.");
            return;
        }

        System.out.printf("%-10s %-29s %-23s %12s %10s %15s%n",
                "Case ID", "Nomor Perkara", "Jenis Narkotika",
                "Berat (g)", "Vonis", "Denda (Rp)");

        System.out.println(
                "---------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < daftar.size(); i++) {
            Putusan p = daftar.get(i);

            System.out.printf("%-10s %-29s %-23s %12.4f %10d %15d%n",
                    aman(p.getCaseId()),
                    potong(p.getNomorPerkara(), 28),
                    potong(p.getJenisNarkotika(), 22),
                    p.getBeratBarangBuktiGram(),
                    p.getVonisBulan(),
                    p.getDendaRupiah());
        }

        System.out.println("Jumlah data: " + daftar.size());
        System.out.println("Catatan: nilai 0 pada data numerik menandakan informasi tidak tersedia.");
    }

    private static void tampilkanDetail(Putusan p) {
        System.out.println("\n========== DETAIL PUTUSAN ==========");
        System.out.println("Case ID                 : " + aman(p.getCaseId()));
        System.out.println("Nomor perkara            : " + aman(p.getNomorPerkara()));
        System.out.println("Pengadilan               : " + aman(p.getPengadilan()));
        System.out.println("Tanggal putusan          : " + p.getTanggalPutusan());
        System.out.println("Kode terdakwa            : " + aman(p.getTerdakwaKode()));
        System.out.println("Jenis narkotika          : " + aman(p.getJenisNarkotika()));
        System.out.println("Berat barang bukti (gram): " + p.getBeratBarangBuktiGram());
        System.out.println("Pasal dakwaan            : " + aman(p.getPasalDakwaan()));
        System.out.println("Pasal terbukti           : " + aman(p.getPasalTerbukti()));
        System.out.println("Tuntutan (bulan)         : " + p.getTuntutanBulan());
        System.out.println("Vonis (bulan)            : " + p.getVonisBulan());
        System.out.println("Denda                    : " + formatRupiah.format(p.getDendaRupiah()));
        System.out.println("Barang bukti             : " + aman(p.getBarangBukti()));
        System.out.println("Keadaan memberatkan      : " + aman(p.getKeadaanMemberatkan()));
        System.out.println("Keadaan meringankan      : " + aman(p.getKeadaanMeringankan()));
        System.out.println("Halaman sumber           : " + aman(p.getSourcePages()));
        System.out.println("====================================");
    }

    private static void tambahData() {
        System.out.println("\nTAMBAH DATA PUTUSAN");

        String caseId = bacaTeksWajib("Case ID: ");
        if (cariIndexCaseId(caseId) >= 0) {
            System.out.println("Case ID sudah digunakan. Data tidak ditambahkan.");
            return;
        }

        String nomorPerkara = bacaTeksWajib("Nomor perkara: ");
        if (cariIndexNomorPerkara(nomorPerkara) >= 0) {
            System.out.println("Nomor perkara sudah ada. Data tidak ditambahkan.");
            return;
        }

        String pengadilan = bacaTeksWajib("Pengadilan: ");
        LocalDate tanggal = bacaTanggal("Tanggal putusan (YYYY-MM-DD): ");
        String terdakwaKode = bacaTeksWajib("Kode terdakwa (contoh: Terdakwa-051): ");
        String jenis = bacaTeksWajib("Jenis narkotika: ");
        double berat = bacaDoubleNonNegatif("Berat netto (gram; isi 0 jika tidak tersedia): ");
        String pasalDakwaan = bacaTeksWajib("Pasal dakwaan: ");
        String pasalTerbukti = bacaTeksWajib("Pasal terbukti: ");
        int tuntutan = bacaIntNonNegatif("Tuntutan (bulan): ");
        int vonis = bacaIntNonNegatif("Vonis (bulan): ");
        long denda = bacaLongNonNegatif("Denda (angka rupiah tanpa titik): ");
        String barangBukti = bacaTeksWajib("Ringkasan barang bukti: ");
        String memberatkan = bacaTeksWajib("Keadaan memberatkan: ");
        String meringankan = bacaTeksWajib("Keadaan meringankan: ");
        String halaman = bacaTeksWajib("Halaman sumber: ");

        Putusan baru = new Putusan(
                caseId, nomorPerkara, pengadilan, tanggal, terdakwaKode,
                jenis, berat, pasalDakwaan, pasalTerbukti, tuntutan,
                vonis, denda, barangBukti, memberatkan, meringankan, halaman
        );

        data.add(baru);
        hasilTerakhir = salinData(data);
        riwayat.push("Menambahkan perkara " + caseId + ".");
        System.out.println("Data berhasil ditambahkan ke DynamicArray.");
        System.out.println("Jumlah data sekarang: " + data.size());
    }

    private static void hapusData() {
        System.out.println("\nHAPUS DATA PUTUSAN");
        String caseId = bacaTeksWajib("Masukkan Case ID yang akan dihapus: ");

        int index = cariIndexCaseId(caseId);
        if (index < 0) {
            System.out.println("Data dengan Case ID tersebut tidak ditemukan.");
            riwayat.push("Gagal menghapus data " + caseId + " (tidak ditemukan).");
            return;
        }

        Putusan terhapus = data.remove(index);
        hasilTerakhir = salinData(data);
        riwayat.push("Menghapus perkara " + terhapus.getCaseId() + ".");
        System.out.println("Data berhasil dihapus menggunakan operasi remove DynamicArray.");
        System.out.println("Jumlah data sekarang: " + data.size());
    }

    private static void cariBerdasarkanCaseId() {
        System.out.println("\nPENCARIAN BERDASARKAN CASE ID");
        String kataKunci = bacaTeksWajib("Masukkan Case ID: ");

        int langkah = 0;
        Putusan ditemukan = null;

        for (int i = 0; i < data.size(); i++) {
            langkah++;
            if (data.get(i).getCaseId().equalsIgnoreCase(kataKunci)) {
                ditemukan = data.get(i);
                break;
            }
        }

        hasilTerakhir = new DynamicArray<>();

        if (ditemukan == null) {
            System.out.println("Data tidak ditemukan.");
        } else {
            hasilTerakhir.add(ditemukan);
            tampilkanDetail(ditemukan);
            System.out.println("Data ditemukan dalam " + langkah + " langkah pencarian.");
        }

        riwayat.push("Pencarian Case ID " + kataKunci + " (" + langkah + " langkah).");
    }

    private static void cariBerdasarkanNomorPerkara() {
        System.out.println("\nPENCARIAN BERDASARKAN NOMOR PERKARA");
        String kataKunci = bacaTeksWajib("Masukkan nomor perkara: ");

        int langkah = 0;
        Putusan ditemukan = null;

        for (int i = 0; i < data.size(); i++) {
            langkah++;
            if (data.get(i).getNomorPerkara().equalsIgnoreCase(kataKunci)) {
                ditemukan = data.get(i);
                break;
            }
        }

        hasilTerakhir = new DynamicArray<>();

        if (ditemukan == null) {
            System.out.println("Data tidak ditemukan.");
        } else {
            hasilTerakhir.add(ditemukan);
            tampilkanDetail(ditemukan);
            System.out.println("Data ditemukan dalam " + langkah + " langkah pencarian.");
        }

        riwayat.push("Pencarian nomor perkara " + kataKunci + " (" + langkah + " langkah).");
    }

    private static void filterJenisNarkotika() {
        System.out.println("\nFILTER JENIS NARKOTIKA");
        String kataKunci = bacaTeksWajib("Masukkan kata kunci jenis (contoh: sabu): ");

        DynamicArray<Putusan> hasil = new DynamicArray<>();

        for (int i = 0; i < data.size(); i++) {
            String jenis = aman(data.get(i).getJenisNarkotika());
            if (jenis.toLowerCase().contains(kataKunci.toLowerCase())) {
                hasil.add(data.get(i));
            }
        }

        hasilTerakhir = hasil;
        tampilkanTabel(hasil);
        riwayat.push("Filter jenis narkotika: " + kataKunci + " (" + hasil.size() + " hasil).");
    }

    private static void filterRentangBerat() {
        System.out.println("\nFILTER RENTANG BERAT BARANG BUKTI");
        double minimum = bacaDoubleNonNegatif("Berat minimum (gram): ");
        double maksimum = bacaDoubleNonNegatif("Berat maksimum (gram): ");

        if (maksimum < minimum) {
            System.out.println("Rentang tidak valid: maksimum lebih kecil daripada minimum.");
            return;
        }

        DynamicArray<Putusan> hasil = new DynamicArray<>();

        for (int i = 0; i < data.size(); i++) {
            double berat = data.get(i).getBeratBarangBuktiGram();

            // Nilai 0 berarti berat netto tidak tersedia, sehingga tidak ikut rentang.
            if (berat > 0 && berat >= minimum && berat <= maksimum) {
                hasil.add(data.get(i));
            }
        }

        hasilTerakhir = hasil;
        tampilkanTabel(hasil);
        riwayat.push("Filter berat " + minimum + "-" + maksimum
                + " gram (" + hasil.size() + " hasil).");
    }

    private static void filterPasalTerbukti() {
        System.out.println("\nFILTER PASAL TERBUKTI");
        String kataKunci = bacaTeksWajib("Masukkan nomor/potongan pasal (contoh: 114): ");

        DynamicArray<Putusan> hasil = new DynamicArray<>();

        for (int i = 0; i < data.size(); i++) {
            String pasal = aman(data.get(i).getPasalTerbukti());
            if (pasal.toLowerCase().contains(kataKunci.toLowerCase())) {
                hasil.add(data.get(i));
            }
        }

        hasilTerakhir = hasil;
        tampilkanTabel(hasil);
        riwayat.push("Filter pasal terbukti " + kataKunci + " (" + hasil.size() + " hasil).");
    }

    private static void filterRentangVonis() {
        System.out.println("\nFILTER RENTANG VONIS");
        int minimum = bacaIntNonNegatif("Vonis minimum (bulan): ");
        int maksimum = bacaIntNonNegatif("Vonis maksimum (bulan): ");

        if (maksimum < minimum) {
            System.out.println("Rentang tidak valid: maksimum lebih kecil daripada minimum.");
            return;
        }

        DynamicArray<Putusan> hasil = new DynamicArray<>();

        for (int i = 0; i < data.size(); i++) {
            int vonis = data.get(i).getVonisBulan();

            // Nilai 0 menandakan vonis tidak tersedia, bukan vonis nol bulan.
            if (vonis > 0 && vonis >= minimum && vonis <= maksimum) {
                hasil.add(data.get(i));
            }
        }

        hasilTerakhir = hasil;
        tampilkanTabel(hasil);
        riwayat.push("Filter vonis " + minimum + "-" + maksimum
                + " bulan (" + hasil.size() + " hasil).");
    }

    private static void tampilkanStatistik() {
        System.out.println("\nSTATISTIK DAN AGREGASI DATA");

        int jumlahPerkara = laporanService.hitungJumlahPerkara(data);
        long totalDenda = laporanService.hitungTotalDenda(data);

        int totalVonis = 0;
        int jumlahVonisTersedia = 0;

        for (int i = 0; i < data.size(); i++) {
            int vonis = data.get(i).getVonisBulan();
            if (vonis > 0) {
                totalVonis += vonis;
                jumlahVonisTersedia++;
            }
        }

        double rataRata = jumlahVonisTersedia == 0
                ? 0
                : (double) totalVonis / jumlahVonisTersedia;

        System.out.println("Jumlah perkara          : " + jumlahPerkara);
        System.out.printf("Rata-rata vonis         : %.2f bulan%n", rataRata);
        System.out.println("Jumlah data vonis valid : " + jumlahVonisTersedia);
        System.out.println("Total denda             : " + formatRupiah.format(totalDenda));

        System.out.println("\nDistribusi jenis narkotika:");
        DynamicArray<String> distribusi =
                laporanService.hitungDistribusiJenisNarkotika(data);

        for (int i = 0; i < distribusi.size(); i++) {
            String[] bagian = distribusi.get(i).split("\\|", 2);
            System.out.println("- " + bagian[0] + ": " + bagian[1] + " perkara");
        }

        riwayat.push("Melihat statistik dan agregasi data.");
    }

    private static void tampilkanUrutanVonis() {
        System.out.println("\nDATA DIURUTKAN BERDASARKAN VONIS (TERKECIL)");
        DynamicArray<Putusan> hasilUrut =
                laporanService.urutkanBerdasarkanVonis(data);

        tampilkanTabel(hasilUrut);

        System.out.println("Urutan di atas menggunakan salinan DynamicArray.");
        System.out.println("Urutan data utama tidak diubah.");
        riwayat.push("Mengurutkan tampilan berdasarkan vonis tanpa mengubah data utama.");
    }

    private static void tampilkanRiwayat() {
        System.out.println("\nRIWAYAT AKTIVITAS (TERBARU KE TERLAMA)");

        if (riwayat.isEmpty()) {
            System.out.println("Belum ada aktivitas.");
            return;
        }

        // Isi stack dipindahkan sementara, ditampilkan, lalu dikembalikan.
        // Dengan begitu riwayat tidak hilang setelah ditampilkan.
        DynamicArray<String> sementara = new DynamicArray<>();

        while (!riwayat.isEmpty()) {
            sementara.add(riwayat.pop());
        }

        for (int i = 0; i < sementara.size(); i++) {
            System.out.println((i + 1) + ". " + sementara.get(i));
        }

        for (int i = sementara.size() - 1; i >= 0; i--) {
            riwayat.push(sementara.get(i));
        }
    }

    private static void eksporData() {
        System.out.println("\nEKSPOR DATA");
        System.out.println("1. Ekspor hasil pencarian/filter terakhir");
        System.out.println("2. Ekspor seluruh data");
        int pilihanData = bacaInt("Pilih sumber data: ");

        DynamicArray<Putusan> sumber;

        if (pilihanData == 1) {
            sumber = hasilTerakhir;
        } else if (pilihanData == 2) {
            sumber = data;
        } else {
            System.out.println("Pilihan tidak tersedia.");
            return;
        }

        if (sumber.isEmpty()) {
            System.out.println("Tidak ada data untuk diekspor.");
            return;
        }

        System.out.println("1. TXT");
        System.out.println("2. CSV");
        int format = bacaInt("Pilih format: ");

        File folderOutput = new File("output");
        if (!folderOutput.exists() && !folderOutput.mkdirs()) {
            System.out.println("Folder output gagal dibuat.");
            return;
        }

        try {
            if (format == 1) {
                String namaFile = "output/hasil-ekspor.txt";
                laporanService.eksporTXT(sumber, namaFile);
                System.out.println("Berhasil mengekspor ke " + namaFile);
                riwayat.push("Ekspor " + sumber.size() + " perkara ke TXT.");
            } else if (format == 2) {
                String namaFile = "output/hasil-ekspor.csv";
                laporanService.eksporCSV(sumber, namaFile);
                System.out.println("Berhasil mengekspor ke " + namaFile);
                riwayat.push("Ekspor " + sumber.size() + " perkara ke CSV.");
            } else {
                System.out.println("Format tidak tersedia.");
            }
        } catch (IOException e) {
            System.out.println("Gagal mengekspor data: " + e.getMessage());
        }
    }

    private static int cariIndexCaseId(String caseId) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getCaseId().equalsIgnoreCase(caseId)) {
                return i;
            }
        }
        return -1;
    }

    private static int cariIndexNomorPerkara(String nomorPerkara) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getNomorPerkara().equalsIgnoreCase(nomorPerkara)) {
                return i;
            }
        }
        return -1;
    }

    private static DynamicArray<Putusan> salinData(DynamicArray<Putusan> sumber) {
        DynamicArray<Putusan> salinan = new DynamicArray<>();

        for (int i = 0; i < sumber.size(); i++) {
            salinan.add(sumber.get(i));
        }

        return salinan;
    }

    private static String bacaTeksWajib(String pesan) {
        while (true) {
            System.out.print(pesan);
            String nilai = input.nextLine().trim();

            if (!nilai.isEmpty()) {
                return nilai;
            }

            System.out.println("Input tidak boleh kosong.");
        }
    }

    private static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Masukkan bilangan bulat yang valid.");
            }
        }
    }

    private static int bacaIntNonNegatif(String pesan) {
        while (true) {
            int nilai = bacaInt(pesan);
            if (nilai >= 0) {
                return nilai;
            }
            System.out.println("Nilai tidak boleh negatif.");
        }
    }

    private static long bacaLongNonNegatif(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                String teks = input.nextLine().trim().replace(".", "").replace(",", "");
                long nilai = Long.parseLong(teks);

                if (nilai >= 0) {
                    return nilai;
                }

                System.out.println("Nilai tidak boleh negatif.");
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka rupiah tanpa simbol Rp.");
            }
        }
    }

    private static double bacaDoubleNonNegatif(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                String teks = input.nextLine().trim().replace(",", ".");
                double nilai = Double.parseDouble(teks);

                if (Double.isFinite(nilai) && nilai >= 0) {
                    return nilai;
                }

                System.out.println("Nilai harus berupa angka dan tidak boleh negatif.");
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }

    private static LocalDate bacaTanggal(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return LocalDate.parse(input.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal harus YYYY-MM-DD, contoh 2026-07-07.");
            }
        }
    }

    private static String aman(String teks) {
        return teks == null || teks.isBlank() ? "Tidak tersedia" : teks;
    }

    private static String potong(String teks, int batas) {
        String nilai = aman(teks);
        if (nilai.length() <= batas) {
            return nilai;
        }
        return nilai.substring(0, batas - 3) + "...";
    }
}
