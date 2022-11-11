package pl.kania.expensesCounter.expenseMappingsInserter;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;

import java.io.Reader;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static pl.kania.expensesCounter.expenseMappingsInserter.ExpenseMappingsCsvParser.CsvHeader.*;

@Slf4j
public class ExpenseMappingsCsvParser {

    private static final String DELIMITER = ",";
    private final CSVFormat csvFormat;


    public ExpenseMappingsCsvParser() {
        this.csvFormat = CSVFormat.Builder.create()
                .setHeader(CsvHeader.class)
                .setDelimiter(DELIMITER)
                .setAllowMissingColumnNames(true)
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .build();
    }

    public List<ExpenseMapping> parseCsv(Reader reader) {
        log.info("Parsing started...");

        return Try.of(() -> csvFormat.parse(reader).getRecords()
                .stream()
                .map(this::parseRecord)
                .collect(toList()))
                .onFailure(e -> log.error("Error parsing CSV file", e))
                .onSuccess(mappings -> log.info("Parsed mappings: {}", mappings))
                .getOrNull();
    }

    private ExpenseMapping parseRecord(CSVRecord record) {
        ExpenseMapping mapping = new ExpenseMapping();
        mapping.setName(parseCsvValue(NAME, record));
        mapping.setExpenseCategory(parseCsvValue(EXPENSE_TYPE, record));
        mapping.setLogicalName(parseCsvValue(LOGICAL_NAME, record));
        mapping.setMappingType(parseCsvValue(MAPPING_TYPE, record));
        mapping.setExpenseTypeSubcategory(parseCsvValue(SUBCATEGORY, record));
        return mapping;
    }

    private String parseCsvValue(CsvHeader header, CSVRecord record) {
        return toLowerCaseNullSafe(record.get(header));
    }

    private String toLowerCaseNullSafe(String value) {
        return Optional.ofNullable(value)
                .map(String::toLowerCase)
                .orElse(null);
    }

    enum CsvHeader {
        NAME,
        MAPPING_TYPE,
        EXPENSE_TYPE,
        SUBCATEGORY,
        LOGICAL_NAME
    }
}
