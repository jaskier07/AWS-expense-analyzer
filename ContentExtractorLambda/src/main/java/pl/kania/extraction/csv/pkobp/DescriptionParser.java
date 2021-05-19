package pl.kania.extraction.csv.pkobp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import pl.kania.extraction.model.TransactionType;

@Slf4j
class DescriptionParser {

    public static final int CONTEXT_INDEX_LINE_0 = 7;
    public static final int CONTEXT_INDEX_LINE_1 = 8;
    public static final int CONTEXT_INDEX_LINE_2 = 9;
    public static final int CONTEXT_INDEX_LINE_3 = 10;
    public static final String EMPTY_DESCRIPTION = "";

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
                return extractSender(description);
            default:
                log.warn("Unknown transaction type {}, extracting whole description", transactionType);
                return record.get(CONTEXT_INDEX_LINE_0) + ' ' + record.get(CONTEXT_INDEX_LINE_1)
                        + ' ' + record.get(CONTEXT_INDEX_LINE_2) + ' ' + record.get(CONTEXT_INDEX_LINE_3);
        }
    }

    private String extractShopUrl(String description) {
        if (description == null) {
            return EMPTY_DESCRIPTION;
        }
        description = description.replaceAll("Lokalizacja: Adres:", "");
        description = description.replaceAll("(http://)|(https://)|(www)", "");
        return description.trim();
    }

    private String extractShop(String description) {
        if (description == null) {
            return EMPTY_DESCRIPTION;
        }
        String[] split = description.split("Adres:");
        if (split.length > 0) {
            return split[1].trim();
        }
        return EMPTY_DESCRIPTION;
    }

    private String extractReceiverName(String description) {
        if (description == null) {
            return EMPTY_DESCRIPTION;
        }
        return description.replaceAll("Nazwa odbiorcy:", "").trim();
    }

    private String extractSender(String description) {
        if (description == null) {
            return EMPTY_DESCRIPTION;
        }
        return description.replaceAll("Nazwa nadawcy:", "").trim();
    }

}
