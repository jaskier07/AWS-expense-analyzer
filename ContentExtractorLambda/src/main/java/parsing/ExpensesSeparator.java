package parsing;

import lombok.RequiredArgsConstructor;
import model.OperationType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class ExpensesSeparator {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private final OperationTypeParserInLine operationParser;

    List<SeparatedExpense> separate(String text) {
        List<SeparatedExpense> expenses = new ArrayList<>();
        String[] lines = text.split(NEW_LINE_SEPARATOR);

        SeparatedExpense currentExpense = null;

        for (String line : lines) {
            if (line == null || line.isBlank()) {
                continue;
            }

            Optional<OperationType> parsedOperation = operationParser.find(line);
            if (parsedOperation.isPresent()) {
                currentExpense = new SeparatedExpense(parsedOperation.get());
                expenses.add(currentExpense);
            }
            if (currentExpense == null) {
                throw new IllegalStateException("Improper expense format - parsed expense's line without parsing operation type before");
            }
            currentExpense.getLines().add(line);
        }

        return expenses;
    }
}
