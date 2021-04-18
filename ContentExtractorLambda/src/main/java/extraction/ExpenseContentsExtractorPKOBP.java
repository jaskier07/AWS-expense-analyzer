package extraction;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
class ExpenseContentsExtractorPKOBP implements ExpenseContentsExtractor  {

    private final ExpenseContentRangeFinder finder;
    private final TextTransformer textTransformer;

    public List<String> extract(PdfTextExtractor extractor, PdfReader reader) {
        int numberOfPages = reader.getNumberOfPages() + 1;
        List<String> extractedParts = new ArrayList<>();

        for (int pageIndex = 0; pageIndex < numberOfPages; pageIndex++) {
            String pageContent = null;

            try {
                pageContent = extractor.getTextFromPage(pageIndex);
                pageContent = textTransformer.transformIntoPolishCharacters(pageContent);
                if (emptyPage(pageContent)) {
                    log.info("Skipping page {} because it is empty", pageIndex);
                    continue;
                }

                ExpenseContentRange range = finder.findRangeInPageContent(pageContent);
                String expensesPart = pageContent.substring(range.getIndexFrom(), range.getIndexTo()).trim();
                extractedParts.add(expensesPart);
            } catch (Exception e) {
                log.error("Cannot parse page " + pageIndex, e);
                log.error("Page content: " + pageContent);
            }
        }

        return extractedParts;
    }

    private static boolean emptyPage(String pageContent) {
        return pageContent == null || pageContent.isBlank();
    }
}
