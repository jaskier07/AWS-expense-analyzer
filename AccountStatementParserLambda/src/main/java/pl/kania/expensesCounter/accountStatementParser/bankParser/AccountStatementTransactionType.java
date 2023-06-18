package pl.kania.expensesCounter.accountStatementParser.bankParser;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.accountStatementParser.util.TextEncodingMapper;
import pl.kania.expensesCounter.commons.dto.BankType;
import pl.kania.expensesCounter.commons.dto.TransactionType;

import java.util.Arrays;

@Slf4j
@Getter
public enum AccountStatementTransactionType {
    INCOMING_TRANSFER(
            new String[]{"Przelew na rachunek", "Przelew na telefon przychodz. zew.", "Zwrot w terminalu", "Zwrot płatności kartą", "Przelew Natychmiastowy na rachunek"},
            new String[]{"Przelew przychodzďż˝cy zewnďż˝trzny"},
            new String[]{},
            TransactionType.INCOMING_TRANSFER
    ),
    OUTGOING_TRANSFER(
            new String[]{"Przelew z rachunku", "Przelew na telefon wychodzący zew.", "Przelew na telefon wychodzący wew.", "Zlecenie stałe", "Przelew z karty"},
            new String[]{"Przelew wychodzďż˝cy zewnďż˝trzny"},
            new String[]{},
            TransactionType.OUTGOING_TRANSFER
    ),
    PURCHASE_CARD(
            new String[]{"Płatność kartą", "Obciążenie"},
            new String[]{}, // no card
            new String[]{},
            TransactionType.PURCHASE_CARD
    ),
    PURCHASE_WEB_MOBILE_CODE(
            new String[]{"Płatność web - kod mobilny", "Zakup w terminalu - kod mobilny"},
            new String[]{}, // no blik
            new String[]{},
            TransactionType.PURCHASE_WEB_MOBILE_CODE
    ),
    WITHDRAWAL(
            new String[]{"Wypłata z bankomatu"},
            new String[]{}, // no card
            new String[]{},
            TransactionType.WITHDRAWAL
    ),
    BANK_PAYMENTS(
            new String[]{"Spłata kredytu"},
            new String[]{"Pobranie opďż˝aty"},
            new String[]{},
            TransactionType.OUTGOING_TRANSFER
    ),
    OTHER(
            new String[]{"Przelew Paybynet"},
            new String[]{"Naliczenie odsetek"},
            new String[]{},
            TransactionType.OTHER
    );

    private final String[] pkoBpNames;
    private final String[] toyotaBankNames;
    private final String[] bnpParibasNames;
    private final TransactionType transactionType;

    AccountStatementTransactionType(String[] pkoBpNames, String[] bnpParibasNames, String[] toyotaBankNames, TransactionType transactionType) {
        TextEncodingMapper mapper = new TextEncodingMapper();
        this.pkoBpNames = mapper.mapToWindows1250(pkoBpNames);
        this.bnpParibasNames = mapper.mapToWindows1250(bnpParibasNames);
        this.toyotaBankNames = mapper.mapToWindows1250(toyotaBankNames);
        this.transactionType = transactionType;
    }

    public static AccountStatementTransactionType from(String name, BankType bankType) {
        return Arrays.stream(values())
                .filter(transactionType -> Arrays.asList(getTransactionTypeNames(bankType, transactionType)).contains(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown transaction type: " + name));
    }

    private static String[] getTransactionTypeNames(BankType bankType, AccountStatementTransactionType transactionType) {
        return switch (bankType) {
            case PKO_BP -> transactionType.pkoBpNames;
            case BNP_PARIBAS -> transactionType.bnpParibasNames;
            case TOYOTA_BANK -> transactionType.toyotaBankNames;
        };
    }
}