package pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas.dataExtraction;

import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;

public class ContractorExtractorBnpParibas {

    public String extractAccountNumber(String contractor) {
        return extractLine(contractor, 0);
    }

    public String extractContractorName(String contractor) {
        return extractLine(contractor, 1);
    }

    private String extractLine(String input, int lineNumber) {
        return Try.of(() -> input)
                .filter(StringUtils::isNotBlank)
                .map(text -> {
                    String[] contractorLines = text.split("\n");
                    return contractorLines[lineNumber];
                })
                .getOrElse("");
    }
}
