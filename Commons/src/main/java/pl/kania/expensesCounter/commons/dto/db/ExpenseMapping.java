package pl.kania.expensesCounter.commons.dto.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseMapping {
    private String name;
    private MappingType mappingType;
    private String expenseType;
    private String expenseTypeSubcategory;
    private String logicalName;

    public String getMappingTypeString() {
        return mappingType == null ? null : mappingType.name();
    }
}
