package pl.kania.expensesCounter.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.BankType;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ExpensesExtractionRequest {
    BankType bankType;
    String content;
}
