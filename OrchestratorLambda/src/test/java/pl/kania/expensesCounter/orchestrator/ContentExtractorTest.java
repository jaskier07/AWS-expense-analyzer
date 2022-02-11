package pl.kania.expensesCounter.orchestrator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@Slf4j
class ContentExtractorTest {

    @Test
    void shouldProcessWithoutException() {
        // given
        String input = "example";
        ContentExtractor extractor = spy(ContentExtractor.class);
        doReturn(getValidInvocationResult()).when(extractor).invoke(input);

        // when & then
        assertDoesNotThrow(() -> {
            List<ParsedExpense> results = extractor.extractContent(input);
            assertFalse(results.isEmpty());
            log.info(results.toString());
        });
    }

    private Optional<byte[]> getValidInvocationResult() {
        return Optional.of("{\"expenses\":[{\"operationDate\":[2021,11,10],\"currencyDate\":[2021,11,8],\"operationId\":null,\"transactionType\":\"PURCHASE_CARD\",\"amount\":-15.21,\"balance\":162486.12,\"description\":\"JMP S.A. BIEDRONKA 420\"},{\"operationDate\":[2021,11,10],\"currencyDate\":[2021,11,8],\"operationId\":null,\"transactionType\":\"PURCHASE_CARD\",\"amount\":-15.2,\"balance\":162501.33,\"description\":\"KONKOL SKLEP 236\"},{\"operationDate\":[2021,11,10],\"currencyDate\":[2021,11,10],\"operationId\":null,\"transactionType\":\"INCOMING_TRANSFER\",\"amount\":25060.53,\"balance\":262516.53,\"description\":\"IHS GLOBAL SP. Z O.O. UL. MARYNARKI POLSKIEJ 163 ; Tytu³: ¥êŒæ¿Ÿó³ WYNAGRODZENIE ZA PAZDZIERNIK 2021\"}]}")
                .map(String::getBytes);
    }

}