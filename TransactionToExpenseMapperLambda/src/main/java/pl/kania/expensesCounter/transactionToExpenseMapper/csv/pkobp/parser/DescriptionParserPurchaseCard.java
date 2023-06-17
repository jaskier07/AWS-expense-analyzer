package pl.kania.expensesCounter.transactionToExpenseMapper.csv.pkobp.parser;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

/**
 * Description pattern:
 * <br/>
 * <code>"Lokalizacja: (...) Kraj: (...) Miasto: (...) Adres: (...)"</code>
 */
class DescriptionParserPurchaseCard implements DescriptionParser {
    private static final List<String> WEB_SUFFIXES = List.of(".COM", ".EU", ".PL", ".NET");

    @Override
    public String parse(CSVRecord record) {
        String description = record.get(CONTEXT_INDEX_LINE_0);
        return extractShop(description);
    }

    private String extractShop(String description) {
        if (description == null) {
            return EMPTY_STRING;
        }
        return (extractShopWebsiteFromCity(description) + SEPARATOR + extractShopAddress(description)).toLowerCase().trim();
    }

    private String extractShopWebsiteFromCity(String description) {
        String[] citySplit = splitByCity(description);
        String partWithCity = citySplit[1];

        String[] addressSplit = splitByAddress(partWithCity);
        partWithCity = addressSplit[0];

        if (WEB_SUFFIXES.stream().anyMatch(partWithCity::contains)) {
            return partWithCity.trim();
        }

        return EMPTY_STRING;
    }

    private String extractShopAddress(String description) {
        String[] addressSplit = splitByAddress(description);
        String partWithAddress = addressSplit[1];

        return partWithAddress.trim();
    }

    private static String[] splitByAddress(String partWithCity) {
        return partWithCity.split("Adres:");
    }

    private static String[] splitByCity(String description) {
        return description.split("Miasto:");
    }
}
