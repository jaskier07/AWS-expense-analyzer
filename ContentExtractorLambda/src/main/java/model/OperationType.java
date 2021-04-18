package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationType {
    INCOMING_TRANSFER(new String[]{"PRZELEW PRZYCHODZ¥CY"}),
    OUTGOING_TRANSFER("PRZELEW WYCHODZ¥CY"),
    PURCHASE_CARD("ZAKUP PRZY UŻYCIU KARTY"),
    PURCHASE_WEB_MOBILE_CODE("P£ATNOŚĆ WEB - KOD MOBILNY"),
    OTHER(new String[]{"PRZELEW PRZYCH. SYSTEMAT. WP£YW", "ZLECENIE NABYCIA JEDNOSTEK TFI"});

    private final String[] names;

    OperationType(String name) {
        this.names = new String[]{name};
    }
}
