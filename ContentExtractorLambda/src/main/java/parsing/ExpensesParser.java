package parsing;

import model.ParserdExpense;

import java.util.List;

public interface ExpensesParser {

    List<ParserdExpense> extract(String path);

}
