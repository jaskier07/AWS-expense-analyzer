package parsing;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@AllArgsConstructor
class SeparatedExpense {

    List<String> lines;

    public SeparatedExpense(String line) {
        lines = new ArrayList<>();
        lines.add(line);
    }

    public SeparatedExpense() {
        lines = new ArrayList<>();
    }
}
