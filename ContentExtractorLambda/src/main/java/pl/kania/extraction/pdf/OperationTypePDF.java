package pl.kania.extraction.pdf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.kania.extraction.model.TransactionType;
import pl.kania.extraction.pdf.parsing.*;

@Getter
@AllArgsConstructor
public enum OperationTypePDF {
    INCOMING_TRANSFER(
            new String[]{"PRZELEW PRZYCHODZĄCY", "PRZELEW PRZYCH. SYSTEMAT. WPŁYW", "PRZELEW NA TELEFON PRZYCHODZ. ZEW."},
            new IncomingTransferExpenseParser()
    ),
    OUTGOING_TRANSFER(
            new String[]{"PRZELEW WYCHODZĄCY", "PRZELEW NA TELEFON WYCHODZĄCY WEW."},
            new OutgoingTransferExpenseParser()
    ),
    PURCHASE_CARD(
            new String[]{"ZAKUP PRZY UŻYCIU KARTY"},
            new CardPurchaseExpenseParser()
    ),
    PURCHASE_WEB_MOBILE_CODE(
            new String[]{"PŁATNOŚĆ WEB - KOD MOBILNY", },
            new MobileCodeExpenseParser()
    ),
    OTHER(
            new String[]{"ZLECENIE NABYCIA JEDNOSTEK TFI"},
            new OtherExpenseParser()
    );

    private final String[] names;
    private final ExpenseParserBasedOnOperationType parser;

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
