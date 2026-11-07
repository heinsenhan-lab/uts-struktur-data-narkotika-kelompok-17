package repository;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PdfReaderTest {

    @Test
    void testReadPdf() throws IOException {
        PdfReader reader = new PdfReader();

        String text = reader.readPdf(
                "data/putusan_104_pid.sus_2026_pn_grt_20261105013756.pdf"
        );

        // Menguji apakah PDF berhasil dibaca
        assertNotNull(text);
        assertFalse(text.isBlank());

        System.out.println("PDF berhasil dibaca.");
        System.out.println("Jumlah karakter: " + text.length());

        // Menguji ekstraksi vonis
        String vonis = reader.extractSection(
                text,
                "M E N G A D I L I :",
                "Demikianlah diputuskan"
        );

        System.out.println("\n=== HASIL EKSTRAKSI VONIS ===");
        System.out.println(vonis);

        assertNotNull(vonis);
        assertFalse(vonis.isBlank());
        assertTrue(vonis.contains("3 (tiga) tahun"));
        assertTrue(vonis.contains("Rp1.000.000.000"));

        // Menguji ekstraksi pasal
        String pasal = reader.extractSection(
                text,
                "Pasal 114",
                "Menimbang"
        );

        System.out.println("\n=== HASIL EKSTRAKSI PASAL ===");
        System.out.println(pasal);

        assertNotNull(pasal);
        assertFalse(pasal.isBlank());

        System.out.println("\nSemua pengujian PdfReader berhasil.");
    }
}