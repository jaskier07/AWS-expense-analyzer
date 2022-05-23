package pl.kania.expensesCounter.transactionSaver.dto;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import java.util.List;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class TransactionSaverRequest {
    List<Transaction> transactions;
}
