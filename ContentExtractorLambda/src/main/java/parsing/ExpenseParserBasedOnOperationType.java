package parsing;

import model.ParsedExpense;

interface ExpenseParserBasedOnOperationType {

    ParsedExpense parse(SeparatedExpense expense);
}
