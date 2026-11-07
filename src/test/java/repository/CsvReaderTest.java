package repository;

import model.Putusan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadValidCsv() throws IOException {
        String csv = """
                caseId,nomorPerkara,pengadilan,tanggalPutusan,terdakwaKode,jenisNarkotika,beratBarangBuktiGram,pasalDakwaan,pasalTerbukti,tuntutanBulan,vonisBulan,dendaRupiah,barangBukti,keadaanMemberatkan,keadaanMeringankan,sourcePages
                CASE-001,001/Pid.Sus/2026,PN Malang,2026-01-15,T-001,Sabu,2.5,Pasal 112,Pasal 112,120,96,100000000,"1 paket, 2 plastik",Berat,Ringan,Halaman 1
                """;

        Path file = tempDir.resolve("data.csv");
        Files.writeString(file, csv);

        CsvReader reader = new CsvReader();
        var result = reader.readCsv(file.toString());

        assertEquals(1, result.size());

        Putusan putusan = result.get(0);

        assertEquals("CASE-001", putusan.getCaseId());
        assertEquals("PN Malang", putusan.getPengadilan());
        assertEquals("1 paket, 2 plastik", putusan.getBarangBukti());
    }

    @Test
    void testSkipInvalidCsvRow() throws IOException {
        String csv = """
                caseId,nomorPerkara,pengadilan,tanggalPutusan,terdakwaKode,jenisNarkotika,beratBarangBuktiGram,pasalDakwaan,pasalTerbukti,tuntutanBulan,vonisBulan,dendaRupiah,barangBukti,keadaanMemberatkan,keadaanMeringankan,sourcePages
                CASE-001,001/Pid.Sus/2026,PN Malang,2026-01-15,T-001,Sabu,2.5,Pasal 112,Pasal 112,120,96,100000000,Bukti,Berat,Ringan,Halaman 1
                BARIS TIDAK VALID
                """;

        Path file = tempDir.resolve("invalid.csv");
        Files.writeString(file, csv);

        CsvReader reader = new CsvReader();
        var result = reader.readCsv(file.toString());

        assertEquals(1, result.size());
    }

    @Test
    void testReadEmptyCsv() throws IOException {
        String csv = """
                caseId,nomorPerkara,pengadilan,tanggalPutusan,terdakwaKode,jenisNarkotika,beratBarangBuktiGram,pasalDakwaan,pasalTerbukti,tuntutanBulan,vonisBulan,dendaRupiah,barangBukti,keadaanMemberatkan,keadaanMeringankan,sourcePages
                """;

        Path file = tempDir.resolve("empty.csv");
        Files.writeString(file, csv);

        CsvReader reader = new CsvReader();
        var result = reader.readCsv(file.toString());

        assertTrue(result.isEmpty());
    }
}