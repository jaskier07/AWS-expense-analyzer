package pl.kania.expensesCounter.transactionToExpenseMapper.csv.pkobp.parser

import org.apache.commons.csv.CSVRecord
import org.mockito.Mockito
import pl.kania.expensesCounter.commons.dto.TransactionType
import spock.lang.Specification

import java.util.stream.IntStream

import static org.mockito.Mockito.when

class DescriptionParserFacadeTest extends Specification {

    private static final int CSV_FIRST_COLUMN_INDEX = 0
    private static final int CSV_LAST_COLUMN_INDEX = 10

    private DescriptionParserFacade descriptionParser = new DescriptionParserFacade()

    def "parses purchase web mobile code"() {
        given:
        CSVRecord record = getCsvRecordFromLine(line)

        when:
        def result = descriptionParser.parse(record, TransactionType.PURCHASE_WEB_MOBILE_CODE)

        then:
        expectedValue == result

        where:
        expectedValue    || line
        "sinsay.com" || "2022-10-28,2022-10-27,P�atno�� web - kod mobilny,-100.00,PLN,+200.00,Tytu�: 012345,Numer telefonu: +48 123 456 789 ,Lokalizacja: Adres: www.sinsay.com,Operacja: 213456,Numer referencyjny: 135632"
    }

    private CSVRecord getCsvRecordFromLine(String line) {
        CSVRecord record = Mockito.mock(CSVRecord)
        String[] lineValues = line.split(",")

        IntStream.rangeClosed(CSV_FIRST_COLUMN_INDEX, CSV_LAST_COLUMN_INDEX).forEach(index -> {
            when(record.get(index)).thenReturn(lineValues[index])
        })

        return record
    }
}
