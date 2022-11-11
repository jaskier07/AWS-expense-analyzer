package pl.kania.expensesCounter.grouping.search;

import io.vavr.control.Either;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;

public abstract class ExpenseMappingsSearch<SEARCH_PARAM_TYPE> {

    public abstract Either<String, ExpenseMapping> search(SEARCH_PARAM_TYPE searchParameters);
}
