package service;

import datastructure.DynamicArray;
import model.Putusan;

public class PutusanService {
    public Putusan searchByCaseId(DynamicArray<Putusan> data, String caseId) {
        for (int i = 0; i < data.size(); i++) {
            Putusan putusan = data.get(i);

            if (putusan.getCaseId() != null
                    && putusan.getCaseId().equalsIgnoreCase(caseId)) {
                return putusan;
            }
        }

        return null;
    }

    public Putusan searchByNomorPerkara(DynamicArray<Putusan> data, String nomorPerkara) {
        for (int i = 0; i < data.size(); i++) {
            Putusan putusan = data.get(i);

            if (putusan.getNomorPerkara() != null
                    && putusan.getNomorPerkara().equalsIgnoreCase(nomorPerkara)) {
                return putusan;
            }
        }

        return null;
    }

    public DynamicArray<Putusan> filterByPengadilan(
            DynamicArray<Putusan> data, String pengadilan) {

        DynamicArray<Putusan> hasil = new DynamicArray<>();

        for (int i = 0; i < data.size(); i++) {
            Putusan putusan = data.get(i);

            if (putusan.getPengadilan() != null
                    && putusan.getPengadilan().equalsIgnoreCase(pengadilan)) {
                hasil.add(putusan);
            }
        }

        return hasil;
    }

    public DynamicArray<Putusan> filterByJenisNarkotika(
            DynamicArray<Putusan> data, String jenisNarkotika) {

        DynamicArray<Putusan> hasil = new DynamicArray<>();

        for (int i = 0; i < data.size(); i++) {
            Putusan putusan = data.get(i);

            if (putusan.getJenisNarkotika() != null
                    && putusan.getJenisNarkotika().equalsIgnoreCase(jenisNarkotika)) {
                hasil.add(putusan);
            }
        }

        return hasil;
    }

    public DynamicArray<Putusan> filterByRentangVonis(
            DynamicArray<Putusan> data, int vonisMinimum, int vonisMaksimum) {

        DynamicArray<Putusan> hasil = new DynamicArray<>();

        if (vonisMinimum < 0 || vonisMaksimum < vonisMinimum) {
            throw new IllegalArgumentException("Rentang vonis tidak valid");
        }

        for (int i = 0; i < data.size(); i++) {
            Putusan putusan = data.get(i);

            if (putusan.getVonisBulan() >= vonisMinimum
                    && putusan.getVonisBulan() <= vonisMaksimum) {
                hasil.add(putusan);
            }
        }

        return hasil;
    }
}