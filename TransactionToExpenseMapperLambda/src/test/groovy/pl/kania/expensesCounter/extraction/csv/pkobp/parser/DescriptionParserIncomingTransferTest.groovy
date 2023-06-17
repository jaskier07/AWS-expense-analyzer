package pl.kania.expensesCounter.transactionToExpenseMapper.csv.pkobp.parser

import org.apache.commons.csv.CSVRecord
import org.mockito.Mockito
import spock.lang.Specification

import static org.mockito.Mockito.when
import static pl.kania.expensesCounter.transactionToExpenseMapper.csv.pkobp.parser.DescriptionParser.CONTEXT_INDEX_LINE_0

public class DescriptionParserIncomingTransferTest extends Specification {

    def "properly parses description"() {
        given:
        def parser = new DescriptionParserIncomingTransfer()
        and:
        CSVRecord record = Mockito.mock(CSVRecord)
        when(record.get(CONTEXT_INDEX_LINE_0)).thenReturn(line0)
        when(record.get(DescriptionParser.CONTEXT_INDEX_LINE_2)).thenReturn(line2)

        when:
        def description = parser.parse(record)

        then:
        expectedDescription == description

        where:
        expectedDescription        || line0                           || line2
        "katarzyna t banany"     || "Nazwa nadawcy: KATARZYNA T"    || "Tytu�: BANANY"
        "aleksander olo za hajs" || "Nazwa nadawcy: ALEKSANDER OLO" || "Tytu�: ZA HAJS"
    }
}