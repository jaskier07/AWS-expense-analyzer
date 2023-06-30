package pl.kania.expensesCounter.accountStatementParser.bankParser.pkobp;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import pl.kania.expensesCounter.accountStatementParser.bankParser.AccountStatementTransactionType;
import pl.kania.expensesCounter.accountStatementParser.bankParser.SingleTransactionCsvParser;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Function;

import static pl.kania.expensesCounter.commons.dto.BankType.PKO_BP;

@Slf4j
public class SingleTransactionCsvParserPkoBp implements SingleTransactionCsvParser {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Transaction extract(CSVRecord record) {
        log.info("Extracting PKOBP record: " + record);

        String transactionTypeString = record.get("Typ transakcji");
        TransactionType transactionType = AccountStatementTransactionType.from(transactionTypeString, PKO_BP).getTransactionType();
        return Transaction.builder()
                .id(record.get("")) // TODO
                .operationDate(getDate(record, "Data operacji"))
                .currencyDate(getDate(record, "Data waluty"))
                .type(transactionType)
                .amount(getDouble(record, "Kwota"))
                .description(record.get("Opis transakcji"))
                .sourceAccountNumber(record.get(""))
                .affectedBankType(PKO_BP) // TODO
                .destAccountNumber(record.get("")) // TODO
                .contractor(record.get("")) // TODO
                .expenseId(null) // will be determined in different place
                .build();
    }

    @Override
    public DateTimeFormatter getDateTimeFormatter() {
        return DATE_FORMATTER;
    }
}
