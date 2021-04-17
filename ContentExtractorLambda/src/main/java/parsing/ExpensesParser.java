package parsing;

import model.ParsedExpense;

import java.util.List;

public interface ExpensesParser {

    List<ParsedExpense> parse(String text);

}
