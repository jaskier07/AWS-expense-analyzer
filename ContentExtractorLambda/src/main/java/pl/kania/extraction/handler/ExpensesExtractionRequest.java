package pl.kania.extraction.handler;

import lombok.Value;
import pl.kania.extraction.model.BankType;

@Value
public class ExpensesExtractionRequest {
    BankType bankType;
    String content;
}
