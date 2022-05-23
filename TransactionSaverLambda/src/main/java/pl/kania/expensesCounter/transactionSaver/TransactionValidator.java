package pl.kania.expensesCounter.transactionSaver;

import com.amazonaws.util.StringUtils;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class TransactionValidator {

    private static final Predicate<Object> VALIDATE_NOT_NULL = Objects::nonNull;
    private static final Predicate<String> VALIDATE_NOT_EMPTY = not(StringUtils::isNullOrEmpty);

    public Optional<String> validateAndReturnErrors(List<Transaction> transactions) {
        String errors = transactions.stream()
                .map(this::validateAndReturnErrors)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining(", "));

        String errorMessage = "Problems with transactions: " + errors;
        return !errors.isEmpty() ? Optional.of(errorMessage) : Optional.empty();
    }

    public Optional<String> validateAndReturnErrors(Transaction transaction) {
        Map<String, Boolean> validations = new HashMap<>();
        validations.put("Nullable id", VALIDATE_NOT_EMPTY.test(transaction.getOperationId()));
        validations.put("Nullable date", VALIDATE_NOT_NULL.test(transaction.getDate()));
        validations.put("Nullable type", VALIDATE_NOT_NULL.test(transaction.getType()));
        validations.put("Nullable amount", VALIDATE_NOT_NULL.test(transaction.getAmount()));
        validations.put("Nullable description", VALIDATE_NOT_EMPTY.test(transaction.getDescription()));

        String errors = validations.entrySet()
                .stream()
                .filter(not(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.joining("."));

        String errorMessage = "Transaction " + transaction.getOperationId() + ": " + errors;
        return !errors.isEmpty() ? Optional.of(errorMessage) : Optional.empty();
    }
}
