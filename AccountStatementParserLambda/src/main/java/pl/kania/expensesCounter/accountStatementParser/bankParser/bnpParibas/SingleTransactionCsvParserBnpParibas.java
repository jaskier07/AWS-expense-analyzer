package pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import pl.kania.expensesCounter.accountStatementParser.bankParser.AccountStatementTransactionType;
import pl.kania.expensesCounter.accountStatementParser.bankParser.SingleTransactionCsvParser;
import pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas.dataExtraction.AccountNumbers;
import pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas.dataExtraction.AccountNumbersExtractorBnpParibas;
import pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas.dataExtraction.ContractorExtractorBnpParibas;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import java.time.format.DateTimeFormatter;

import static pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas.CsvHeaderBnpParibas.*;
import static pl.kania.expensesCounter.commons.dto.BankType.BNP_PARIBAS;

@Slf4j
@RequiredArgsConstructor
public class SingleTransactionCsvParserBnpParibas implements SingleTransactionCsvParser {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final AccountNumbersExtractorBnpParibas accountNumbersExtractor;
    private final ContractorExtractorBnpParibas contractorExtractor;

    public SingleTransactionCsvParserBnpParibas() {
        this.accountNumbersExtractor = new AccountNumbersExtractorBnpParibas();
        this.contractorExtractor = new ContractorExtractorBnpParibas();
    }

    @Override
    public Transaction extract(CSVRecord record) {
        log.info("Extracting Bnp Paribas record: " + record);

        String transactionTypeString = record.get(TRANSACTION_TYPE.getName());
        TransactionType transactionType = AccountStatementTransactionType.from(transactionTypeString, BNP_PARIBAS).getTransactionType();

        Double amount = getDouble(record, AMOUNT.getName());
        String contractor = record.get(CONTRACTOR.getName());
        String bankProduct = record.get(BANK_PRODUCT.getName());
        AccountNumbers accountNumbers = accountNumbersExtractor.extract(contractor, bankProduct, amount);

        return Transaction.builder()
                .id("") // TODO
                .operationDate(getDate(record, OPERATION_DATE.getName()))
                .currencyDate(getDate(record, CURRENCY_DATE.getName()))
                .type(transactionType)
                .amount(amount)
                .description(record.get(DESCRIPTION.getName()))
                .sourceAccountNumber(accountNumbers.sourceAccountNumber())
                .affectedBankType(BNP_PARIBAS)
                .destAccountNumber(accountNumbers.destAccountNumber())
                .contractor(contractorExtractor.extractContractorName(contractor))
                .expenseId(null) // will be determined in different place
                .build();
    }

    @Override
    public DateTimeFormatter getDateTimeFormatter() {
        return DATE_FORMATTER;
    }
}
