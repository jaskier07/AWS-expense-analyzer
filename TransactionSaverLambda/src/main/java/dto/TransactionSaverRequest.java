package dto;

import lombok.NoArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import java.util.List;

@Value
@NoArgsConstructor(force = true)
public class TransactionSaverRequest {
    List<Transaction> transactions;
}
