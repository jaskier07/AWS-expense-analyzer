package pl.kania.expensesCounter.grouping.search;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.grouping.model.ExpenseMapping;

import java.util.*;

import static com.amazonaws.regions.Regions.EU_CENTRAL_1;

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
    public ExpenseMapping search(List<String> searchParameters) {
        if (searchParameters.isEmpty()) {
            throw new IllegalArgumentException("Empty search parameters list: " + searchParameters);
        }

        Map<String, AttributeValue> result = null;
        searchParameters = new ArrayList<>(searchParameters);

        for (int i = 0; noResults(result) && canKeepSearchingWithLessKeywords(searchParameters); i++) {
            if (i != 0) {
                removeLastSearchParameter(searchParameters);
                log.info("Search retry #{} with parameters: {}", i, searchParameters);
                throwExceptionIfTooManyLoopIterations(i);
            }

            GetItemRequest itemRequest = getSearchRequest(searchParameters);
            result = getSearchResults(itemRequest);
        }

        return mapToResult(result);
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

    private ExpenseMapping mapToResult(Map<String, AttributeValue> result) {
        return Optional.ofNullable(result)
                .map((map) -> {
                    Set<String> keys = map.keySet();
                    log.info(keys.toString());
                    return new ExpenseMapping(null, null, null, null);
                })
                .orElseThrow();
    }

    private void throwExceptionIfTooManyLoopIterations(int i) {
        if (i > LOOP_OPERATIONS_LIMIT) {
            throw new IllegalStateException("Too many loop iterations: " + LOOP_OPERATIONS_LIMIT);
        }
    }
}
