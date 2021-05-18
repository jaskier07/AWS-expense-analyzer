package pl.kania.extraction.pdf.parsing;

import pl.kania.extraction.model.ParsedExpense;

import java.util.List;

public interface ExpensesParser {

    List<ParsedExpense> parse(String text);

}
