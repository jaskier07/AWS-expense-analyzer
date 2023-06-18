package pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.schema;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "operacje")
public record ToyotaBankOperations(

        List<ToyotaBankOperation> operations
) {
}
