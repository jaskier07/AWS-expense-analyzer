package pl.kania.extraction.csv;

import lombok.extern.slf4j.Slf4j;
import pl.kania.extraction.model.BankType;
import pl.kania.extraction.model.ParsedExpense;

import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Slf4j
public class ExtractorRunnerCsv {

    public static final String FILEPATH = "";
    public static final Charset CHARSET = Charset.forName("Windows-1250");

    public static void main(String[] args) {

        try (Reader reader = new FileReader(FILEPATH, CHARSET)){
            ExpensesExtractorCSV extractor = new ExpensesExtractorFactory().get(BankType.PKO_BP);
            Set<ParsedExpense> expenses = extractor.extract(reader);
            expenses.forEach(e -> log.info(e.toString()));
        } catch (Exception e) {
            log.error("Extraction error", e);
        }
    }

}
