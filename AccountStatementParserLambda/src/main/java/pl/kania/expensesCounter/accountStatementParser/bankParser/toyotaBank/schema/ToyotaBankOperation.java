package pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.schema;

import pl.kania.expensesCounter.commons.dto.TransactionType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement(name = "operacja")
public record ToyotaBankOperation(
        @XmlElement(name = "id")
        String id,
        @XmlElement(name = "data_waluty")
        LocalDate operationDate,
        @XmlElement(name = "data_ksiegowa")
        LocalDate currencyDate,
        @XmlElement(name = "rodzaj")
        String type,
        @XmlElement(name = "kwota")
        Double amount,
        @XmlElement(name = "tresc1")
        String description,
        @XmlElement(name = "rachunek_n")
        String sourceAccountNumber,
        @XmlElement(name = "rachunek_o")
        String destAccountNumber,
        @XmlElement(name = "nazwa1")
        String contractor
) {
}
