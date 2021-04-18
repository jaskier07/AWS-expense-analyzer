package parsing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.ParsedExpense;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
class ExpensesParserPKOBP implements ExpensesParser {

    private final ExpensesSeparator expensesSeparator;

    @Override
    public List<ParsedExpense> parse(String text) {
        List<SeparatedExpense> expenses = expensesSeparator.separate(text);
        expenses.forEach(e -> log.info(e.toString()));

        return new ArrayList<>();
    }
}
