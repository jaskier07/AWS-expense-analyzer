package parsing;

import lombok.AllArgsConstructor;
import lombok.Value;
import model.OperationType;

import java.util.ArrayList;
import java.util.List;

@Value
@AllArgsConstructor
class SeparatedExpense {

    OperationType operationType;
    List<String> lines;

    public SeparatedExpense(OperationType operationType, String line) {
        lines = new ArrayList<>();
        lines.add(line);
        this.operationType = operationType;
    }

    public SeparatedExpense(OperationType operationType) {
        lines = new ArrayList<>();
        this.operationType = operationType;
    }
}
