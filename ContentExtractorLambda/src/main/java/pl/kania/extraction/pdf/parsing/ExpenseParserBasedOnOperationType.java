package pl.kania.extraction.pdf.parsing;

import pl.kania.extraction.model.ParsedExpense;

import java.util.Optional;

public interface ExpenseParserBasedOnOperationType {

    Optional<ParsedExpense> parse(SeparatedExpense expense);
}
