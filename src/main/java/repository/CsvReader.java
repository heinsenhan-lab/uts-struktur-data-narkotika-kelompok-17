package repository;

import datastructure.DynamicArray;
import model.Putusan;
import java.time.DateTimeException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {
    public DynamicArray<Putusan> readCsv(String filePath) throws IOException {
        DynamicArray<Putusan> data = new DynamicArray<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1;

            reader.readLine();

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                try {
                    String[] fields = parseCsvLine(line);
                    Putusan putusan = parsePutusan(fields);

                    data.add(putusan);

                } catch (IllegalArgumentException | DateTimeException e) {
                    System.err.println(
                            "Baris " + lineNumber + " dilewati: " + e.getMessage()
                    );
                }
            }
        }

        return data;
    }
    private String[] parseCsvLine(String line) {
        String[] fields = new String[16];
        StringBuilder field = new StringBuilder();

        int fieldIndex = 0;
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (insideQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    field.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (c == ',' && !insideQuotes) {
                if (fieldIndex >= 16) {
                    throw new IllegalArgumentException("Kolom CSV terlalu banyak");
                }

                fields[fieldIndex++] = field.toString().trim();
                field.setLength(0);
            } else {
                field.append(c);
            }
        }

        if (insideQuotes || fieldIndex != 15) {
            throw new IllegalArgumentException("Format baris CSV tidak valid");
        }

        fields[fieldIndex] = field.toString().trim();

        return fields;
    }

    private Putusan parsePutusan(String[] fields) {
        String caseId = fields[0].isEmpty() ? null : fields[0];
        String nomorPerkara = fields[1].isEmpty() ? null : fields[1];
        String pengadilan = fields[2].isEmpty() ? null : fields[2];

        java.time.LocalDate tanggalPutusan = fields[3].isEmpty()
                ? null
                : java.time.LocalDate.parse(fields[3]);

        String terdakwaKode = fields[4].isEmpty() ? null : fields[4];
        String jenisNarkotika = fields[5].isEmpty() ? null : fields[5];

        double beratBarangBuktiGram = fields[6].isEmpty()
                ? 0
                : Double.parseDouble(fields[6]);

        String pasalDakwaan = fields[7].isEmpty() ? null : fields[7];
        String pasalTerbukti = fields[8].isEmpty() ? null : fields[8];

        int tuntutanBulan = fields[9].isEmpty()
                ? 0
                : Integer.parseInt(fields[9]);

        int vonisBulan = fields[10].isEmpty()
                ? 0
                : Integer.parseInt(fields[10]);

        long dendaRupiah = fields[11].isEmpty()
                ? 0
                : Long.parseLong(fields[11]);

        String barangBukti = fields[12].isEmpty() ? null : fields[12];
        String keadaanMemberatkan = fields[13].isEmpty() ? null : fields[13];
        String keadaanMeringankan = fields[14].isEmpty() ? null : fields[14];
        String sourcePages = fields[15].isEmpty() ? null : fields[15];

        return new Putusan(
                caseId,
                nomorPerkara,
                pengadilan,
                tanggalPutusan,
                terdakwaKode,
                jenisNarkotika,
                beratBarangBuktiGram,
                pasalDakwaan,
                pasalTerbukti,
                tuntutanBulan,
                vonisBulan,
                dendaRupiah,
                barangBukti,
                keadaanMemberatkan,
                keadaanMeringankan,
                sourcePages
        );
    }

}