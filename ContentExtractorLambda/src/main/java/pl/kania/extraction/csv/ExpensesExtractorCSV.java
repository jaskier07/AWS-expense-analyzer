package pl.kania.extraction.csv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pl.kania.extraction.model.ParsedExpense;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public abstract class ExpensesExtractorCSV {

    private final CSVFormat csvFormat;
    private final SingleExpenseExtractor extractor;

    public ExpensesExtractorCSV(SingleExpenseExtractor extractor) {
        this.extractor = extractor;
        this.csvFormat = CSVFormat.DEFAULT
                .withHeader(getHeaderValues())
                .withDelimiter(getDelimiter())
                .withFirstRecordAsHeader()
                .withAllowMissingColumnNames();
    }

    protected abstract char getDelimiter();

    protected abstract String[] getHeaderValues();

    public ParsedExpense[] extract(Reader reader) throws IOException {
        log.info("Extracting started...");

        return csvFormat.parse(reader).getRecords()
                .stream()
                .map(extractor::extract)
                .toArray(ParsedExpense[]::new);
    }

    public static void main(String[] args) {
        byte[] eee = Base64.getDecoder().decode("IkRhdGEgb3BlcmFjamkiLCJEYXRhIHdhbHV0eSIsIlR5cCB0cmFuc2FrY2ppIiwiS3dvdGEiLCJXYWx1dGEiLCJTYWxkbyBwbyB0cmFuc2FrY2ppIiwiT3BpcyB0cmFuc2FrY2ppIiwiIiwiIiwiIiwiIg0KIjIwMjEtMTEtMTAiLCIyMDIxLTExLTA4IiwiULNhdG5vnOYga2FydLkiLCItMTUuMjEiLCJQTE4iLCIrMTYyNDg2LjEyIiwiVHl0dbM6IDAwMDAwMzYyNyA1NTQ3Mzg0MTMxMjQ5NjM0NjEwMDc0OCIsIkxva2FsaXphY2phOiBLcmFqOiBQT0xTS0EgTWlhc3RvOiBHREFOU0sgQWRyZXM6IEpNUCBTLkEuIEJJRURST05LQSA0MjAiLCJPcnlnaW5hbG5hIGt3b3RhIG9wZXJhY2ppOiAxNSwyMSBQTE4iLCJOdW1lciBrYXJ0eTogNTE2OTMxKioqKioqMzk3MSIsIiINCiIyMDIxLTExLTEwIiwiMjAyMS0xMS0wOCIsIlCzYXRub5zmIGthcnS5IiwiLTE1LjIwIiwiUExOIiwiKzE2MjUwMS4zMyIsIlR5dHWzOiAwMDAwMTg0NjUgOTUzODM4MjEzMTIwOTk2NzY1ODA1NDIiLCJMb2thbGl6YWNqYTogS3JhajogUE9MU0tBIE1pYXN0bzogR0RBTlNLIEFkcmVzOiBLT05LT0wgU0tMRVAgMjM2IiwiT3J5Z2luYWxuYSBrd290YSBvcGVyYWNqaTogMTUsMjAgUExOIiwiTnVtZXIga2FydHk6IDUxNjkzMSoqKioqKjM5NzEiLCIiDQoiMjAyMS0xMS0xMCIsIjIwMjEtMTEtMTAiLCJQcnplbGV3IG5hIHJhY2h1bmVrIiwiKzEzMDYwLjUzIiwiUExOIiwiKzE2MjUxNi41MyIsIlJhY2h1bmVrIG5hZGF3Y3k6IDA5IDEyODAgMDAwMyAwMDAwIDAwMzAgMTM5MyA1MDAxIiwiTmF6d2EgbmFkYXdjeTogSUhTIEdMT0JBTCBTUC4gWiBPLk8uIFVMLiBNQVJZTkFSS0kgUE9MU0tJRUogMTYzIiwiQWRyZXMgbmFkYXdjeTogODAtODY4IEdEQdFTSy9QTCIsIlR5dHWzOiBXWU5BR1JPRFpFTklFIFpBIFBBWkRaSUVSTklLIDIwMjEiLCIiDQo=");
        new String(eee);
    }
}
