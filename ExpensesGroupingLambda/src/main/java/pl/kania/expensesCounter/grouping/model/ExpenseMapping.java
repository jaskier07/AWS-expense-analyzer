package pl.kania.expensesCounter.grouping.model;

import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;

@Value
public class ExpenseMapping {
    String name;
    MappingType mappingType;
    String expenseType;
    String expenseTypeSubcategory;
}
