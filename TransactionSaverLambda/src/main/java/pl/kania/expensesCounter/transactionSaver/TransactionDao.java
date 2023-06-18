package pl.kania.expensesCounter.transactionSaver;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.db.Transaction;
import pl.kania.expensesCounter.commons.util.DateFormatterProvider;

import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.amazonaws.regions.Regions.EU_CENTRAL_1;
import static java.util.stream.Collectors.toList;

@Slf4j
public class TransactionDao {

    private final AmazonDynamoDB dynamoDB;
    private final DateTimeFormatter formatter;

    public TransactionDao() {
        this.dynamoDB = AmazonDynamoDBClient.builder()
                .withRegion(EU_CENTRAL_1.getName())
                .build();
        this.formatter = DateFormatterProvider.getLocalDateFormatter();
    }

    public int saveTransactions(List<Transaction> expenseMappings) {
        return Optional.of(expenseMappings.stream()
                .map(this::mapTransactionToParameterMap)
                .map(PutRequest::new)
                .map(WriteRequest::new)
                .collect(toList()))
                .map(this::mapWriteRequestToParameterMap)
                .map(BatchWriteItemRequest::new)
                .map(request -> save(request, expenseMappings.size()))
                .orElseThrow();
    }

    private int save(BatchWriteItemRequest request, int mappingsSize) {
        log.info(request.toString());
        BatchWriteItemResult result = dynamoDB.batchWriteItem(request);
        log.info(result.toString());
        return mappingsSize;
    }

    private Map<String, List<WriteRequest>> mapWriteRequestToParameterMap(List<WriteRequest> writeRequests) {
        Map<String, List<WriteRequest>> writeRequestsMap = new HashMap<>();
        writeRequestsMap.put("transactions", writeRequests);
        return writeRequestsMap;
    }

    private Map<String, AttributeValue> mapTransactionToParameterMap(Transaction transaction) {
        Map<String, AttributeValue> parameterMap = new HashMap<>();
        parameterMap.put("id", new AttributeValue(transaction.getId()));
        parameterMap.put("transaction_type", new AttributeValue(transaction.getType().name()));
        parameterMap.put("amount", new AttributeValue(transaction.getAmount().toString()));
        parameterMap.put("description", new AttributeValue(transaction.getDescription()));
        parameterMap.put("date", new AttributeValue(formatter.format(transaction.getOperationDate())));
        return parameterMap;
    }
}
