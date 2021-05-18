package pl.kania.extraction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    INCOMING_TRANSFER,
    OUTGOING_TRANSFER,
    PURCHASE_CARD,
    PURCHASE_WEB_MOBILE_CODE,
    OTHER;
}
