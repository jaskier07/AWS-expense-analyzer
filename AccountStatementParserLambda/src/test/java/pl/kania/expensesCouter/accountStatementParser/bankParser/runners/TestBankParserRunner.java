package pl.kania.expensesCouter.accountStatementParser.bankParser.runners;

import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.accountStatementParser.bankParser.TransactionParserFactory;
import pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.TransactionParserToyotaBank;
import pl.kania.expensesCounter.commons.dto.BankType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;
import pl.kania.expensesCounter.commons.util.BankTypeCharsetFactory;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class TestBankParserRunner {

    public static void main(String[] args) throws Exception {
        // input
        BankType bankType = BankType.PKO_BP;
        String path = "C:\\Users\\alexp\\Development\\AWS-expense-analyzer\\AccountStatementParserLambda\\src\\main\\resources\\bankStatements\\pkobp.csv";

        // run
        Path pathToFile = Paths.get(path);
        Charset charset = new BankTypeCharsetFactory().getByBankType(bankType);
        String file = Files.readString(pathToFile, charset);
        List<Transaction> result = new TransactionParserFactory().get(bankType).parseTransactions(file);

        // print results
        result.forEach(transaction -> log.info(transaction.toString()));
    }
}
