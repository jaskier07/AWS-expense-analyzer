package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dto.TransactionSaverRequest;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.db.Transaction;
import pl.kania.expensesCounter.commons.util.ObjectMapperProvider;

import java.util.Optional;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@Slf4j
public class TransactionSaverRequestHandler implements RequestHandler<String, String> {

    private final ObjectMapperProvider objectMapperProvider;

    public TransactionSaverRequestHandler(ObjectMapperProvider objectMapperProvider) {
        this.objectMapperProvider = objectMapperProvider;
    }

    @Override
    public String handleRequest(String input, Context context) {
        log.info(input);

        return Optional.ofNullable(input)
                .filter(not(String::isBlank))
                .map(this::parseTransactions)
                .map()
                .orElseThrow();
    }

    private TransactionSaverRequest parseTransactions(String json) {
        return Try.of(() -> objectMapperProvider.get().readValue(json, TransactionSaverRequest.class))
                .onFailure(e -> log.error("Error parsing transactions", e))
                .getOrNull();
    }
}
