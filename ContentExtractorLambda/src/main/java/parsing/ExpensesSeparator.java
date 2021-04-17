package parsing;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class ExpensesSeparator {

    private final DateFinderInTextBeginning dateFinder;

    List<SeparatedExpense> separate(String text) {
        List<SeparatedExpense> expenses = new ArrayList<>();
        String[] lines = text.split("\n");

        SeparatedExpense currentExpense = new SeparatedExpense();
        expenses.add(currentExpense);

        for (String line : lines) {
            if (line == null || line.isBlank()) {
                continue;
            }

            Optional<LocalDate> parsedDate = dateFinder.find(line);
            if (parsedDate.isPresent()) {
                currentExpense = new SeparatedExpense();
                expenses.add(currentExpense);
            }
            currentExpense.getLines().add(line);
        }

        return expenses.stream()
                .filter(e -> !e.getLines().isEmpty())
                .collect(Collectors.toList());
    }
}
