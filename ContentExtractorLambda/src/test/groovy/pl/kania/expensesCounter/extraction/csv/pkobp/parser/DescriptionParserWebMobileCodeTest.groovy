package pl.kania.expensesCounter.extraction.csv.pkobp.parser

import org.apache.commons.csv.CSVRecord
import org.mockito.Mockito
import spock.lang.Specification

import static org.mockito.Mockito.when
import static pl.kania.expensesCounter.extraction.csv.pkobp.parser.DescriptionParser.CONTEXT_INDEX_LINE_1

class DescriptionParserWebMobileCodeTest extends Specification {

    def "properly parses description"() {
        given:
        def parser = new DescriptionParserWebMobileCode()
        and:
        CSVRecord record = Mockito.mock(CSVRecord)
        when(record.get(CONTEXT_INDEX_LINE_1)).thenReturn(line)

        when:
        def description = parser.parse(record)

        then:
        expectedDescription == description

        where:
        expectedDescription || line
        "jakdojade.pl"      || "Lokalizacja: Adres: jakdojade.pl"
        "sinsay.com"        || "Lokalizacja: Adres: www.sinsay.com"
    }
}
