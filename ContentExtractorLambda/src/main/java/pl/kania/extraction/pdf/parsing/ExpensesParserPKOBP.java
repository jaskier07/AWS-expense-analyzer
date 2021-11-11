package pl.kania.extraction.pdf.parsing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.extraction.model.ParsedExpense;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
class ExpensesParserPKOBP implements ExpensesParser {

    private final ExpensesSeparator expensesSeparator;

    @Override
    public List<ParsedExpense> parse(String text) {
        List<SeparatedExpense> expenses = expensesSeparator.separate(text);

        return expenses.stream()
                .map(this::parseExpense)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<ParsedExpense> parseExpense(SeparatedExpense separatedExpense) {
         return separatedExpense.getOperationType().getParser()
                 .parse(separatedExpense)
                 .or(() -> {
                    log.warn("Skipping expense due to problem with parsing: " + separatedExpense);
                    return Optional.empty();
                 });
    }
}
