package pl.kania.expensesCounter.extraction.csv.pkobp.parser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import pl.kania.expensesCounter.commons.dto.TransactionType;

import static pl.kania.expensesCounter.extraction.csv.pkobp.parser.DescriptionParser.*;

@Slf4j
@AllArgsConstructor
public class DescriptionParserFacade {

    private final DescriptionParserWebMobileCode parserWebMobileCode;
    private final DescriptionParserOther parserOther;
    private final DescriptionParserIncomingTransfer parserIncomingTransfer;
    private final DescriptionParserOutgoingTransfer parserOutgoingTransfer;
    private final DescriptionParserPurchaseCard parserPurchaseCard;

    public DescriptionParserFacade() {
        this.parserWebMobileCode = new DescriptionParserWebMobileCode();
        this.parserOther = new DescriptionParserOther();
        this.parserIncomingTransfer = new DescriptionParserIncomingTransfer();
        this.parserOutgoingTransfer = new DescriptionParserOutgoingTransfer();
        this.parserPurchaseCard = new DescriptionParserPurchaseCard();
    }

    public String parse(CSVRecord record, TransactionType transactionType) {
        switch (transactionType) {
            case PURCHASE_WEB_MOBILE_CODE:
                return parserWebMobileCode.parse(record);
            case PURCHASE_CARD:
                return parserPurchaseCard.parse(record);
            case OUTGOING_TRANSFER:
                return parserOutgoingTransfer.parse(record);
            case INCOMING_TRANSFER:
                return parserIncomingTransfer.parse(record);
            case OTHER:
                return parserOther.parse(record);
            default:
                log.warn("Unknown transaction type {}, extracting whole description", transactionType);
                return record.get(CONTEXT_INDEX_LINE_0) + ' ' + record.get(CONTEXT_INDEX_LINE_1)
                        + ' ' + record.get(CONTEXT_INDEX_LINE_2) + ' ' + record.get(CONTEXT_INDEX_LINE_3);
        }
    }

}
