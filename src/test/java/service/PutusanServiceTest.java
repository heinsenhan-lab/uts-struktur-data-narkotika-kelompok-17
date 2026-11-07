package service;

import datastructure.DynamicArray;
import model.Putusan;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PutusanServiceTest {

    private Putusan buatPutusan(
            String caseId, String nomor, String pengadilan,
            String narkotika, int vonis) {

        return new Putusan(
                caseId, nomor, pengadilan,
                LocalDate.of(2026, 1, 15),
                "T-001", narkotika, 2.5,
                "Pasal 112", "Pasal 112",
                120, vonis, 100000000L,
                "Barang bukti", "Memberatkan",
                "Meringankan", "Halaman 1"
        );
    }

    private DynamicArray<Putusan> buatData() {
        DynamicArray<Putusan> data = new DynamicArray<>();

        data.add(buatPutusan(
                "CASE-001", "001/Pid.Sus/2026",
                "PN Malang", "Sabu", 96));

        data.add(buatPutusan(
                "CASE-002", "002/Pid.Sus/2026",
                "PN Surabaya", "Ganja", 48));

        data.add(buatPutusan(
                "CASE-003", "003/Pid.Sus/2026",
                "PN Malang", "Sabu", 120));

        return data;
    }

    @Test
    void testSearchByCaseId() {
        PutusanService service = new PutusanService();

        Putusan hasil = service.searchByCaseId(
                buatData(), "CASE-001");

        assertNotNull(hasil);
        assertEquals("CASE-001", hasil.getCaseId());
    }

    @Test
    void testSearchByNomorPerkara() {
        PutusanService service = new PutusanService();

        Putusan hasil = service.searchByNomorPerkara(
                buatData(), "002/Pid.Sus/2026");

        assertNotNull(hasil);
        assertEquals("CASE-002", hasil.getCaseId());
    }

    @Test
    void testSearchNotFound() {
        PutusanService service = new PutusanService();

        Putusan hasil = service.searchByCaseId(
                buatData(), "CASE-999");

        assertNull(hasil);
    }

    @Test
    void testFilterByPengadilan() {
        PutusanService service = new PutusanService();

        DynamicArray<Putusan> hasil = service.filterByPengadilan(
                buatData(), "PN Malang");

        assertEquals(2, hasil.size());
    }

    @Test
    void testFilterByJenisNarkotika() {
        PutusanService service = new PutusanService();

        DynamicArray<Putusan> hasil = service.filterByJenisNarkotika(
                buatData(), "Sabu");

        assertEquals(2, hasil.size());
    }

    @Test
    void testFilterByRentangVonis() {
        PutusanService service = new PutusanService();

        DynamicArray<Putusan> hasil = service.filterByRentangVonis(
                buatData(), 48, 96);

        assertEquals(2, hasil.size());
    }

    @Test
    void testInvalidRentangVonis() {
        PutusanService service = new PutusanService();

        assertThrows(IllegalArgumentException.class,
                () -> service.filterByRentangVonis(
                        buatData(), 120, 48));
    }
}