package pl.kania.expensesCounter.transactionSaver.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;
import pl.kania.expensesCounter.commons.util.RequestHelper;
import pl.kania.expensesCounter.transactionSaver.TransactionDao;
import pl.kania.expensesCounter.transactionSaver.TransactionValidator;
import pl.kania.expensesCounter.transactionSaver.dto.TransactionSaverRequest;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.util.ObjectMapperProvider;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Slf4j
@RequiredArgsConstructor
public class TransactionSaverRequestHandler implements RequestHandler<Object, String> {

    private final ObjectMapperProvider objectMapperProvider;
    private final TransactionValidator transactionValidator;
    private final TransactionDao transactionDao;
    private final RequestHelper requestHelper;

    public TransactionSaverRequestHandler() {
        this.objectMapperProvider = new ObjectMapperProvider();
        this.transactionValidator = new TransactionValidator();
        this.transactionDao = new TransactionDao();
        this.requestHelper = new RequestHelper();
    }

    @Override
    public String handleRequest(Object input, Context context) {
        log.info(input.toString());

        return Optional.of(input)
                .map(requestHelper::mapInputJsonToString)
                .filter(not(String::isBlank))
                .map(this::parseTransactions)
                .map(TransactionSaverRequest::getTransactions)
                .map(this::ensureTransactionsAreValid)
                .map(transactionDao::saveTransactions)
                .map(savedTransactions -> "Saved " + savedTransactions + " transactions")
                .orElseThrow(() -> new NoSuchElementException("Cannot save transactions"));
    }

    private List<Transaction> ensureTransactionsAreValid(List<Transaction> transactions) {
        transactionValidator.validateAndReturnErrors(transactions)
                .ifPresent(errors -> {
                    throw new IllegalArgumentException(errors);
                });
        return transactions;
    }

    private TransactionSaverRequest parseTransactions(String json) {
        return Try.of(() -> objectMapperProvider.get().readValue(json, TransactionSaverRequest.class))
                .onFailure(e -> log.error("Error parsing transactions", e))
                .getOrNull();
    }
}