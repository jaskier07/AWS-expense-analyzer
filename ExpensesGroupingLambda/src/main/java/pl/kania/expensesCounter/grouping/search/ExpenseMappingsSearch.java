package pl.kania.expensesCounter.grouping.search;

import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;

public abstract class ExpenseMappingsSearch<SEARCH_PARAM_TYPE> {

    public abstract ExpenseMapping search(SEARCH_PARAM_TYPE searchParameters);
}
