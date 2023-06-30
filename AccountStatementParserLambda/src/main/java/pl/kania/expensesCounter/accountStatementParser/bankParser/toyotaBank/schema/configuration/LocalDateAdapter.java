package pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.schema.configuration;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String value) throws Exception {
        return LocalDate.parse(value);
    }

    @Override
    public String marshal(LocalDate value) throws Exception {
        return value.toString();
    }
}
