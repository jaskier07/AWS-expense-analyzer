package pl.kania.extraction.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.kania.extraction.model.BankType;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ExpensesExtractionRequest {
    BankType bankType;
    String content;
}
