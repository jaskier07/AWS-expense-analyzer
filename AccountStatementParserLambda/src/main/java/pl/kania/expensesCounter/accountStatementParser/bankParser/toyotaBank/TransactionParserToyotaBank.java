package pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank;

import io.vavr.Predicates;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pl.kania.expensesCounter.accountStatementParser.bankParser.AccountStatementTransactionType;
import pl.kania.expensesCounter.accountStatementParser.bankParser.TransactionParser;
import pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.schema.ToyotaBankOperationMapper;
import pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.schema.ToyotaBankOperations;
import pl.kania.expensesCounter.commons.dto.BankType;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;
import java.util.Objects;

import static pl.kania.expensesCounter.commons.dto.BankType.TOYOTA_BANK;

@RequiredArgsConstructor
public class TransactionParserToyotaBank implements TransactionParser {

    private final Unmarshaller unmarshaller;
    private final ToyotaBankOperationMapper operationMapper;

    public TransactionParserToyotaBank() throws JAXBException {
        unmarshaller = JAXBContext.newInstance(ToyotaBankOperations.class).createUnmarshaller();
        operationMapper = new ToyotaBankOperationMapper();
    }

    @Override
    public List<Transaction> parseTransactions(String accountStatements) {
        return Try.of(() -> accountStatements)
                .filter(StringUtils::isNotBlank)
                .map(StringReader::new)
                .mapTry(reader -> (ToyotaBankOperations) unmarshaller.unmarshal(reader))
                .filter(operations -> Objects.nonNull(operations) && Objects.nonNull(operations.getOperations()))
                .map(toyotaOperations -> toyotaOperations
                        .getOperations().stream()
                        .map(operationMapper::mapOperationToTransaction)
                        .toList()
                )
                .get();
    }
}
