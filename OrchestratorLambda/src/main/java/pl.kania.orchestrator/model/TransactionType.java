package pl.kania.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    INCOMING_TRANSFER,
    OUTGOING_TRANSFER,
    PURCHASE_CARD,
    PURCHASE_WEB_MOBILE_CODE,
    WITHDRAWAL,
    OTHER;
}
