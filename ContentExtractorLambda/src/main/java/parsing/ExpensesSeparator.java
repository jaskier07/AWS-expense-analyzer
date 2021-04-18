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

    private final OperationTypeParserInLine operationParser;

    List<SeparatedExpense> separate(String text) {
        List<SeparatedExpense> expenses = new ArrayList<>();
        String[] lines = text.split("\n");

        SeparatedExpense currentExpense = new SeparatedExpense();

        for (String line : lines) {
            if (line == null || line.isBlank()) {
                continue;
            }

            Optional<OperationType> parsedOperation = operationParser.find(line);
            if (parsedOperation.isPresent()) {
                currentExpense = new SeparatedExpense();
                expenses.add(currentExpense);
            }
            currentExpense.getLines().add(line);
        }

        return expenses;
    }
}
