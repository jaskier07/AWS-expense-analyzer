package pl.kania.extraction.pdf.parsing;

import pl.kania.extraction.model.ParsedExpense;

interface ExpenseParserBasedOnOperationType {

    ParsedExpense parse(SeparatedExpense expense);
}
