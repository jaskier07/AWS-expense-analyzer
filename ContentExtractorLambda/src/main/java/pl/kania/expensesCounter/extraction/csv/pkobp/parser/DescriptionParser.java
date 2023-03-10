package pl.kania.expensesCounter.extraction.csv.pkobp.parser;

import org.apache.commons.csv.CSVRecord;

public interface DescriptionParser {

   int CONTEXT_INDEX_TRANSACTION_TYPE = 2;
   int CONTEXT_INDEX_TRANSACTION_DESCRIPTION = 6;
   int CONTEXT_INDEX_LINE_0 = 7;
   int CONTEXT_INDEX_LINE_1 = 8;
   int CONTEXT_INDEX_LINE_2 = 9;
   int CONTEXT_INDEX_LINE_3 = 10;
   String SEPARATOR = " ";
   String EMPTY_STRING = "";

    String parse(CSVRecord record);
}
