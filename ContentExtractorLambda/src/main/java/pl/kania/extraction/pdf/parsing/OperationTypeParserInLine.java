package pl.kania.extraction.pdf.parsing;

import pl.kania.extraction.model.OperationType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OperationTypeParserInLine extends ValueInLineParser<OperationType> {
    @Override
    protected OperationType parseValue(String line) {
        String formattedLine = line.trim().toUpperCase();
        List<OperationType> values = Arrays.stream(OperationType.values())
                .filter(l -> Arrays.stream(l.getNames())
                        .anyMatch(formattedLine::contains))
                .collect(Collectors.toList());

        if (values.isEmpty()) {
            return null;
        } else if (values.size() > 1) {
            throw new IllegalStateException("Ambiguous line, found more than one operation type in line " + line);
        } else {
            return values.get(0);
        }
    }
}
