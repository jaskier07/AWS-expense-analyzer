package pl.kania.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ExpensesExtractionRequest {
    BankType bankType;
    String content;
}
