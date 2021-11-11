package pl.kania.extraction.csv.pkobp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.kania.extraction.model.TransactionType;

import java.util.Arrays;

@Slf4j
@Getter
@AllArgsConstructor
public enum TransactionTypeCSV_PKOBP {
    INCOMING_TRANSFER(new String[] {"Przelew na rachunek", "Przelew na telefon przychodz. zew.", "Zwrot w terminalu", "Zwrot płatności kartą"}),
    OUTGOING_TRANSFER(new String[]{"Przelew z rachunku", "Przelew na telefon wychodzący zew."}),
    PURCHASE_CARD("Płatność kartą"),
    PURCHASE_WEB_MOBILE_CODE(new String[]{"Płatność web - kod mobilny", "Zakup w terminalu - kod mobilny"}),
    OTHER(new String[]{"Przelew Paybynet"});

    private final String[] names;

    TransactionTypeCSV_PKOBP(String name) {
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
            case INCOMING_TRANSFER:
                return TransactionType.INCOMING_TRANSFER;
            default:
                log.warn("Unknown transaction type: " + this);
                return TransactionType.OTHER;
        }
    }

    public static TransactionTypeCSV_PKOBP from(String name) {
        return Arrays.stream(values())
                .filter(type -> Arrays.asList(type.names).contains(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown transaction type: " + name));
    }
}