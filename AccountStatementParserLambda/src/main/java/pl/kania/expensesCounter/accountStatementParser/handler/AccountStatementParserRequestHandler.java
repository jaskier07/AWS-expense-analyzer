package pl.kania.expensesCounter.accountStatementParser.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import pl.kania.expensesCounter.accountStatementParser.bankParser.TransactionParser;
import pl.kania.expensesCounter.accountStatementParser.bankParser.TransactionParserFactory;
import pl.kania.expensesCounter.commons.dto.BankType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;
import pl.kania.expensesCounter.commons.util.Base64RequestReader;
import pl.kania.expensesCounter.commons.util.RequestHelper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class AccountStatementParserRequestHandler implements RequestHandler<Map<String, String>, String> {

    private static final String ACCOUNT_STATEMENT_PARAMETER = "accountStatement";
    private static final String BANK_TYPE_PARAMETER = "bankType";

    private final RequestHelper requestHelper = new RequestHelper();
    private final Base64RequestReader requestReader = new Base64RequestReader();
    private final TransactionParserFactory transactionParserFactory = new TransactionParserFactory();

    @Override
    public String handleRequest(Map<String, String> inputParameters, Context context) {
        MapUtils.debugPrint(System.out, "Lambda's parameters", inputParameters);

        String accountStatementBase64Encoded = inputParameters.get(ACCOUNT_STATEMENT_PARAMETER);
        BankType bankType = BankType.valueOf(inputParameters.get(BANK_TYPE_PARAMETER));

        return Try.of(() -> accountStatementBase64Encoded)
                .map(requestReader::readStringBase64Encoded)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .mapTry(accountStatement -> parseTransactions(accountStatement, bankType))
                .mapTry(requestHelper::writeObjectAsBase64)
                .onFailure(e -> log.error("Problem parsing account statement", e))
                .getOrElseThrow(() -> new IllegalStateException("Problem parsing account statement"));
    }

    private List<Transaction> parseTransactions(String accountStatement, BankType bankType) throws Exception {
        TransactionParser parser = transactionParserFactory.get(bankType);
        return parser.parseTransactions(accountStatement);
    }
}
