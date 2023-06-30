package pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas.dataExtraction;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountNumbersExtractorBnpParibas {

    private final ContractorExtractorBnpParibas contractorExtractor;
    private final BankProductAccountNumberExtractorBnpParibas bankProductExtractor;

    public AccountNumbersExtractorBnpParibas() {
        contractorExtractor = new ContractorExtractorBnpParibas();
        bankProductExtractor = new BankProductAccountNumberExtractorBnpParibas();
    }

    public AccountNumbers extract(String contractor, String product, double amount) {
        String contractorAccountNumber = contractorExtractor.extractAccountNumber(contractor);
        String bankProductAccountNumber = bankProductExtractor.extract(product);

        if (amount > 0) {
            return new AccountNumbers(contractorAccountNumber, bankProductAccountNumber);
        } else {
            return new AccountNumbers(bankProductAccountNumber, contractorAccountNumber);
        }
    }
}
