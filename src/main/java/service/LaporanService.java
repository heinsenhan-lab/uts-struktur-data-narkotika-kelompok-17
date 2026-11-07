package service;

import datastructure.DynamicArray;
import model.Putusan;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LaporanService {

    public int hitungJumlahPerkara(DynamicArray<Putusan> data) {
        return data.size();
    }

    public double hitungRataRataVonis(DynamicArray<Putusan> data) {
        if (data.isEmpty()) {
            return 0;
        }

        int total = 0;

        for (int i = 0; i < data.size(); i++) {
            total += data.get(i).getVonisBulan();
        }

        return (double) total / data.size();
    }

    public long hitungTotalDenda(DynamicArray<Putusan> data) {
        long total = 0;

        for (int i = 0; i < data.size(); i++) {
            total += data.get(i).getDendaRupiah();
        }

        return total;
    }

    public DynamicArray<String> hitungDistribusiJenisNarkotika(
            DynamicArray<Putusan> data) {

        DynamicArray<String> hasil = new DynamicArray<>();

        for (int i = 0; i < data.size(); i++) {
            String jenis = data.get(i).getJenisNarkotika();

            if (jenis == null || jenis.isBlank()) {
                jenis = "Tidak diketahui";
            }

            boolean sudahAda = false;

            for (int j = 0; j < hasil.size(); j++) {
                String[] bagian = hasil.get(j).split("\\|", 2);

                if (bagian[0].equalsIgnoreCase(jenis)) {
                    int jumlah = Integer.parseInt(bagian[1]);
                    hasil.set(j, jenis + "|" + (jumlah + 1));
                    sudahAda = true;
                    break;
                }
            }

            if (!sudahAda) {
                hasil.add(jenis + "|1");
            }
        }

        return hasil;
    }

    public DynamicArray<Putusan> urutkanBerdasarkanVonis(
            DynamicArray<Putusan> data) {

        DynamicArray<Putusan> hasil = new DynamicArray<>();

        for (int i = 0; i < data.size(); i++) {
            hasil.add(data.get(i));
        }

        for (int i = 0; i < hasil.size() - 1; i++) {
            int indeksMinimum = i;

            for (int j = i + 1; j < hasil.size(); j++) {
                if (hasil.get(j).getVonisBulan()
                        < hasil.get(indeksMinimum).getVonisBulan()) {
                    indeksMinimum = j;
                }
            }

            Putusan sementara = hasil.get(i);
            hasil.set(i, hasil.get(indeksMinimum));
            hasil.set(indeksMinimum, sementara);
        }

        return hasil;
    }

    public void eksporTXT(DynamicArray<Putusan> data, String namaFile)
            throws IOException {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(namaFile))) {

            for (int i = 0; i < data.size(); i++) {
                writer.write(data.get(i).toString());
                writer.newLine();
                writer.newLine();
            }
        }
    }

    public void eksporCSV(DynamicArray<Putusan> data, String namaFile)
            throws IOException {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(namaFile))) {

            writer.write("Case ID,Nomor Perkara,Pengadilan,Jenis Narkotika,"
                    + "Berat Barang Bukti,Vonis Bulan,Denda Rupiah");
            writer.newLine();

            for (int i = 0; i < data.size(); i++) {
                Putusan p = data.get(i);

                writer.write(
                        csv(p.getCaseId()) + ","
                                + csv(p.getNomorPerkara()) + ","
                                + csv(p.getPengadilan()) + ","
                                + csv(p.getJenisNarkotika()) + ","
                                + p.getBeratBarangBuktiGram() + ","
                                + p.getVonisBulan() + ","
                                + p.getDendaRupiah()
                );

                writer.newLine();
            }
        }
    }

    private String csv(String nilai) {
        if (nilai == null) {
            return "";
        }

        return "\"" + nilai.replace("\"", "\"\"") + "\"";
    }
}