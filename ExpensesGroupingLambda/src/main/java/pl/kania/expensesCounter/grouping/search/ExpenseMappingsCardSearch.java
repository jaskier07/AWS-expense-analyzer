package pl.kania.expensesCounter.grouping.search;

import pl.kania.expensesCounter.grouping.model.ExpenseMapping;

import java.util.ArrayList;
import java.util.List;

public class ExpenseMappingsCardSearch extends ExpenseMappingsSearch<List<String>> {


    @Override
    public List<ExpenseMapping> search(List<String> searchParameters) {
        return new ArrayList<>();
    }
}
