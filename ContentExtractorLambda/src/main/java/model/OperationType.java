package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationType {
    INCOMING_TRANSFER(new String[]{"PRZELEW PRZYCHODZĄCY"}),
    OUTGOING_TRANSFER("PRZELEW WYCHODZĄCY"),
    PURCHASE_CARD("ZAKUP PRZY UŻYCIU KARTY"),
    PURCHASE_WEB_MOBILE_CODE("PŁATNOŚĆ WEB - KOD MOBILNY"),
    OTHER(new String[]{"PRZELEW PRZYCH. SYSTEMAT. WPŁYW", "ZLECENIE NABYCIA JEDNOSTEK TFI"});

    private final String[] names;

    OperationType(String name) {
        this.names = new String[]{name};
    }
}
