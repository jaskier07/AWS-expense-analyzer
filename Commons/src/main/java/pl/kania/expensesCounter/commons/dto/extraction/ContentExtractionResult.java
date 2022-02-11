package pl.kania.expensesCounter.commons.dto.extraction;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ContentExtractionResult {

    List<ParsedExpense> expenses;

}
