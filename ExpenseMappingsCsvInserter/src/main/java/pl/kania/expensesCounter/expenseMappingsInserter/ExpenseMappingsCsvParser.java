package pl.kania.expensesCounter.expenseMappingsInserter;

import io.vavr.control.Try;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;
import pl.kania.expensesCounter.commons.dto.db.MappingType;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Slf4j
public class ExpenseMappingsCsvParser {

    private static final String[] HEADER = new String[]{"name","mapping_type","expense_type","subcategory","logical_name"};
    private static final String DELIMITER = ",";
    private static final boolean ALLOW_MISSING_COLUMN_NAMES = true;
    private final CSVFormat csvFormat;


    public ExpenseMappingsCsvParser() {
        this.csvFormat = CSVFormat.Builder.create()
                .setHeader(HEADER)
                .setDelimiter(DELIMITER)
                .setAllowMissingColumnNames(ALLOW_MISSING_COLUMN_NAMES)
                .build();
    }

    public List<ExpenseMapping> parseCsv(Reader reader) {
        log.info("Parsing started...");

        return Try.of(() -> csvFormat.parse(reader).getRecords()
                .stream()
                .map(this::parseRecord)
                .collect(Collectors.toList()))
                .onFailure(e -> log.error("Error parsing CSV file", e))
                .getOrNull();
    }

    private ExpenseMapping parseRecord(CSVRecord record) {
        ExpenseMapping mapping = new ExpenseMapping();
        mapping.setName(record.get("name"));
        mapping.setExpenseType(record.get("expense_type"));
        mapping.setLogicalName(record.get("logical_name"));
        mapping.setMappingType(MappingType.parse(record.get("mapping_type")));
        mapping.setExpenseTypeSubcategory(record.get("subcategory"));
        return new ExpenseMapping();
    }
}
