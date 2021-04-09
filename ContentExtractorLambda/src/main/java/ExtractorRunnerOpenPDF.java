import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import extraction.ExpenseContentExtractorType;
import extraction.ExpenseContentsExtractor;
import extraction.ExpenseContentsExtractorFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Getter
@Slf4j
public class ExtractorRunnerOpenPDF {

    public static void main(String... args) throws IOException {
        Path path = Paths.get("");
        byte[] bytes = Files.readAllBytes(path);

        try (PdfReader pdfReader = new PdfReader(bytes)) {
            PdfTextExtractor extractor = new PdfTextExtractor(pdfReader);
            printExpenseContents(extractor, pdfReader);
        } catch (Exception e) {
            log.error("Error reading file", e);
        }
    }

    private static void printExpenseContents(PdfTextExtractor textExtractor, PdfReader reader) {
        ExpenseContentsExtractor extractor = new ExpenseContentsExtractorFactory().get(ExpenseContentExtractorType.PKO_BP);
        List<String> expenseContents = extractor.extract(textExtractor, reader);
        expenseContents.forEach(log::info);
    }
}
