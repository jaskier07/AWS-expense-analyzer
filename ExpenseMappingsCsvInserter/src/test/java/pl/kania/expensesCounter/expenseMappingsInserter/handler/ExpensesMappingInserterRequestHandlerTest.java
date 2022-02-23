package pl.kania.expensesCounter.expenseMappingsInserter.handler;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.kania.expensesCounter.commons.util.Base64RequestReader;
import pl.kania.expensesCounter.expenseMappingsInserter.ExpenseMappingDao;
import pl.kania.expensesCounter.expenseMappingsInserter.ExpenseMappingsCsvParser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExpensesMappingInserterRequestHandlerTest {

    @Test
    void shouldProcessWithoutException() {
        // given
        String input = getInput();
        Context lambdaContext = mock(Context.class);

        ExpenseMappingDao dao = mock(ExpenseMappingDao.class);
        when(dao.saveMappings(anyList())).thenReturn(1);

        ExpensesMappingInserterRequestHandler handler = new ExpensesMappingInserterRequestHandler(
                new Base64RequestReader(),
                dao,
                new ExpenseMappingsCsvParser()
        );

        // when & then
        assertDoesNotThrow(() -> {
            handler.handleRequest(input, lambdaContext);
        });
    }

    private String getInput() {
        return "biedronka,keyword,food,discount,biedronka";
    }


}