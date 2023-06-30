package pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.schema.configuration.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@XmlRootElement(name = "operacja")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ToyotaBankOperation {
    public String id;
    public LocalDate operationDate;
    public LocalDate currencyDate;
    public String type;
    public Double amount;
    public String description;
    public String sourceAccountNumber;
    public String destAccountNumber;
    public String contractor;

    @XmlElement(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlElement(name = "data_waluty")
    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlElement(name = "data_ksiegowa")
    public void setCurrencyDate(LocalDate currencyDate) {
        this.currencyDate = currencyDate;
    }

    @XmlElement(name = "rodzaj")
    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "kwota")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @XmlElement(name = "tresc1")
    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "rachunek_n")
    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    @XmlElement(name = "rachunek_o")
    public void setDestAccountNumber(String destAccountNumber) {
        this.destAccountNumber = destAccountNumber;
    }

    @XmlElement(name = "nazwa1")
    public void setContractor(String contractor) {
        this.contractor = contractor;
    }
}
