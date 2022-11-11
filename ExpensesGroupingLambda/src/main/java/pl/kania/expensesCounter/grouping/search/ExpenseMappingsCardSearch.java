package pl.kania.expensesCounter.grouping.search;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;

import java.util.*;
import java.util.function.Function;

import static com.amazonaws.regions.Regions.EU_CENTRAL_1;
import static java.util.function.Predicate.not;

@Slf4j
@RequiredArgsConstructor
public class ExpenseMappingsCardSearch extends ExpenseMappingsSearch<List<String>> {

    private static final String TABLE_NAME = "expense_mappings";
    private static final String COLUMN_TO_SEARCH = "name";
    private static final int LOOP_OPERATIONS_LIMIT = 20;
    private final AmazonDynamoDB dynamoDB;

    public ExpenseMappingsCardSearch() {
        this.dynamoDB = AmazonDynamoDBClient.builder()
                .withRegion(EU_CENTRAL_1.getName())
                .build();
    }

    @Override
    public Either<String, ExpenseMapping> search(final List<String> searchParameters) {
        if (searchParameters.isEmpty()) {
            return Either.left("Empty search parameters list: " + searchParameters);
        }

        return Try.of(() -> {
            Map<String, AttributeValue> result = null;
            List<String> currentSearchParameters = new ArrayList<>(searchParameters);

            for (int i = 0; noResults(result) && canKeepSearchingWithLessKeywords(currentSearchParameters); i++) {
                if (i != 0) {
                    removeLastSearchParameter(currentSearchParameters);
                    log.info("Search retry #{} with parameters: {}", i, currentSearchParameters);
                    throwExceptionIfTooManyLoopIterations(i);
                }

                GetItemRequest itemRequest = getSearchRequest(currentSearchParameters);
                result = getSearchResults(itemRequest);
            }

            return mapToResult(result);
        }).getOrElse(Either.left("Problem searching for mappings"));
    }

    private boolean canKeepSearchingWithLessKeywords(List<String> searchParameters) {
        return searchParameters.size() > 1;
    }

    private void removeLastSearchParameter(List<String> searchParameters) {
        searchParameters.remove(searchParameters.size() - 1);
    }

    private boolean noResults(Map<String, AttributeValue> result) {
        return result == null || result.isEmpty();
    }

    private Map<String, AttributeValue> getSearchResults(GetItemRequest itemRequest) {
        GetItemResult result = dynamoDB.getItem(itemRequest);
        Map<String, AttributeValue> foundItem = result.getItem();
        log.info("Result: {}", foundItem);
        return foundItem;
    }

    private GetItemRequest getSearchRequest(List<String> searchParameters) {
        String keywords = String.join(" ", searchParameters).toLowerCase();
        AttributeValue attributeValue = new AttributeValue(keywords);

        Map<String, AttributeValue> key = new HashMap<>();
        key.put(COLUMN_TO_SEARCH, attributeValue);

        GetItemRequest itemRequest = new GetItemRequest()
                .withKey(key)
                .withTableName(TABLE_NAME);
        log.info("Request: " + itemRequest.toString());

        return itemRequest;
    }

    private Either<String, ExpenseMapping> mapToResult(Map<String, AttributeValue> result) {
        return Optional.ofNullable(result)
                .map((Map<String, AttributeValue> map) -> {
                    log.info(map.toString());
                    return new ExpenseMapping(
                            getStringValue("name", map),
                            getStringValue("mappingType", map),
                            getStringValue("expenseType", map),
                            getStringValue("expenseTypeSubcategory", map),
                            getStringValue("logicalName", map)
                    );
                })
                .map((Function<ExpenseMapping, Either<String, ExpenseMapping>>) Either::right)
                .orElse(Either.left("Problem mapping to result"));
    }

    private String getStringValue(String key, Map<String, AttributeValue> result) {
        return Optional.ofNullable(result.get(key))
                .map(r -> r.getS())
                .filter(not(String::isBlank))
                .orElse(null);
    }

    private void throwExceptionIfTooManyLoopIterations(int i) {
        if (i > LOOP_OPERATIONS_LIMIT) {
            throw new IllegalStateException("Too many loop iterations: " + LOOP_OPERATIONS_LIMIT);
        }
    }
}
