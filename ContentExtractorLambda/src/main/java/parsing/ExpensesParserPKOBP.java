package parsing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.ParsedExpense;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
class ExpensesParserPKOBP implements ExpensesParser {

    private final ExpensesSeparator expensesSeparator;
    private final OutgoingTransferExpenseParser outgoingTransferParser;
    private final MobileCodeExpenseParser mobileCodeParser;
    private final CardPurchaseExpenseParser cardParser;

    @Override
    public List<ParsedExpense> parse(String text) {
        List<SeparatedExpense> expenses = expensesSeparator.separate(text);

        return expenses.stream()
                .map(this::parseExpense)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<ParsedExpense> parseExpense(SeparatedExpense separatedExpense) {
        switch (separatedExpense.getOperationType()) {
            case PURCHASE_CARD:
                return Optional.of(cardParser.parse(separatedExpense));
            case OUTGOING_TRANSFER:
                return Optional.of(outgoingTransferParser.parse(separatedExpense));
            case PURCHASE_WEB_MOBILE_CODE:
                return Optional.of(mobileCodeParser.parse(separatedExpense));
        }
        return Optional.empty();
    }
}
