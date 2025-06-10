package mft.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.naming.Name;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@SuperBuilder

public class AccountInfo {

    private int id;
    private Person person;
    private int amount;
    private TransactionType transactionType;
    private LocalDateTime dateTime;
}
