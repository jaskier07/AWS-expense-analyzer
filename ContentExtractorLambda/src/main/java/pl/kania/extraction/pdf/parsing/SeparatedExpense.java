package pl.kania.extraction.pdf.parsing;

import lombok.AllArgsConstructor;
import lombok.Value;
import pl.kania.extraction.pdf.OperationTypePDF;

import java.util.ArrayList;
import java.util.List;

@Value
@AllArgsConstructor
class SeparatedExpense {

    OperationTypePDF operationType;
    List<String> lines;

    public SeparatedExpense(OperationTypePDF operationType, String line) {
        lines = new ArrayList<>();
        lines.add(line);
        this.operationType = operationType;
    }

    public SeparatedExpense(OperationTypePDF operationType) {
        lines = new ArrayList<>();
        this.operationType = operationType;
    }
}
