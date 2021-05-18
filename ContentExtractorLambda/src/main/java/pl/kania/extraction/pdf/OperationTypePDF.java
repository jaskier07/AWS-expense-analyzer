package pl.kania.extraction.pdf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.kania.extraction.model.TransactionType;

@Getter
@AllArgsConstructor
public enum OperationTypePDF {
    INCOMING_TRANSFER(new String[]{"PRZELEW PRZYCHODZĄCY"}),
    OUTGOING_TRANSFER("PRZELEW WYCHODZĄCY"),
    PURCHASE_CARD("ZAKUP PRZY UŻYCIU KARTY"),
    PURCHASE_WEB_MOBILE_CODE("PŁATNOŚĆ WEB - KOD MOBILNY"),
    OTHER(new String[]{"PRZELEW PRZYCH. SYSTEMAT. WPŁYW", "ZLECENIE NABYCIA JEDNOSTEK TFI"});

    private final String[] names;

    OperationTypePDF(String name) {
        this.names = new String[]{name};
    }

    public TransactionType toTransactionType() {
        switch (this) {
            case PURCHASE_WEB_MOBILE_CODE:
                return TransactionType.PURCHASE_WEB_MOBILE_CODE;
            case OUTGOING_TRANSFER:
                return TransactionType.OUTGOING_TRANSFER;
            case PURCHASE_CARD:
                return TransactionType.PURCHASE_CARD;
            case OTHER:
                return TransactionType.OTHER;
            case INCOMING_TRANSFER:
                return TransactionType.INCOMING_TRANSFER;
            default:
                throw new IllegalStateException("Unknown TransactionType: " + this);
        }
    }
}
