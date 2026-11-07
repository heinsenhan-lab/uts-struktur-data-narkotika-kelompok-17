package repository;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfReader {

    public String readPdf(String filePath) throws IOException {
        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
    public String extractSection(String text, String startKeyword, String endKeyword) {
        int start = text.indexOf(startKeyword);

        if (start == -1) {
            return "";
        }

        int end = text.indexOf(endKeyword, start);

        if (end == -1) {
            end = text.length();
        }

        return text.substring(start, end).trim();
    }
}