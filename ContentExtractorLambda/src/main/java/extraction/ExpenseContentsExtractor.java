package extraction;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

import java.util.List;

public interface ExpenseContentsExtractor {

    List<String> extract(PdfTextExtractor extractor, PdfReader reader);
}
