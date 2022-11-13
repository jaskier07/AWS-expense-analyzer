package pl.kania.expensesCounter.extraction.csv.pkobp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import pl.kania.expensesCounter.commons.dto.TransactionType;

@Slf4j
class DescriptionParser {
    public static final int CONTEXT_INDEX_TRANSACTION_TYPE = 2;
    public static final int CONTEXT_INDEX_TRANSACTION_DESCRIPTION = 6;
    public static final int CONTEXT_INDEX_LINE_0 = 7;
    public static final int CONTEXT_INDEX_LINE_1 = 8;
    public static final int CONTEXT_INDEX_LINE_2 = 9;
    public static final int CONTEXT_INDEX_LINE_3 = 10;
    private static final String SEPARATOR = " ; ";
    private static final String EMPTY_STRING = "";

    String parse(CSVRecord record, TransactionType transactionType) {
        String description;
        switch (transactionType) {
            case PURCHASE_WEB_MOBILE_CODE:
                description = record.get(CONTEXT_INDEX_LINE_1);
                return extractShopUrl(description);
            case PURCHASE_CARD:
                description = record.get(CONTEXT_INDEX_LINE_0);
                return extractShop(description);
            case OUTGOING_TRANSFER:
                description = record.get(CONTEXT_INDEX_LINE_0);
                return extractReceiverName(description);
            case INCOMING_TRANSFER:
                description = record.get(CONTEXT_INDEX_LINE_0);
                description += SEPARATOR + record.get(CONTEXT_INDEX_LINE_2);
                return extractSender(description);
            case OTHER:
                description = record.get(CONTEXT_INDEX_TRANSACTION_TYPE);
                description += " ; " + record.get(CONTEXT_INDEX_TRANSACTION_DESCRIPTION);
                return description;
            default:
                log.warn("Unknown transaction type {}, extracting whole description", transactionType);
                return record.get(CONTEXT_INDEX_LINE_0) + ' ' + record.get(CONTEXT_INDEX_LINE_1)
                        + ' ' + record.get(CONTEXT_INDEX_LINE_2) + ' ' + record.get(CONTEXT_INDEX_LINE_3);
        }
    }

    private String extractShopUrl(String description) {
        if (description == null) {
            return EMPTY_STRING;
        }
        description = description.replaceAll("Lokalizacja: Adres:", EMPTY_STRING);
        description = description.replaceAll("(http://)|(https://)|(www.)", EMPTY_STRING);
        return description.trim();
    }

    private String extractShop(String description) {
        if (description == null) {
            return EMPTY_STRING;
        }
        String[] split = description.split("Adres:");
        if (split.length > 1) {
            return split[1].trim();
        }
        return EMPTY_STRING;
    }

    private String extractReceiverName(String description) {
        if (description == null) {
            return EMPTY_STRING;
        }
        return description.replaceAll("Nazwa odbiorcy:", EMPTY_STRING).trim();
    }

    private String extractSender(String description) {
        if (description == null) {
            return EMPTY_STRING;
        }
        return description.replaceAll("Nazwa nadawcy:", EMPTY_STRING).trim();
    }

}
