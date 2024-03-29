package pl.kania.expensesCounter.grouping.purchase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.dto.grouping.ExpenseError;
import pl.kania.expensesCounter.commons.dto.grouping.ExpenseGroupingResult;
import pl.kania.expensesCounter.commons.dto.grouping.GroupingResultPerExpenseCategory;
import pl.kania.expensesCounter.grouping.purchase.processor.CardPurchaseProcessor;
import pl.kania.expensesCounter.grouping.purchase.processor.IncomingTransferPurchaseProcessor;
import pl.kania.expensesCounter.grouping.purchase.processor.OutgoingTransferPurchaseProcessor;
import pl.kania.expensesCounter.grouping.purchase.processor.WebMobileCodePurchaseProcessor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

@Slf4j
@AllArgsConstructor
public class PurchaseProcessorFacade {

    private final CardPurchaseProcessor cardPurchaseProcessor;
    private final IncomingTransferPurchaseProcessor incomingTransferProcessor;
    private final OutgoingTransferPurchaseProcessor outgoingTransferProcessor;
    private final WebMobileCodePurchaseProcessor webMobileCodePurchaseProcessor;

    public PurchaseProcessorFacade() {
        this.cardPurchaseProcessor = new CardPurchaseProcessor();
        this.incomingTransferProcessor = new IncomingTransferPurchaseProcessor();
        this.outgoingTransferProcessor = new OutgoingTransferPurchaseProcessor();
        this.webMobileCodePurchaseProcessor = new WebMobileCodePurchaseProcessor();
    }

    public ExpenseGroupingResult groupExpensesByExpenseCategories(Map<TransactionType, List<ParsedExpense>> expensesPerTransactionType) {
        List<ExpenseGroupingResult> results = groupAllExpensesByExpenseTypes(expensesPerTransactionType);

        Map<String, GroupingResultPerExpenseCategory> groupings = getGroupings(results);
        List<ExpenseError> errors = gatherErrors(results);

        return new ExpenseGroupingResult(groupings, errors);
    }

    private List<ExpenseGroupingResult> groupAllExpensesByExpenseTypes(Map<TransactionType, List<ParsedExpense>> expensesPerTransactionType) {
        return expensesPerTransactionType.entrySet().stream()
                .map(entry -> {
                    TransactionType transactionType = entry.getKey();
                    List<ParsedExpense> expenses = entry.getValue();

                    switch (transactionType) {
                        case PURCHASE_CARD:
                            return cardPurchaseProcessor.process(expenses);
                        case INCOMING_TRANSFER:
                            return incomingTransferProcessor.process(expenses);
                        case OUTGOING_TRANSFER:
                            return outgoingTransferProcessor.process(expenses);
                        case PURCHASE_WEB_MOBILE_CODE:
                            return webMobileCodePurchaseProcessor.process(expenses);
                        case WITHDRAWAL:
                        case OTHER:
                        default:
                            List<ExpenseError> errors = getExpenseErrorsWhenMappingNotFound(transactionType, expenses);
                            return new ExpenseGroupingResult(emptyMap(), errors);
                    }
                })
                .collect(Collectors.toList());
    }

    private List<ExpenseError> getExpenseErrorsWhenMappingNotFound(TransactionType transactionType, List<ParsedExpense> expenses) {
        String errorMessage = "Unhandled transaction type: " + transactionType;
        log.error(errorMessage);
        return expenses.stream()
                .map(expense -> new ExpenseError(expense, errorMessage))
                .collect(Collectors.toList());
    }

    private List<ExpenseError> gatherErrors(List<ExpenseGroupingResult> results) {
        return results.stream()
                .map(ExpenseGroupingResult::getErrors)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private Map<String, GroupingResultPerExpenseCategory> getGroupings(List<ExpenseGroupingResult> results) {
        Map<String, GroupingResultPerExpenseCategory> groupings = new HashMap<>();

        results.stream()
                .map(ExpenseGroupingResult::getGroupings)
                .forEach(groupingsPerCategory -> {
                    groupingsPerCategory.forEach((category, grouping) -> {
                        if (groupings.containsKey(category)) {
                            GroupingResultPerExpenseCategory existingGroupings = groupings.get(category);
                            existingGroupings.addExpenses(grouping.getExpenses());
                            // FIXME is it enough? no need to replace value? groupings.replace(category, newGrouping);
                        } else {
                            groupings.put(category, grouping);
                        }
                    });
                });

        return groupings;
    }
}
