package pl.kania.expensesCounter.expenseMappingsInserter;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.amazonaws.regions.Regions.EU_CENTRAL_1;
import static java.util.stream.Collectors.toList;

@Slf4j
public class ExpenseMappingDao {

    private final AmazonDynamoDB dynamoDB;

    public ExpenseMappingDao() {
        this.dynamoDB = AmazonDynamoDBClient.builder()
                .withRegion(EU_CENTRAL_1.getName())
                .build();
    }

    public int saveMappings(List<ExpenseMapping> expenseMappings) {
        return Optional.of(expenseMappings.stream()
                .map(this::mapExpenseToParameterMap)
                .map(PutRequest::new)
                .map(WriteRequest::new)
                .collect(toList()))
                .map(this::mapWriteRequestToParameterMap)
                .map(BatchWriteItemRequest::new)
                .map(request -> saveMappings(request, expenseMappings.size()))
                .orElseThrow();
    }

    private int saveMappings(BatchWriteItemRequest request, int mappingsSize) {
        log.info(request.toString());
        BatchWriteItemResult result = dynamoDB.batchWriteItem(request);
        log.info(result.toString());
        return mappingsSize;
    }

    Map<String, List<WriteRequest>> mapWriteRequestToParameterMap(List<WriteRequest> writeRequests) {
        Map<String, List<WriteRequest>> writeRequestsMap = new HashMap<>();
        writeRequestsMap.put("expense_mappings", writeRequests);
        return writeRequestsMap;
    }

    private Map<String, AttributeValue> mapExpenseToParameterMap(ExpenseMapping mapping) {
        Map<String, AttributeValue> parameterMap = new HashMap<>();
        parameterMap.put("name", new AttributeValue(mapping.getName()));
        parameterMap.put("mapping_type", new AttributeValue(mapping.getMappingType()));
        parameterMap.put("expense_type", new AttributeValue(mapping.getExpenseType()));
        parameterMap.put("logical_name", new AttributeValue(mapping.getLogicalName()));
        parameterMap.put("subcategory", new AttributeValue(mapping.getExpenseTypeSubcategory()));
        return parameterMap;
    }
}
