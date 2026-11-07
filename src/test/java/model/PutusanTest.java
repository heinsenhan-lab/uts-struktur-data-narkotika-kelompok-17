package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PutusanTest {

    @Test
    void testConstructorValid() {
        Putusan putusan = new Putusan(
                "P001",
                "12/Pid.Sus/2025/PN Mlg",
                "Pengadilan Negeri Malang",
                LocalDate.of(2025, 1, 10),
                "Terdakwa 001",
                "Sabu",
                2.5,
                "Pasal 112",
                "Pasal 112",
                60,
                48,
                100000000L,
                "Kristal putih",
                "Memberatkan",
                "Meringankan",
                "Halaman 1-5"
        );

        assertEquals("P001", putusan.getCaseId());
        assertEquals("Sabu", putusan.getJenisNarkotika());
        assertEquals(48, putusan.getVonisBulan());
    }

    @Test
    void testConstructorRejectsNegativeWeight() {
        assertThrows(IllegalArgumentException.class, () ->
                new Putusan(
                        "P002", "13/Pid.Sus/2025/PN Mlg",
                        "Pengadilan Negeri Malang",
                        LocalDate.of(2025, 1, 10),
                        "Terdakwa 002", "Ganja", -1,
                        "Pasal 111", "Pasal 111",
                        60, 48, 100000000L,
                        "Daun kering", "", "", "Halaman 1-5"
                )
        );
    }

    @Test
    void testConstructorRejectsEmptyCaseId() {
        assertThrows(IllegalArgumentException.class, () ->
                new Putusan(
                        "", "14/Pid.Sus/2025/PN Mlg",
                        "Pengadilan Negeri Malang",
                        LocalDate.of(2025, 1, 10),
                        "Terdakwa 003", "Ekstasi", 1,
                        "Pasal 112", "Pasal 112",
                        60, 48, 100000000L,
                        "Tablet", "", "", "Halaman 1-5"
                )
        );
    }
}