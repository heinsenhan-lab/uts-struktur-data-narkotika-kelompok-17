package service;

import datastructure.DynamicArray;
import model.Putusan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LaporanServiceTest {

    private LaporanService laporanService;
    private DynamicArray<Putusan> data;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        laporanService = new LaporanService();
        data = new DynamicArray<>();

        data.add(buatPutusan("P001", "Sabu", 24, 5000000));
        data.add(buatPutusan("P002", "Ganja", 48, 10000000));
        data.add(buatPutusan("P003", "Sabu", 36, 7500000));
    }

    private Putusan buatPutusan(
            String caseId, String jenis, int vonis, long denda) {

        return new Putusan(
                caseId,
                "123/Pid.Sus/2026/PN.Mlg",
                "Pengadilan Negeri Malang",
                LocalDate.of(2026, 1, 1),
                "Terdakwa " + caseId,
                jenis,
                10.0,
                "Pasal 112",
                "Pasal 112",
                60,
                vonis,
                denda,
                "Barang bukti",
                "Memberatkan",
                "Meringankan",
                "1-5"
        );
    }

    @Test
    void testHitungJumlahPerkara() {
        assertEquals(3, laporanService.hitungJumlahPerkara(data));
    }

    @Test
    void testHitungRataRataVonis() {
        assertEquals(36.0, laporanService.hitungRataRataVonis(data));
    }

    @Test
    void testRataRataVonisDataKosong() {
        assertEquals(
                0,
                laporanService.hitungRataRataVonis(new DynamicArray<>())
        );
    }

    @Test
    void testHitungTotalDenda() {
        assertEquals(22500000L, laporanService.hitungTotalDenda(data));
    }

    @Test
    void testHitungDistribusiJenisNarkotika() {
        DynamicArray<String> hasil =
                laporanService.hitungDistribusiJenisNarkotika(data);

        assertEquals(2, hasil.size());
        assertEquals("Sabu|2", hasil.get(0));
        assertEquals("Ganja|1", hasil.get(1));
    }

    @Test
    void testUrutkanBerdasarkanVonis() {
        DynamicArray<Putusan> hasil =
                laporanService.urutkanBerdasarkanVonis(data);

        assertEquals(24, hasil.get(0).getVonisBulan());
        assertEquals(36, hasil.get(1).getVonisBulan());
        assertEquals(48, hasil.get(2).getVonisBulan());
    }

    @Test
    void testPengurutanTidakMengubahDataUtama() {
        laporanService.urutkanBerdasarkanVonis(data);

        assertEquals(24, data.get(0).getVonisBulan());
        assertEquals(48, data.get(1).getVonisBulan());
        assertEquals(36, data.get(2).getVonisBulan());
    }

    @Test
    void testEksporTXT() throws IOException {
        Path file = tempDir.resolve("hasil.txt");

        laporanService.eksporTXT(data, file.toString());

        assertTrue(Files.exists(file));
        assertTrue(Files.readString(file).contains("P001"));
    }

    @Test
    void testEksporCSV() throws IOException {
        Path file = tempDir.resolve("laporan.csv");

        laporanService.eksporCSV(data, file.toString());

        String isi = Files.readString(file);

        assertTrue(Files.exists(file));
        assertTrue(isi.contains("Case ID,Nomor Perkara"));
        assertTrue(isi.contains("P001"));
        assertTrue(isi.contains("5000000"));
    }
}
