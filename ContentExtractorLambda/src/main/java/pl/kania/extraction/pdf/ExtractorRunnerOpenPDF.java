package pl.kania.extraction.pdf;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import pl.kania.extraction.pdf.extraction.ExpenseContentsExtractor;
import pl.kania.extraction.pdf.extraction.ExpenseContentsExtractorFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.kania.extraction.model.BankType;
import pl.kania.extraction.pdf.parsing.ExpensesParser;
import pl.kania.extraction.pdf.parsing.ExpensesParserFactory;

import java.io.IOException;
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
        ExpenseContentsExtractor extractor = new ExpenseContentsExtractorFactory().get(BankType.PKO_BP);
        List<String> expenseContents = extractor.extract(textExtractor, reader);
//        expenseContents.forEach(log::info);

        ExpensesParser expensesParser = new ExpensesParserFactory().get(BankType.PKO_BP);
        expenseContents.forEach(expensesParser::parse);
    }
}
