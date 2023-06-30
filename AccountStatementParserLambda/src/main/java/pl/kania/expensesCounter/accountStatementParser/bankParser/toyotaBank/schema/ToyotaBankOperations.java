package pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.schema;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "operacje")
public class ToyotaBankOperations {

        public List<ToyotaBankOperation> operations = new ArrayList<>();

        @XmlElement(name = "operacja")
        public void setOperations(List<ToyotaBankOperation> operations) {
                this.operations = operations;
        }
}
