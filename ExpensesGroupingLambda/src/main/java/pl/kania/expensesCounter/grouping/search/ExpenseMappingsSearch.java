package pl.kania.expensesCounter.grouping.search;

import pl.kania.expensesCounter.grouping.model.ExpenseMapping;

import java.util.List;

public abstract class ExpenseMappingsSearch<SEARCH_PARAM_TYPE> {

    public abstract ExpenseMapping search(SEARCH_PARAM_TYPE searchParameters);
}
