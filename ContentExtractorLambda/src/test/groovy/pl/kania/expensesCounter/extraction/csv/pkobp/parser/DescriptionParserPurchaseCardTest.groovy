package pl.kania.expensesCounter.extraction.csv.pkobp.parser

import org.apache.commons.csv.CSVRecord
import org.mockito.Mockito
import spock.lang.Specification

import static org.mockito.Mockito.when
import static pl.kania.expensesCounter.extraction.csv.pkobp.parser.DescriptionParser.CONTEXT_INDEX_LINE_0

class DescriptionParserPurchaseCardTest extends Specification {

    def "properly parses description"() {
        given:
        def parser = new DescriptionParserPurchaseCard()
        and:
        CSVRecord record = Mockito.mock(CSVRecord)
        when(record.get(CONTEXT_INDEX_LINE_0)).thenReturn(line)

        when:
        def description = parser.parse(record)

        then:
        expectedDescription == description

        where:
        expectedDescription             || line
        "zabka z6973 k.1"               || "Lokalizacja: Kraj: POLSKA Miasto: GDANSK Adres: ZABKA Z6973 K.1"
        "free-now.com freenow*7pee96-2" || "Lokalizacja: Kraj: POLSKA Miasto: FREE-NOW.COM Adres: FREENOW*7PEE96-2"
        "slony spichlerz"               || "Lokalizacja: Kraj: POLSKA Miasto: GDANSK Adres: SLONY SPICHLERZ"
        "super-pharm gdansk balt"       || "Lokalizacja: Kraj: POLSKA Miasto: GDANSK Adres: SUPER-PHARM GDANSK BALT"
    }

}
