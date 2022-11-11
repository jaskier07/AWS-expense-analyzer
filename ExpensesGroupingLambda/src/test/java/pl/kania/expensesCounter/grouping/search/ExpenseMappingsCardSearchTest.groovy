package pl.kania.expensesCounter.grouping.search

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.GetItemResult
import io.vavr.control.Either
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping
import spock.lang.Specification

import java.util.stream.Collectors
import java.util.stream.IntStream

class ExpenseMappingsCardSearchTest extends Specification {

    private ExpenseMappingsCardSearch cardSearch
    private AmazonDynamoDB dynamoDB

    def setup() {
        this.dynamoDB = Mock(AmazonDynamoDB)
        this.cardSearch = new ExpenseMappingsCardSearch(dynamoDB)
    }

    def "returns error message when empty search parameters"() {
        given:
        List<String> parameters = Collections.emptyList()

        when:
        def result = cardSearch.search(parameters)

        then:
        result.isLeft()
        result.getLeft().contains("Empty search parameters list")
    }

    def "searches at least once to obtain the result"() {
        given:
        List<String> parameters = List.of("cukiernia")
        GetItemResult itemResult = getValidResultItem()

        and:
        dynamoDB.getItem(_) >> itemResult

        when:
        Either<String, ExpenseMapping> result = cardSearch.search(parameters)

        then:
        verifyValidResult(result)
    }

    def "continues to search with less parameters until it finds the result"() {
        given:
        List<String> parameters = List.of("cukiernia", "vega", "nowa")
        GetItemResult itemResult = getValidResultItem()

        when:
        Either<String, ExpenseMapping> result = cardSearch.search(parameters)

        then:
        3 * dynamoDB.getItem(_) >>> [
                new GetItemResult(), new GetItemResult(), itemResult
        ]
        and:
        verifyValidResult(result)
    }

    def "throws exception when too many loop operations"() {
        given:
        List<String> parameters = IntStream.range(0, 22)
                .boxed()
                .map(Integer::toString)
                .collect(Collectors.toList())

        when:
        def result = cardSearch.search(parameters)

        then:
        20 * dynamoDB.getItem(_) >> new GetItemResult()
        result.isLeft()
        result.getLeft().contains("Too many loop iterations")
    }

    private static void verifyValidResult(Either<String, ExpenseMapping> result) {
        result.isRight()
        "Cukiernia Vega" == result.get().getName()
        "Keyword" == result.get().getMappingType()
        "Food" == result.get().getExpenseCategory()
        "Pastry Shop" == result.get().getExpenseTypeSubcategory()
        "Cukiernia Vega" == result.get().getLogicalName()
    }

    private static GetItemResult getValidResultItem() {
        def itemParams = [
                "name"                  : new AttributeValue("Cukiernia Vega"),
                "mappingType"           : new AttributeValue("Keyword"),
                "expenseType"           : new AttributeValue("Food"),
                "expenseTypeSubcategory": new AttributeValue("Pastry Shop"),
                "logicalName"           : new AttributeValue("Cukiernia Vega")
        ]
        GetItemResult itemResult = new GetItemResult()
        itemResult.setItem(itemParams)
        return itemResult
    }
}
